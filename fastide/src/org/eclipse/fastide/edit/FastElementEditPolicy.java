/**
 *
 */
package org.eclipse.fastide.edit;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.FastSubpart;
import org.eclipse.fastide.model.commands.FastDeleteCommand;

/**
 * @author …Ú»›÷€
 */
public class FastElementEditPolicy extends ComponentEditPolicy {
    protected Command createDeleteCommand(GroupRequest request) {
        Object parent = getHost().getParent().getModel();
        FastDeleteCommand deleteCmd = new FastDeleteCommand();
        deleteCmd.setParent((FastDiagram) parent);
        deleteCmd.setChild((FastSubpart) getHost().getModel());
        return deleteCmd;
    }
}
