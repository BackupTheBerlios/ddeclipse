package org.eclipse.fastide;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fastide.model.EndNode;
import org.eclipse.fastide.model.FunctionNode;
import org.eclipse.fastide.model.JoinpointNode;
import org.eclipse.fastide.model.PredicateNode;
import org.eclipse.fastide.model.SimpleNode;
import org.eclipse.fastide.model.StartNode;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class FastidePlugin extends AbstractUIPlugin {

    // The shared instance.
    private static FastidePlugin plugin;

    /**
     * The constructor.
     */
    public FastidePlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static FastidePlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(
                "org.eclipse.fastide", path);
    }

    public static PaletteRoot createPalette() {
        PaletteRoot fastPalette = new PaletteRoot();
        fastPalette.addAll(createCategories(fastPalette));
        return fastPalette;
    }

    static private List createCategories(PaletteRoot root) {
        List categories = new ArrayList();

        categories.add(createControlGroup(root));
        categories.add(createComponentsDrawer());
        // categories.add(createTemplateComponentsDrawer());
        // categories.add(createComplexTemplatePartsDrawer());
        return categories;
    }

    static private PaletteContainer createComponentsDrawer() {

        PaletteDrawer drawer = new PaletteDrawer("Fast Nodes", ImageDescriptor
                .createFromFile(SimpleNode.class, "icons/comp.gif"));//$NON-NLS-1$

        List entries = new ArrayList();

        CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
                "Function Node", "Function Node", "function node template",
                new SimpleFactory(FunctionNode.class), ImageDescriptor
                        .createFromFile(SimpleNode.class, "icons/xor16.gif"), //$NON-NLS-1$
                ImageDescriptor.createFromFile(SimpleNode.class,
                        "icons/xor24.gif")//$NON-NLS-1$
        );
        entries.add(combined);

        combined = new CombinedTemplateCreationEntry("Predicate Node",
                "Predicate Node", "predicate node template", new SimpleFactory(
                        PredicateNode.class), ImageDescriptor.createFromFile(
                        SimpleNode.class, "icons/or16.gif"),//$NON-NLS-1$
                ImageDescriptor.createFromFile(SimpleNode.class,
                        "icons/or24.gif")//$NON-NLS-1$
        );
        entries.add(combined);

        combined = new CombinedTemplateCreationEntry("Joinpoint Node",
                "Joinpoint Node", "joinpoint node template", new SimpleFactory(
                        JoinpointNode.class), ImageDescriptor.createFromFile(
                        SimpleNode.class, "icons/and16.gif"),//$NON-NLS-1$
                ImageDescriptor.createFromFile(SimpleNode.class,
                        "icons/and24.gif"));
        entries.add(combined);

        combined = new CombinedTemplateCreationEntry("Start Node",
                "Start Node", "start node template", new SimpleFactory(
                        StartNode.class), ImageDescriptor.createFromFile(
                        SimpleNode.class, "icons/and16.gif"), ImageDescriptor
                        .createFromFile(SimpleNode.class, "icons/and24.gif"));
        entries.add(combined);

        combined = new CombinedTemplateCreationEntry("End Node", "End Node",
                "end node template", new SimpleFactory(EndNode.class),
                ImageDescriptor.createFromFile(SimpleNode.class,
                        "icons/and16.gif"), ImageDescriptor.createFromFile(
                        SimpleNode.class, "icons/and24.gif"));
        entries.add(combined);

        drawer.addAll(entries);
        return drawer;
    }

    static private PaletteContainer createControlGroup(PaletteRoot root) {
        PaletteGroup controlGroup = new PaletteGroup("Controls");

        List entries = new ArrayList();

        ToolEntry tool = new PanningSelectionToolEntry();
        entries.add(tool);
        root.setDefaultEntry(tool);

        PaletteStack marqueeStack = new PaletteStack("Marquee", "", null); //$NON-NLS-1$
        marqueeStack.add(new MarqueeToolEntry());
        MarqueeToolEntry marquee = new MarqueeToolEntry();
        marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
                new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED));
        marqueeStack.add(marquee);
        marquee = new MarqueeToolEntry();
        marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
                new Integer(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED
                        | MarqueeSelectionTool.BEHAVIOR_NODES_CONTAINED));
        marqueeStack.add(marquee);
        marqueeStack
                .setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
        entries.add(marqueeStack);

        tool = new ConnectionCreationToolEntry("Connection", "Connection",
                null, ImageDescriptor.createFromFile(SimpleNode.class,
                        "icons/connection16.gif"),//$NON-NLS-1$
                ImageDescriptor.createFromFile(SimpleNode.class,
                        "icons/connection24.gif")//$NON-NLS-1$
        );
        entries.add(tool);
        controlGroup.addAll(entries);
        return controlGroup;
    }
}
