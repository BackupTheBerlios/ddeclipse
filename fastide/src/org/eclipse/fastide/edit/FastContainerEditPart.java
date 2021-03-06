/**
 *
 */
package org.eclipse.fastide.edit;

import java.util.List;

import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.fastide.model.FastDiagram;

/**
 * @author ������
 */
public abstract class FastContainerEditPart extends FastEditPart {

    /**
     * @see org.eclipse.fastide.edit.FastEditPart#createAccessible()
     */
    protected AccessibleEditPart createAccessible() {
        // TODO Auto-generated method stub
        return new AccessibleGraphicalEditPart() {
            public void getName(AccessibleEvent e) {
                e.result = getFastDiagram().toString();
            }
        };
    }

    /**
     * Installs the desired EditPolicies for this.
     */
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.CONTAINER_ROLE,
                new FastContainerEditPolicy());
    }

    /**
     * Returns the model of this as a FastDiagram.
     * 
     * @return FastDiagram of this.
     */
    protected FastDiagram getFastDiagram() {
        return (FastDiagram) getModel();
    }

    /**
     * Returns the children of this through the model.
     * 
     * @return Children of this as a List.
     */
    protected List getModelChildren() {
        return getFastDiagram().getChildren();
    }
}
