package org.eclipse.fastide.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fastide.model.StartNode;

public class StartNodeFigure extends NodeFigure {
    public StartNodeFigure() {
        FixedConnectionAnchor c = new FixedConnectionAnchor(this);
        c.offsetV = 9;
        connectionAnchors.put(StartNode.OUT1, c);
        outputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 9;
        connectionAnchors.put(StartNode.OUT2, c);
        outputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 9;
        c.leftToRight = false;
        connectionAnchors.put(StartNode.OUT3, c);
        outputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 9;
        c.topDown = false;
        connectionAnchors.put(StartNode.OUT4, c);
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
        r.resize(-6, -6);
        r.translate(3, 3);
        g.setForegroundColor(ColorConstants.blue);
        g.drawOval(r);
    }

    public String toString() {
        return "StartFigure";
    }
}
