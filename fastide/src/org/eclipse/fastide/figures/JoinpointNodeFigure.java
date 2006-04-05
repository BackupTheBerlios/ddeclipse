/**
 *
 */
package org.eclipse.fastide.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fastide.model.JoinpointNode;

/**
 * @author …Ú»›÷€
 */
public class JoinpointNodeFigure extends NodeFigure {
    public JoinpointNodeFigure() {
        FixedConnectionAnchor c = new FixedConnectionAnchor(this);
        c.offsetH = 25;
        connectionAnchors.put(JoinpointNode.IN_1, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 25;
        c.topDown = false;
        connectionAnchors.put(JoinpointNode.IN_2, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetV = 25;
        c.leftToRight = false;
        connectionAnchors.put(JoinpointNode.OUT, c);
        outputConnectionAnchors.addElement(c);
    }

    /**
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics g) {
        // TODO Auto-generated method stub
        Rectangle r = getBounds().getCopy();
        g.translate(r.getLocation());
        g.setBackgroundColor(ColorConstants.green);
        g.setForegroundColor(ColorConstants.black);
        g.fillOval(1, 1, r.width - 2, r.height - 2);
        g.drawOval(1, 1, r.width - 2, r.height - 2);
    }

    public String toString() {
        return "JoinpointFigure";
    }
}
