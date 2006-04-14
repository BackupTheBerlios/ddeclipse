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
        c.offsetH = 19;
        connectionAnchors.put(JoinpointNode.IN_1, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 19;
        c.topDown = false;
        connectionAnchors.put(JoinpointNode.IN_2, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetV = 19;
        c.leftToRight = false;
        connectionAnchors.put(JoinpointNode.OUT_2, c);
        outputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetV = 19;
        connectionAnchors.put(JoinpointNode.OUT_1, c);
        outputConnectionAnchors.addElement(c);
    }

    /**
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics g) {
        // TODO Auto-generated method stub
        super.paintFigure(g);
        Rectangle r = getBounds().getCopy();
        g.setForegroundColor(ColorConstants.black);
        r.width = r.width - 2;
        r.height = r.height - 2;
        g.drawOval(r);
        r.resize(-16, -16);
        r.translate(8, 8);
        g.setForegroundColor(ColorConstants.blue);
        g.drawOval(r);
    }

    public String toString() {
        return "JoinpointFigure";
    }
}
