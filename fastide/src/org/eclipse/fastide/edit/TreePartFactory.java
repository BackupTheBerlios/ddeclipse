/**
 *
 */
package org.eclipse.fastide.edit;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.SimpleNode;

/**
 * @author …Ú»›÷€
 */
public class TreePartFactory implements EditPartFactory {

    /**
     * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
     *      java.lang.Object)
     */
    public EditPart createEditPart(EditPart context, Object model) {
        // TODO Auto-generated method stub
        if(model instanceof FastDiagram)
            return new FastContainerTreeEditPart(model);
        else if(model instanceof SimpleNode)
            return new FastTreeEditPart(model);
        return new FastTreeEditPart(model);
    }
}
