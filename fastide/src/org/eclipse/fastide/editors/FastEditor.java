/**
 *
 */
package org.eclipse.fastide.editors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.fastide.FastContextMenuProvider;
import org.eclipse.fastide.FastidePlugin;
import org.eclipse.fastide.actions.FastPasteTemplateAction;
import org.eclipse.fastide.dnd.TextTransferDropTargetListener;
import org.eclipse.fastide.edit.GraphicalPartFactory;
import org.eclipse.fastide.edit.TreePartFactory;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.FastRuler;
import org.eclipse.fastide.palette.FastPaletteCustomizer;
import org.eclipse.fastide.rulers.FastRulerProvider;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.CopyTemplateAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.MatchHeightAction;
import org.eclipse.gef.ui.actions.MatchWidthAction;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

/**
 * @author …Ú»›÷€
 */
public class FastEditor extends GraphicalEditorWithFlyoutPalette {

    class OutlinePage extends ContentOutlinePage implements IAdaptable {

        private PageBook pageBook;

        private Control outline;

        private Canvas overview;

        private IAction showOutlineAction, showOverviewAction;

        static final int ID_OUTLINE = 0;

        static final int ID_OVERVIEW = 1;

        private Thumbnail thumbnail;

        private DisposeListener disposeListener;

        public OutlinePage(EditPartViewer viewer) {
            super(viewer);
        }

        public void init(IPageSite pageSite) {
            super.init(pageSite);
            ActionRegistry registry = getActionRegistry();
            IActionBars bars = pageSite.getActionBars();
            String id = ActionFactory.UNDO.getId();
            bars.setGlobalActionHandler(id, registry.getAction(id));
            id = ActionFactory.REDO.getId();
            bars.setGlobalActionHandler(id, registry.getAction(id));
            id = ActionFactory.DELETE.getId();
            bars.setGlobalActionHandler(id, registry.getAction(id));
            bars.updateActionBars();
        }

        protected void configureOutlineViewer() {
            getViewer().setEditDomain(getEditDomain());
            getViewer().setEditPartFactory(new TreePartFactory());
            ContextMenuProvider provider = new FastContextMenuProvider(
                    getViewer(), getActionRegistry());
            getViewer().setContextMenu(provider);
            getSite().registerContextMenu(
                    "org.eclipse.fastide.outline.contextmenu", //$NON-NLS-1$
                    provider, getSite().getSelectionProvider());
            getViewer().setKeyHandler(getCommonKeyHandler());
            getViewer().addDropTargetListener(
                    new FastTemplateTransferDropTargetListener(getViewer()));
            IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
            showOutlineAction = new Action() {
                public void run() {
                    showPage(ID_OUTLINE);
                }
            };
            showOutlineAction.setImageDescriptor(ImageDescriptor
                    .createFromFile(FastEditor.class, "icons/outline.gif")); //$NON-NLS-1$
            tbm.add(showOutlineAction);
            showOverviewAction = new Action() {
                public void run() {
                    showPage(ID_OVERVIEW);
                }
            };
            showOverviewAction.setImageDescriptor(ImageDescriptor
                    .createFromFile(FastEditor.class, "icons/overview.gif")); //$NON-NLS-1$
            tbm.add(showOverviewAction);
            showPage(ID_OUTLINE);
        }

        public void createControl(Composite parent) {
            pageBook = new PageBook(parent, SWT.NONE);
            outline = getViewer().createControl(pageBook);
            overview = new Canvas(pageBook, SWT.NONE);
            pageBook.showPage(outline);
            configureOutlineViewer();
            hookOutlineViewer();
            initializeOutlineViewer();
        }

        public void dispose() {
            unhookOutlineViewer();
            if (thumbnail != null) {
                thumbnail.deactivate();
                thumbnail = null;
            }
            super.dispose();
            FastEditor.this.outlinePage = null;
            outlinePage = null;
        }

        public Object getAdapter(Class type) {
            if (type == ZoomManager.class)
                return getGraphicalViewer().getProperty(
                        ZoomManager.class.toString());
            return null;
        }

