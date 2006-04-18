package org.eclipse.fastide.views;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fastide.editors.FastEditor;
import org.eclipse.fastide.listeners.OpenFileListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.ViewPart;

public class FastFilesView extends ViewPart {
    private Tree               tree;

    private TreeItem           files;

    private List               fileList;

    public static final String ID = "org.eclipse.fastide.views.filesView";

    public FastFilesView() {
        setPartName("Files");
        fileList = new ArrayList();
    }

    public void createPartControl(Composite parent) {
        tree = new Tree(parent, SWT.NONE);
        files = new TreeItem(tree, SWT.NONE);
        files.setText("Files");
        tree.addMouseListener(new OpenFileListener(getSite()
                .getWorkbenchWindow()));
    }

    public void setFocus() {

    }

    public void addFile(File file, IEditorInput input) {
        if (!fileList.contains(file)) {
            fileList.add(file);
            TreeItem fileItem = new TreeItem(files, SWT.NONE);
            fileItem.setText(file.getName());
            fileItem.setImage(createImage(FastEditor.class, "icons/logic.gif"));
            fileItem.setData("file", file);
            fileItem.setData("input", input);
        }
    }

    public void removeFile(File file) {

    }

    private Image createImage(Class rsrcClass, String name) {
        InputStream stream = rsrcClass.getResourceAsStream(name);
        Image image = new Image(null, stream);
        try {
            stream.close();
        } catch (IOException ioe) {
        }
        return image;
    }
}
