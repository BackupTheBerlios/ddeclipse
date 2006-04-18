/**
 * 
 */
package org.eclipse.fastide.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.eclipse.core.runtime.Path;
import org.eclipse.fastide.editors.FastEditorInput;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

/**
 * @author …Ú»›÷€
 */
public class OpenFileListener implements MouseListener {
    private IWorkbenchWindow window;

    public OpenFileListener(IWorkbenchWindow window) {
        this.window = window;
    }

    public void mouseDoubleClick(MouseEvent e) {
        IWorkbenchPage page = window.getActivePage();
        Tree tree = (Tree) e.getSource();
        TreeItem item = tree.getSelection()[0];
        File file = (File) item.getData("file");
        IEditorInput input = (IEditorInput) item.getData("input");
        IEditorPart editor = page.findEditor(input);
        if (editor != null) {
            page.bringToTop(editor);
            editor.setFocus();
        } else {
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

    public void mouseDown(MouseEvent e) {

    }

    public void mouseUp(MouseEvent e) {

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
