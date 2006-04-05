/**
 *
 */
package org.eclipse.fastide.editors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.FastDiagramFactory;

/**
 * @author …Ú»›÷€
 */
public class FastWizardPage extends WizardNewFileCreationPage {

    private IWorkbench workbench;

    private static int exampleCount = 1;

    public FastWizardPage(IWorkbench aWorkbench, IStructuredSelection selection) {
        super("sampleFastPage", selection);
        this.setTitle("Create FAST Diagram");
        this.setDescription("Create FAST Diagram");
        this.setImageDescriptor(ImageDescriptor.createFromFile(getClass(),
                "icons/logicbanner.gif"));
        this.workbench = aWorkbench;
    }

    public void createControl(Composite parent) {
        super.createControl(parent);
        this.setFileName("emptyModel" + exampleCount + ".fst"); //$NON-NLS-2$//$NON-NLS-1$
        setPageComplete(validatePage());
    }

    protected InputStream getInitialContents() {
        FastDiagram ld = new FastDiagram();
        ld = (FastDiagram) FastDiagramFactory.createEmptyModel();
        ByteArrayInputStream bais = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(ld);
            oos.flush();
            oos.close();
            baos.close();
            bais = new ByteArrayInputStream(baos.toByteArray());
            bais.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bais;
    }

    public boolean finish() {
        IFile newFile = createNewFile();
        if (newFile == null)
            return false; // ie.- creation was unsuccessful

        // Since the file resource was created fine, open it for editing
        // iff requested by the user
        try {
            IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
            IWorkbenchPage page = dwindow.getActivePage();
            if (page != null)
                IDE.openEditor(page, newFile, true);
        } catch (org.eclipse.ui.PartInitException e) {
            e.printStackTrace();
            return false;
        }
        exampleCount++;
        return true;
    }
}
