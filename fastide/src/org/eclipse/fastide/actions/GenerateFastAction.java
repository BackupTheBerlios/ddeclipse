/**
 * 
 */
package org.eclipse.fastide.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.fastide.editors.FastEditorInput;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * @author …Ú»›÷€
 */
public class GenerateFastAction extends Action {
    private IWorkbenchWindow window;

    private String           filePath;

    private File             directory;

    public GenerateFastAction(IWorkbenchWindow window) {
        super("&Run");
        this.window = window;
        setId("org.eclipse.fastide.actions.runAction");
    }

    public void run() {
        // TODO Auto-generated method stub
        FastEditorInput input = (FastEditorInput) window.getActivePage()
                .getActiveEditor().getEditorInput();
        directory = input.getPath().removeLastSegments(1).toFile();
        filePath = input.getPath().removeFileExtension()
                .addFileExtension("fss").toOSString();
        try {
            Process process = Runtime.getRuntime().exec(
                    "D:/JSoftware/FAST/bin/fast.bat " + filePath, null,
                    directory);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
