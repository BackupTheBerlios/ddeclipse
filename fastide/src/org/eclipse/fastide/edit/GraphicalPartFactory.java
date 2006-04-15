/**
 *
 */
package org.eclipse.fastide.edit;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.fastide.model.FastConnection;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.FastNode;

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
        if (model instanceof FastConnection)
            child = new ConnectionNodeEditPart();
        else if (model instanceof FastNode)
            child = new FastNodeEditPart();
        else if (model instanceof FastDiagram)
            child = new FastDiagramEditPart();
        child.setModel(model);
        return child;
    }
}
