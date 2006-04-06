/**
 *
 */
package org.eclipse.fastide.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fastide.figures.EndNodeFigure;
import org.eclipse.fastide.figures.FunctionNodeFigure;
import org.eclipse.fastide.figures.JoinpointNodeFigure;
import org.eclipse.fastide.figures.PredicateNodeFigure;
import org.eclipse.fastide.figures.StartNodeFigure;
import org.eclipse.fastide.model.FunctionNode;
import org.eclipse.fastide.model.JoinpointNode;
import org.eclipse.fastide.model.PredicateNode;
import org.eclipse.fastide.model.SimpleNode;
import org.eclipse.fastide.model.StartNode;
import org.eclipse.gef.AccessibleAnchorProvider;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.swt.accessibility.AccessibleEvent;

/**
 * @author …Ú»›÷€
 */
public class FastNodeEditPart extends FastEditPart {

    /**
     * @see org.eclipse.fastide.edit.FastEditPart#createAccessible()
     */
    protected AccessibleEditPart createAccessible() {
        // TODO Auto-generated method stub
        return new AccessibleGraphicalEditPart() {
            public void getName(AccessibleEvent e) {
                e.result = getSimpleNode().toString();
            }
        };
    }

    /**
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
     */
    protected IFigure createFigure() {
        // TODO Auto-generated method stub
        if (getModel() instanceof FunctionNode)
            return new FunctionNodeFigure();
        else if (getModel() instanceof PredicateNode)
            return new PredicateNodeFigure();
        else if (getModel() instanceof JoinpointNode)
            return new JoinpointNodeFigure();
        else if (getModel() instanceof StartNode)
            return new StartNodeFigure();
        else
            return new EndNodeFigure();
    }

    /**
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class key) {
        // TODO Auto-generated method stub
        if (key == AccessibleAnchorProvider.class)
            return new DefaultAccessibleAnchorProvider() {
                public List getSourceAnchorLocations() {
                    List list = new ArrayList();
                    Vector sourceAnchors = getNodeFigure()
                            .getSourceConnectionAnchors();
                    for (int i = 0; i < sourceAnchors.size(); i++) {
                        ConnectionAnchor anchor = (ConnectionAnchor) sourceAnchors
                                .get(i);
                        list.add(anchor.getReferencePoint()
                                .getTranslated(0, -3));
                    }
                    return list;
                }

                public List getTargetAnchorLocations() {
                    List list = new ArrayList();
                    Vector targetAnchors = getNodeFigure()
                            .getTargetConnectionAnchors();
                    for (int i = 0; i < targetAnchors.size(); i++) {
                        ConnectionAnchor anchor = (ConnectionAnchor) targetAnchors
                                .get(i);
                        list.add(anchor.getReferencePoint());
                    }
                    return list;
                }
            };
        return super.getAdapter(key);
    }

    public SimpleNode getSimpleNode() {
        return (SimpleNode) getModel();
    }
}
