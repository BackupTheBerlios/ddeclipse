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
public class EndNode extends FastNode {
    public static final String IN1              = "IN1";

    public static final String IN2              = "IN2";

    public static final String IN3              = "IN3";

    public static final String IN4              = "IN4";

    static final long          serialVersionUID = 1;

    private static final Image ICON             = createImage(FastNode.class,
                                                        "icons/or16.gif");

    public EndNode() {
        size = new Dimension(20, 20);
        location = new Point(20, 20);
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#update()
     */
    public void update() {
        // TODO Auto-generated method stub
        getInput(IN1);
        getInput(IN2);
        getInput(IN3);
        getInput(IN4);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // TODO Auto-generated method stub
        return "End Node";
    }

    /**
     * @see org.eclipse.fastide.model.FastNode#getIconImage()
     */
    public Image getIconImage() {
        // TODO Auto-generated method stub
        return ICON;
    }
}