        public Control getControl() {
            return pageBook;
        }

        protected void hookOutlineViewer() {
            getSelectionSynchronizer().addViewer(getViewer());
        }

        protected void initializeOutlineViewer() {
            setContents(getFastDiagram());
        }

        protected void initializeOverview() {
            LightweightSystem lws = new LightweightSystem(overview);
            RootEditPart rep = getGraphicalViewer().getRootEditPart();
            if (rep instanceof ScalableFreeformRootEditPart) {
                ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) rep;
                thumbnail = new ScrollableThumbnail((Viewport) root.getFigure());
                thumbnail.setBorder(new MarginBorder(3));
                thumbnail.setSource(root
                        .getLayer(LayerConstants.PRINTABLE_LAYERS));
                lws.setContents(thumbnail);
                disposeListener = new DisposeListener() {
                    public void widgetDisposed(DisposeEvent e) {
                        if (thumbnail != null) {
                            thumbnail.deactivate();
                            thumbnail = null;
                        }
                    }
                };
                getEditor().addDisposeListener(disposeListener);
            }
        }

        public void setContents(Object contents) {
            getViewer().setContents(contents);
        }

        protected void showPage(int id) {
            if (id == ID_OUTLINE) {
                showOutlineAction.setChecked(true);
                showOverviewAction.setChecked(false);
                pageBook.showPage(outline);
                if (thumbnail != null)
                    thumbnail.setVisible(false);
            } else if (id == ID_OVERVIEW) {
                if (thumbnail == null)
                    initializeOverview();
                showOutlineAction.setChecked(false);
                showOverviewAction.setChecked(true);
                pageBook.showPage(overview);
                thumbnail.setVisible(true);
            }
        }

