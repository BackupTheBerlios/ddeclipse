/**
 *
 */
package org.eclipse.fastide.model;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;

/**
 * @author …Ú»›÷€
 */
public class StartNode extends SimpleNode {
    public static final String OUT1 = "OUT1";

    public static final String OUT2 = "OUT2";

    public static final String OUT3 = "OUT3";

    public static final String OUT4 = "OUT4";

    static final long serialVersionUID = 1;

    private static final Image ICON = createImage(SimpleNode.class,
            "icons/or16.gif");

    public StartNode() {
        size = new Dimension(20, 20);
        location = new Point(20, 20);
    }

    /**
     * @see org.eclipse.fastide.model.FastSubpart#getIconImage()
     */
    public Image getIconImage() {
        // TODO Auto-generated method stub
        return ICON;
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#update()
     */
    public void update() {
        // TODO Auto-generated method stub
        setOutput(OUT1, true);
        setOutput(OUT2, true);
        setOutput(OUT3, true);
        setOutput(OUT4, true);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // TODO Auto-generated method stub
        return "Start Node";
    }
}
