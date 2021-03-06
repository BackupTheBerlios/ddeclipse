/**
 *
 */
package org.eclipse.fastide.edit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.fastide.figures.FigureFactory;
import org.eclipse.fastide.model.FastBendpoint;
import org.eclipse.fastide.model.FastConnection;

/**
 * @author ������
 */
public class ConnectionNodeEditPart extends AbstractConnectionEditPart
        implements PropertyChangeListener {

    AccessibleEditPart acc;

    public static final Color alive = new Color(Display.getDefault(), 0, 74,
                                            168), dead = new Color(Display
                                            .getDefault(), 0, 0, 0);

    public void activate() {
        super.activate();
        getConnectionNode().addPropertyChangeListener(this);
    }

    public void activateFigure() {
        super.activateFigure();
        /*
         * Once the figure has been added to the ConnectionLayer, start
         * listening for its router to change.
         */
        getFigure().addPropertyChangeListener(
                Connection.PROPERTY_CONNECTION_ROUTER, this);
    }

    /**
     * Adds extra EditPolicies as required.
     */
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
                new ConnectionNodeEndpointEditPolicy());
        // Note that the Connection is already added to the diagram and knows
        // its Router.
        refreshBendpointEditPolicy();
        installEditPolicy(EditPolicy.CONNECTION_ROLE,
                new ConnectionNodeEditPolicy());
    }

    /**
     * Returns a newly created Figure to represent the connection.
     * 
     * @return The created Figure.
     */
    protected IFigure createFigure() {
        if (getConnectionNode() == null)
            return null;
        Connection connx = FigureFactory
                .createNewBendableConnectionNode(getConnectionNode());
        return connx;
    }

    public void deactivate() {
        getConnectionNode().removePropertyChangeListener(this);
        super.deactivate();
    }

    public void deactivateFigure() {
        getFigure().removePropertyChangeListener(
                Connection.PROPERTY_CONNECTION_ROUTER, this);
        super.deactivateFigure();
    }

    public AccessibleEditPart getAccessibleEditPart() {
        if (acc == null)
            acc = new AccessibleGraphicalEditPart() {
                public void getName(AccessibleEvent e) {
                    e.result = "Connection";
                }
            };
        return acc;
    }

    /**
     * Returns the model of this represented as a ConnectionNode.
     * 
     * @return Model of this as <code>ConnectionNode</code>
     */
    protected FastConnection getConnectionNode() {
        return (FastConnection) getModel();
    }

    /**
     * Returns the Figure associated with this, which draws the ConnectionNode.
     * 
     * @return Figure of this.
     */
    protected IFigure getConnectionNodeFigure() {
        return (PolylineConnection) getFigure();
    }

    /**
     * Listens to changes in properties of the ConnectionNode (like the contents
     * being carried), and reflects is in the visuals.
     * 
     * @param event
     *            Event notifying the change.
     */
    public void propertyChange(PropertyChangeEvent event) {
        String property = event.getPropertyName();
        if (Connection.PROPERTY_CONNECTION_ROUTER.equals(property)) {
            refreshBendpoints();
            refreshBendpointEditPolicy();
        }
        if ("value".equals(property)) //$NON-NLS-1$
            refreshVisuals();
        if ("bendpoint".equals(property)) //$NON-NLS-1$
            refreshBendpoints();
    }

    /**
     * Updates the bendpoints, based on the model.
     */
    protected void refreshBendpoints() {
        if (getConnectionFigure().getConnectionRouter() instanceof ManhattanConnectionRouter)
            return;
        List modelConstraint = getConnectionNode().getBendpoints();
        List figureConstraint = new ArrayList();
        for (int i = 0; i < modelConstraint.size(); i++) {
            FastBendpoint cbp = (FastBendpoint) modelConstraint
                    .get(i);
            RelativeBendpoint rbp = new RelativeBendpoint(getConnectionFigure());
            rbp.setRelativeDimensions(cbp.getFirstRelativeDimension(), cbp
                    .getSecondRelativeDimension());
            rbp.setWeight((i + 1) / ((float) modelConstraint.size() + 1));
            figureConstraint.add(rbp);
        }
        getConnectionFigure().setRoutingConstraint(figureConstraint);
    }

    private void refreshBendpointEditPolicy() {
        if (getConnectionFigure().getConnectionRouter() instanceof ManhattanConnectionRouter)
            installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, null);
        else
            installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
                    new ConnectionNodeBendpointEditPolicy());
    }

    /**
     * Refreshes the visual aspects of this, based upon the model
     * (ConnectionNode). It changes the wire color depending on the state of
     * ConnectionNode.
     */
    protected void refreshVisuals() {
        refreshBendpoints();
        if (getConnectionNode().getValue())
            getConnectionNodeFigure().setForegroundColor(alive);
        else
            getConnectionNodeFigure().setForegroundColor(dead);
    }
}
