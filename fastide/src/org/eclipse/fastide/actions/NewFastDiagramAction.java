package org.eclipse.fastide.actions;

import java.io.File;

import org.eclipse.core.runtime.Path;
import org.eclipse.fastide.editors.FastEditorInput;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public class NewFastDiagramAction extends Action {
    private IWorkbenchWindow window;

    public NewFastDiagramAction(IWorkbenchWindow window) {
        super("New Fast Diagram...");
        this.window = window;
    }

    public void run() {
        // TODO Auto-generated method stub
        FileDialog fd = new FileDialog(window.getShell(), SWT.SAVE);
        fd.setFilterExtensions(new String[] { "*.fst" });
        fd.setText("New...");

        if (fd.open() != null) {
            String fileName = fd.getFileName();

            if (!fileName.endsWith(".fst"))
                fileName = fileName.concat(".fst");
            File file = new File(fd.getFilterPath() + "/" + fileName);
            IWorkbenchPage page = window.getActivePage();
            Path path = new Path(file.getAbsolutePath());
            FastEditorInput input = new FastEditorInput(path);
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
        return "org.eclipse.fastide.newAction";
    }
}
