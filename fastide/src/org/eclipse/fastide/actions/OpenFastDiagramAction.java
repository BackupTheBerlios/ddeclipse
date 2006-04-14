package org.eclipse.fastide.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        super("Open FAST Diagram");
        this.window = window;
    }

    public void run() {
        // TODO Auto-generated method stub
        FileDialog fd = new FileDialog(window.getShell(), SWT.SAVE);
        fd.setFilterExtensions(new String[] { "*.fst", "*.*" });
        fd.setText("New...");

        if (fd.open() != null) {
            String fileName = fd.getFileName();
            File file = new File(fd.getFilterPath() + "/" + fileName);
            InputStream fileStream = null;
            FastDiagram diagram = null;
            try {
                fileStream = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileStream);
                diagram = (FastDiagram) in.readObject();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            IWorkbenchPage page = window.getActivePage();
            Path path = new Path(file.getAbsolutePath());
            FastEditorInput input = new FastEditorInput(path);
            input.setDiagram(diagram);
            try {
                page.openEditor(input, "org.eclipse.fastide.fasteditor");
            } catch (PartInitException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String getId() {
        // TODO Auto-generated method stub
        return "org.eclipse.fastide.openAction";
    }
}
