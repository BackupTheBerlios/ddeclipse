/**
 *
 */
package org.eclipse.fastide.editors;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * @author …Ú»›÷€
 */
public class FastCreationWizard extends Wizard implements INewWizard {

    private FastWizardPage       fastPage = null;

    private IStructuredSelection selection;

    private IWorkbench           workbench;

    public void addPages() {
        fastPage = new FastWizardPage(workbench, selection);
        addPage(fastPage);
    }

    public void init(IWorkbench aWorkbench,
            IStructuredSelection currentSelection) {
        workbench = aWorkbench;
        selection = currentSelection;
    }

    public boolean performFinish() {
        return fastPage.finish();
    }
}
