/**
 *
 */
package org.eclipse.fastide.figures;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;

/**
 * @author ������
 */
public class FastNodeFigure extends Figure {
    protected Hashtable connectionAnchors       = new Hashtable(7);

    protected Vector    inputConnectionAnchors  = new Vector(2, 2);

    protected Vector    outputConnectionAnchors = new Vector(2, 2);

    public ConnectionAnchor connectionAnchorAt(Point p) {
        ConnectionAnchor closest = null;
        long min = Long.MAX_VALUE;

        Enumeration e = getSourceConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor) e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        e = getTargetConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor) e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    public ConnectionAnchor getConnectionAnchor(String terminal) {
        return (ConnectionAnchor) connectionAnchors.get(terminal);
    }

    public String getConnectionAnchorName(ConnectionAnchor c) {
        Enumeration keys = connectionAnchors.keys();
        String key;
        while (keys.hasMoreElements()) {
            key = (String) keys.nextElement();
            if (connectionAnchors.get(key).equals(c))
                return key;
        }
        return null;
    }

    public ConnectionAnchor getSourceConnectionAnchorAt(Point p) {
        ConnectionAnchor closest = null;
        long min = Long.MAX_VALUE;

        Enumeration e = getSourceConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor) e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    public Vector getSourceConnectionAnchors() {
        return outputConnectionAnchors;
    }

    public ConnectionAnchor getTargetConnectionAnchorAt(Point p) {
        ConnectionAnchor closest = null;
        long min = Long.MAX_VALUE;

        Enumeration e = getTargetConnectionAnchors().elements();
        while (e.hasMoreElements()) {
            ConnectionAnchor c = (ConnectionAnchor) e.nextElement();
            Point p2 = c.getLocation(null);
            long d = p.getDistance2(p2);
            if (d < min) {
                min = d;
                closest = c;
            }
        }
        return closest;
    }

    public Vector getTargetConnectionAnchors() {
        return inputConnectionAnchors;
    }

    protected void paintFigure(Graphics graphics) {
        // TODO Auto-generated method stub
        super.paintFigure(graphics);
        graphics.setAntialias(SWT.ON);
    }
}
