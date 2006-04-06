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
        c.offsetV = 19;
        connectionAnchors.put(FunctionNode.IN, c);
        inputConnectionAnchors.addElement(c);

        c = new FixedConnectionAnchor(this);
        c.offsetV = 19;
        c.leftToRight = false;
        connectionAnchors.put(FunctionNode.OUT, c);
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
        g.drawRectangle(r);
        r.resize(-16, -16);
        r.translate(8, 8);
        g.setForegroundColor(ColorConstants.blue);
        g.drawRectangle(r);
    }

    public String toString() {
        return "FunctionFigure";
    }
}
