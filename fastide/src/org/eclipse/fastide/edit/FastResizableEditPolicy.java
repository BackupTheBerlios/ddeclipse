/**
 *
 */
package org.eclipse.fastide.edit;

import java.util.Iterator;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.fastide.figures.FunctionNodeFeedbackFigure;
import org.eclipse.fastide.figures.JoinpointNodeFeedbackFigure;
import org.eclipse.fastide.figures.PredicateNodeFeedbackFigure;
import org.eclipse.fastide.model.FunctionNode;
import org.eclipse.fastide.model.JoinpointNode;
import org.eclipse.fastide.model.PredicateNode;

/**
 * @author …Ú»›÷€
 */
public class FastResizableEditPolicy extends ResizableEditPolicy {
    /**
     * Creates the figure used for feedback.
     * @return the new feedback figure
     */
    protected IFigure createDragSourceFeedbackFigure() {
        IFigure figure = createFigure((GraphicalEditPart) getHost(), null);

        figure.setBounds(getInitialFeedbackBounds());
        addFeedback(figure);
        return figure;
    }

    protected IFigure createFigure(GraphicalEditPart part, IFigure parent) {
        IFigure child = getCustomFeedbackFigure(part.getModel());

        if(parent != null)
            parent.add(child);

        Rectangle childBounds = part.getFigure().getBounds().getCopy();

        IFigure walker = part.getFigure().getParent();

        while (walker != ((GraphicalEditPart) part.getParent()).getFigure()) {
            walker.translateToParent(childBounds);
            walker = walker.getParent();
        }

        child.setBounds(childBounds);

        Iterator i = part.getChildren().iterator();

        while (i.hasNext())
            createFigure((GraphicalEditPart) i.next(), child);

        return child;
    }

    protected IFigure getCustomFeedbackFigure(Object modelPart) {
        IFigure figure;

        if(modelPart instanceof FunctionNode)
            figure = new FunctionNodeFeedbackFigure();
        else if(modelPart instanceof PredicateNode)
            figure = new PredicateNodeFeedbackFigure();
        else if(modelPart instanceof JoinpointNode)
            figure = new JoinpointNodeFeedbackFigure();
        else {
            figure = new RectangleFigure();
            ((RectangleFigure) figure).setXOR(true);
            ((RectangleFigure) figure).setFill(true);
            figure.setBackgroundColor(ColorConstants.gray);
            figure.setForegroundColor(ColorConstants.white);
        }

        return figure;
    }

    /**
     * Returns the layer used for displaying feedback.
     * @return the feedback layer
     */
    protected IFigure getFeedbackLayer() {
        return getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
    }

    /**
     * @see org.eclipse.gef.editpolicies.NonResizableEditPolicy#getInitialFeedbackBounds()
     */
    protected Rectangle getInitialFeedbackBounds() {
        return getHostFigure().getBounds();
    }
}
