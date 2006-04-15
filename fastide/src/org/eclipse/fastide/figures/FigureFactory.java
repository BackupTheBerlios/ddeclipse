/**
 *
 */
package org.eclipse.fastide.figures;

import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.fastide.model.FastConnection;

/**
 * @author …Ú»›÷€
 */
public class FigureFactory {
    public static PolylineConnection createNewBendableConnectionNode(
            FastConnection wire) {
        PolylineConnection conn = new PolylineConnection();
        // conn.setSourceDecoration(new PolygonDecoration());
        conn.setTargetDecoration(new PolylineDecoration());
        return conn;
    }

    public static PolylineConnection createNewWire(FastConnection wire) {

        PolylineConnection conn = new PolylineConnection();
        PolygonDecoration arrow;
        arrow = new PolygonDecoration();
        arrow.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
        arrow.setScale(5, 2.5);
        conn.setTargetDecoration(arrow);
        return conn;
    }
}
