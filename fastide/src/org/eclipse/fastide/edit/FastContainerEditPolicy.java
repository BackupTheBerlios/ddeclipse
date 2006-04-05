/**
 *
 */
package org.eclipse.fastide.edit;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.FastSubpart;
import org.eclipse.fastide.model.commands.OrphanChildCommand;

/**
 * @author ������
 */
public class FastContainerEditPolicy extends ContainerEditPolicy {

    /**
     * @see org.eclipse.gef.editpolicies.ContainerEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
     */
    protected Command getCreateCommand(CreateRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    public Command getOrphanChildrenCommand(GroupRequest request) {
        List parts = request.getEditParts();
        CompoundCommand result = new CompoundCommand("orphan child");
        for (int i = 0; i < parts.size(); i++) {
            OrphanChildCommand orphan = new OrphanChildCommand();
            orphan.setChild((FastSubpart) ((EditPart) parts.get(i)).getModel());
            orphan.setParent((FastDiagram) getHost().getModel());
            orphan.setLabel("orphan child");
            result.add(orphan);
        }
        return result.unwrap();
    }
}
