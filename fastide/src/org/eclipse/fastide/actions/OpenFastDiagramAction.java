package org.eclipse.fastide.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.eclipse.core.runtime.Path;
import org.eclipse.fastide.editors.FastEditorInput;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public class OpenFastDiagramAction extends Action {
    private IWorkbenchWindow window;

    public OpenFastDiagramAction(IWorkbenchWindow window) {
        super("&Open");

        this.window = window;
        setId("org.eclipse.fastide.openAction");
        setActionDefinitionId("org.eclipse.fastide.openAction");
    }

    public void run() {
        String fileName = openDialog();
        if (fileName != null) {
            File file = new File(fileName);
            try {
                FastDiagram diagram = getDiagram(file);
                openEditor(diagram, file);
            } catch (PartInitException exception) {
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * Opens the file dialog and allow the user to enter a file name for the
     * file to be opened.
     * </p>
     * 
     * @return The absolute path of the file selected.
     */
    private String openDialog() {
        FileDialog fileDialog = new FileDialog(window.getShell(), SWT.SAVE);
        fileDialog.setFilterExtensions(new String[] { "*.fst" });
        fileDialog.setText("Open...");
        return fileDialog.open();
    }

    /**
     * Obtain the FAST diagram from the specific file given.
     * 
     * @param file
     *            The file that the FAST diagram is obtained
     * @return The FAST Diagram obtained from the file
     * @throws IOException
     */
    private FastDiagram getDiagram(File file) throws IOException {
        InputStream fileStream = null;
        FastDiagram diagram = null;
        fileStream = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileStream);
        try {
            diagram = (FastDiagram) in.readObject();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return diagram;
    }

    private void openEditor(FastDiagram diagram, File file)
            throws PartInitException {
        IWorkbenchPage page = window.getActivePage();
        Path path = new Path(file.getAbsolutePath());
        FastEditorInput input = new FastEditorInput(path);
        input.setDiagram(diagram);
        page.openEditor(input, "org.eclipse.fastide.fasteditor");
    }
}
