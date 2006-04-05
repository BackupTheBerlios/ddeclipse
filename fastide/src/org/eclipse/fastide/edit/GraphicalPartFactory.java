/**
 *
 */
package org.eclipse.fastide.edit;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.fastide.model.ConnectionNode;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.SimpleNode;

/**
 * @author …Ú»›÷€
 */
public class GraphicalPartFactory implements EditPartFactory {

    /**
     * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
     *      java.lang.Object)
     */
    public EditPart createEditPart(EditPart context, Object model) {
        // TODO Auto-generated method stub
        EditPart child = null;
        if(model instanceof ConnectionNode)
            child = new ConnectionNodeEditPart();
        else if(model instanceof SimpleNode)
            child = new FastNodeEditPart();
        else if(model instanceof FastDiagram)
            child = new FastDiagramEditPart();
        child.setModel(model);
        return child;
    }
}