        protected void unhookOutlineViewer() {
            getSelectionSynchronizer().removeViewer(getViewer());
            if (disposeListener != null && getEditor() != null
                    && !getEditor().isDisposed())
                getEditor().removeDisposeListener(disposeListener);
        }
    }

    private KeyHandler sharedKeyHandler;

    private PaletteRoot root;

    private OutlinePage outlinePage;

    private boolean editorSaving = false;

    private FastDiagram fastDiagram = new FastDiagram();

    private boolean savePreviouslyNeeded = false;

    private RulerComposite rulerComp;

    protected static final String PALETTE_DOCK_LOCATION = "Dock location"; //$NON-NLS-1$

    protected static final String PALETTE_SIZE = "Palette Size"; //$NON-NLS-1$

    protected static final String PALETTE_STATE = "Palette state"; //$NON-NLS-1$

    protected static final int DEFAULT_PALETTE_SIZE = 130;

    static {
        FastidePlugin.getDefault().getPreferenceStore().setDefault(
                PALETTE_SIZE, DEFAULT_PALETTE_SIZE);
    }

    public FastEditor() {
        setEditDomain(new DefaultEditDomain(this));
    }

    protected void closeEditor(boolean save) {
        getSite().getPage().closeEditor(FastEditor.this, save);
    }

    public void commandStackChanged(EventObject event) {
        if (isDirty()) {
            if (!savePreviouslyNeeded()) {
                setSavePreviouslyNeeded(true);
                firePropertyChange(IEditorPart.PROP_DIRTY);
            }
        } else {
            setSavePreviouslyNeeded(false);
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
        super.commandStackChanged(event);
    }

    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();

        ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();

        List zoomLevels = new ArrayList(3);
        zoomLevels.add(ZoomManager.FIT_ALL);
        zoomLevels.add(ZoomManager.FIT_WIDTH);
        zoomLevels.add(ZoomManager.FIT_HEIGHT);
        root.getZoomManager().setZoomLevelContributions(zoomLevels);

        IAction zoomIn = new ZoomInAction(root.getZoomManager());
        IAction zoomOut = new ZoomOutAction(root.getZoomManager());
        getActionRegistry().registerAction(zoomIn);
        getActionRegistry().registerAction(zoomOut);
        getSite().getKeyBindingService().registerAction(zoomIn);
        getSite().getKeyBindingService().registerAction(zoomOut);

        viewer.setRootEditPart(root);

        viewer.setEditPartFactory(new GraphicalPartFactory());
        ContextMenuProvider provider = new FastContextMenuProvider(viewer,
                getActionRegistry());
        viewer.setContextMenu(provider);
        getSite().registerContextMenu(
                "org.eclipse.gef.examples.logic.editor.contextmenu", //$NON-NLS-1$
                provider, viewer);
        viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer)
                .setParent(getCommonKeyHandler()));

        loadProperties();

        // Actions
        IAction showRulers = new ToggleRulerVisibilityAction(
                getGraphicalViewer());
        getActionRegistry().registerAction(showRulers);

        IAction snapAction = new ToggleSnapToGeometryAction(
                getGraphicalViewer());
        getActionRegistry().registerAction(snapAction);

        IAction showGrid = new ToggleGridAction(getGraphicalViewer());
        getActionRegistry().registerAction(showGrid);

        Listener listener = new Listener() {
            public void handleEvent(Event event) {
                handleActivationChanged(event);
            }
        };
        getGraphicalControl().addListener(SWT.Activate, listener);
        getGraphicalControl().addListener(SWT.Deactivate, listener);
    }

    protected void createOutputStream(OutputStream os) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(getFastDiagram());
        out.close();
    }

    protected void createXmlOutputStream(File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.appendChild(getFastDiagram().getXml(document));
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document),
                    new StreamResult(out));
        } catch (DOMException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        } catch (TransformerConfigurationException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        } catch (ParserConfigurationException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        } catch (TransformerFactoryConfigurationError exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        } catch (TransformerException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }
    }

    protected CustomPalettePage createPalettePage() {
        return new CustomPalettePage(getPaletteViewerProvider()) {
            public void init(IPageSite pageSite) {
                super.init(pageSite);
                IAction copy = getActionRegistry().getAction(
                        ActionFactory.COPY.getId());
                pageSite.getActionBars().setGlobalActionHandler(
                        ActionFactory.COPY.getId(), copy);
            }
        };
    }

    protected PaletteViewerProvider createPaletteViewerProvider() {
        return new PaletteViewerProvider(getEditDomain()) {
            private IMenuListener menuListener;

            protected void configurePaletteViewer(PaletteViewer viewer) {
                super.configurePaletteViewer(viewer);
                viewer.setCustomizer(new FastPaletteCustomizer());
                viewer
                        .addDragSourceListener(new TemplateTransferDragSourceListener(
                                viewer));
            }

            protected void hookPaletteViewer(PaletteViewer viewer) {
                super.hookPaletteViewer(viewer);
                final CopyTemplateAction copy = (CopyTemplateAction) getActionRegistry()
                        .getAction(ActionFactory.COPY.getId());
                viewer.addSelectionChangedListener(copy);
                if (menuListener == null)
                    menuListener = new IMenuListener() {
                        public void menuAboutToShow(IMenuManager manager) {
                            manager.appendToGroup(
                                    GEFActionConstants.GROUP_COPY, copy);
                        }
                    };
                viewer.getContextMenu().addMenuListener(menuListener);
            }
        };
    }

    public void doSave(IProgressMonitor progressMonitor) {
        FastEditorInput in = (FastEditorInput) getEditorInput();
        File file = in.getPath().toFile();
        try {
            OutputStream stream = new FileOutputStream(file);
            createOutputStream(stream);
            file = in.getPath().removeFileExtension().addFileExtension("fsx")
                    .toFile();
            createXmlOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getCommandStack().markSaveLocation();
    }

    public void doSaveAs() {

    }

    public Object getAdapter(Class type) {
        if (type == IContentOutlinePage.class) {
            outlinePage = new OutlinePage(new TreeViewer());
            return outlinePage;
        }
        if (type == ZoomManager.class)
            return getGraphicalViewer().getProperty(
                    ZoomManager.class.toString());

        return super.getAdapter(type);
    }

    protected Control getGraphicalControl() {
        return rulerComp;
    }

    /**
     * Returns the KeyHandler with common bindings for both the Outline and
     * Graphical Views. For example, delete is a common action.
     */
    protected KeyHandler getCommonKeyHandler() {
        if (sharedKeyHandler == null) {
            sharedKeyHandler = new KeyHandler();
            sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
                    getActionRegistry().getAction(
                            GEFActionConstants.DIRECT_EDIT));
        }
        return sharedKeyHandler;
    }

    protected FastDiagram getFastDiagram() {
        return fastDiagram;
    }

    protected FlyoutPreferences getPalettePreferences() {
        return new FlyoutPreferences() {
            public int getDockLocation() {
                return FastidePlugin.getDefault().getPreferenceStore().getInt(
                        PALETTE_DOCK_LOCATION);
            }

            public int getPaletteState() {
                return FastidePlugin.getDefault().getPreferenceStore().getInt(
                        PALETTE_STATE);
            }

            public int getPaletteWidth() {
                return FastidePlugin.getDefault().getPreferenceStore().getInt(
                        PALETTE_SIZE);
            }

            public void setDockLocation(int location) {
                FastidePlugin.getDefault().getPreferenceStore().setValue(
                        PALETTE_DOCK_LOCATION, location);
            }

            public void setPaletteState(int state) {
                FastidePlugin.getDefault().getPreferenceStore().setValue(
                        PALETTE_STATE, state);
            }

            public void setPaletteWidth(int width) {
                FastidePlugin.getDefault().getPreferenceStore().setValue(
                        PALETTE_SIZE, width);
            }
        };
    }

    protected PaletteRoot getPaletteRoot() {
        if (root == null) {
            root = FastidePlugin.createPalette();
        }
        return root;
    }

    public void gotoMarker(IMarker marker) {
    }

    protected void handleActivationChanged(Event event) {
        IAction copy = null;
        if (event.type == SWT.Deactivate)
            copy = getActionRegistry().getAction(ActionFactory.COPY.getId());
        if (getEditorSite().getActionBars().getGlobalActionHandler(
                ActionFactory.COPY.getId()) != copy) {
            getEditorSite().getActionBars().setGlobalActionHandler(
                    ActionFactory.COPY.getId(), copy);
            getEditorSite().getActionBars().updateActionBars();
        }
    }

    protected void initializeGraphicalViewer() {
        super.initializeGraphicalViewer();
        getGraphicalViewer().setContents(getFastDiagram());

        getGraphicalViewer()
                .addDropTargetListener(
                        new FastTemplateTransferDropTargetListener(
                                getGraphicalViewer()));
        getGraphicalViewer().addDropTargetListener(
                new TextTransferDropTargetListener(getGraphicalViewer(),
                        TextTransfer.getInstance()));
    }

    protected void createActions() {
        super.createActions();
        ActionRegistry registry = getActionRegistry();
        IAction action;

        action = new CopyTemplateAction(this);
        registry.registerAction(action);

        action = new MatchWidthAction(this);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new MatchHeightAction(this);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new FastPasteTemplateAction(this);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new DirectEditAction((IWorkbenchPart) this);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this,
                PositionConstants.LEFT);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this,
                PositionConstants.RIGHT);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this,
                PositionConstants.TOP);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this,
                PositionConstants.BOTTOM);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this,
                PositionConstants.CENTER);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());

        action = new AlignmentAction((IWorkbenchPart) this,
                PositionConstants.MIDDLE);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#createGraphicalViewer(org.eclipse.swt.widgets.Composite)
     */
    protected void createGraphicalViewer(Composite parent) {
        rulerComp = new RulerComposite(parent, SWT.NONE);
        super.createGraphicalViewer(rulerComp);
        rulerComp
                .setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
    }

    protected FigureCanvas getEditor() {
        return (FigureCanvas) getGraphicalViewer().getControl();
    }

    public boolean isSaveAsAllowed() {
        return true;
    }

    protected void loadProperties() {
        // Ruler properties
        FastRuler ruler = getFastDiagram().getRuler(PositionConstants.WEST);
        RulerProvider provider = null;
        if (ruler != null) {
            provider = new FastRulerProvider(ruler);
        }
        getGraphicalViewer().setProperty(RulerProvider.PROPERTY_VERTICAL_RULER,
                provider);
        ruler = getFastDiagram().getRuler(PositionConstants.NORTH);
        provider = null;
        if (ruler != null) {
            provider = new FastRulerProvider(ruler);
        }
        getGraphicalViewer().setProperty(
                RulerProvider.PROPERTY_HORIZONTAL_RULER, provider);
        getGraphicalViewer().setProperty(
                RulerProvider.PROPERTY_RULER_VISIBILITY,
                new Boolean(getFastDiagram().getRulerVisibility()));

        // Snap to Geometry property
        getGraphicalViewer().setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED,
                new Boolean(getFastDiagram().isSnapToGeometryEnabled()));

        // Grid properties
        getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED,
                new Boolean(getFastDiagram().isGridEnabled()));
        // We keep grid visibility and enablement in sync
        getGraphicalViewer().setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE,
                new Boolean(getFastDiagram().isGridEnabled()));

        // Zoom
        ZoomManager manager = (ZoomManager) getGraphicalViewer().getProperty(
                ZoomManager.class.toString());
        if (manager != null)
            manager.setZoom(getFastDiagram().getZoom());
        // Scroll-wheel Zoom
        getGraphicalViewer().setProperty(
                MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1),
                MouseWheelZoomHandler.SINGLETON);

    }

    protected boolean performSaveAs() {
        SaveAsDialog dialog = new SaveAsDialog(getSite().getWorkbenchWindow()
                .getShell());
        dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
        dialog.open();
        IPath path = dialog.getResult();

        if (path == null)
            return false;

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IFile file = workspace.getRoot().getFile(path);

        if (!file.exists()) {
            WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
                public void execute(final IProgressMonitor monitor) {
                    saveProperties();
                    try {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        createOutputStream(out);
                        file.create(
                                new ByteArrayInputStream(out.toByteArray()),
                                true, monitor);
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            try {
                new ProgressMonitorDialog(getSite().getWorkbenchWindow()
                        .getShell()).run(false, true, op);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            superSetInput(new FileEditorInput(file));
            getCommandStack().markSaveLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean savePreviouslyNeeded() {
        return savePreviouslyNeeded;
    }

    protected void saveProperties() {
        getFastDiagram().setRulerVisibility(
                ((Boolean) getGraphicalViewer().getProperty(
                        RulerProvider.PROPERTY_RULER_VISIBILITY))
                        .booleanValue());
        getFastDiagram().setGridEnabled(
                ((Boolean) getGraphicalViewer().getProperty(
                        SnapToGrid.PROPERTY_GRID_ENABLED)).booleanValue());
        getFastDiagram().setSnapToGeometry(
                ((Boolean) getGraphicalViewer().getProperty(
                        SnapToGeometry.PROPERTY_SNAP_ENABLED)).booleanValue());
        ZoomManager manager = (ZoomManager) getGraphicalViewer().getProperty(
                ZoomManager.class.toString());
        if (manager != null)
            getFastDiagram().setZoom(manager.getZoom());
    }

    public void setInput(IEditorInput input) {
        superSetInput(input);

        FastEditorInput in = (FastEditorInput) input;
        setFastDiagram(in.getDiagram());
        setPartName(in.getName());

        if (!editorSaving) {
            if (getGraphicalViewer() != null) {
                getGraphicalViewer().setContents(getFastDiagram());
                loadProperties();
            }
            if (outlinePage != null) {
                outlinePage.setContents(getFastDiagram());
            }
        }
    }

    public void setFastDiagram(FastDiagram diagram) {
        fastDiagram = diagram;
    }

    private void setSavePreviouslyNeeded(boolean value) {
        savePreviouslyNeeded = value;
    }

    protected void superSetInput(IEditorInput input) {
        // The workspace never changes for an editor. So, removing and re-adding
        // the
        // resourceListener is not necessary. But it is being done here for the
        // sake
        // of proper implementation. Plus, the resourceListener needs to be
        // added
        // to the workspace the first time around.
        super.setInput(input);
    }
}
