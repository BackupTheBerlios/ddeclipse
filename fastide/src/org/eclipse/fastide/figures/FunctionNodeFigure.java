/**
 *
 */
package org.eclipse.fastide.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fastide.model.FunctionNode;

/**
 * @author …Ú»›÷€
 */
public class FunctionNodeFigure extends NodeFigure {
    public FunctionNodeFigure() {
        FixedConnectionAnchor c = new FixedConnectionAnchor(this);
        c.offsetV = 25;
        connectionAnchors.put(FunctionNode.IN, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetV = 25;
        c.leftToRight = false;
        connectionAnchors.put(FunctionNode.OUT, c);
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
        g.fillRectangle(1, 1, r.width - 2, r.height - 2);
        g.drawRectangle(1, 1, r.width - 2, r.height - 2);
    }

    public String toString() {
        return "FunctionFigure";
    }
}
