/**
 *
 */
package org.eclipse.fastide.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fastide.model.PredicateNode;

/**
 * @author …Ú»›÷€
 */
public class PredicateNodeFigure extends NodeFigure {
    private static PointList kite = null;

    static {
        kite = new PointList();
        kite.addPoint(24, 0);
        kite.addPoint(0, 24);
        kite.addPoint(24, 48);
        kite.addPoint(48, 24);
    }

    public PredicateNodeFigure() {
        FixedConnectionAnchor c = new FixedConnectionAnchor(this);
        c.offsetV = 25;
        connectionAnchors.put(PredicateNode.IN, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 25;
        connectionAnchors.put(PredicateNode.OUT_1, c);
        outputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetH = 25;
        c.topDown = false;
        connectionAnchors.put(PredicateNode.OUT_2, c);
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
        g.fillPolygon(kite);
        g.drawPolygon(kite);
    }

    public String toString() {
        return "PredicateFigure";
    }
}
