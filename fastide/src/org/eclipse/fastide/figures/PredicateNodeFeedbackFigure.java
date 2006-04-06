/**
 *
 */
package org.eclipse.fastide.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

/**
 * @author …Ú»›÷€
 */
public class PredicateNodeFeedbackFigure extends PredicateNodeFigure {

    /**
     * @see org.eclipse.fastide.figures.PredicateNodeFigure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics g) {
        // TODO Auto-generated method stub
        g.setAntialias(SWT.ON);
        Rectangle r = getBounds().getCopy();
        g.translate(r.getLocation());
        g.setForegroundColor(ColorConstants.black);
        g.drawPolygon(outerKite);
        g.setBackgroundColor(ColorConstants.gray);
        g.setAlpha(50);
        g.fillPolygon(outerKite);
    }
}
