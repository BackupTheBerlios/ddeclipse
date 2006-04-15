package org.eclipse.fastide.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fastide.model.EndNode;

public class EndNodeFigure extends FastNodeFigure {
    public EndNodeFigure() {
        FixedConnectionAnchor c = new FixedConnectionAnchor(this);
        c.offsetV = 9;
        connectionAnchors.put(EndNode.IN1, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 9;
        connectionAnchors.put(EndNode.IN2, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 9;
        c.leftToRight = false;
        connectionAnchors.put(EndNode.IN3, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 9;
        c.topDown = false;
        connectionAnchors.put(EndNode.IN4, c);
        inputConnectionAnchors.addElement(c);
    }

    /**
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics g) {
        // TODO Auto-generated method stub
        super.paintFigure(g);
        Rectangle r = getBounds().getCopy();
        g.setForegroundColor(ColorConstants.blue);
        r.width = r.width - 2;
        r.height = r.height - 2;
        g.drawOval(r);
        r.resize(-6, -6);
        r.translate(3, 3);
        g.setForegroundColor(ColorConstants.black);
        g.drawOval(r);
    }

    public String toString() {
        return "EndFigure";
    }
}
