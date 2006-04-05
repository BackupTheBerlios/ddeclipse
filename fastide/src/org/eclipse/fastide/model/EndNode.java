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
public class EndNode extends SimpleNode {
    public static final String IN               = "IN";

    static final long          serialVersionUID = 1;

    private static final Image ICON             = createImage(SimpleNode.class,
                                                        "icons/or16.gif");

    public EndNode() {
        size = new Dimension(10, 10);
        location = new Point(20, 20);
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#update()
     */
    public void update() {
        // TODO Auto-generated method stub
        getInput(IN);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // TODO Auto-generated method stub
        return "End Node";
    }

    /**
     * @see org.eclipse.fastide.model.SimpleNode#getIconImage()
     */
    public Image getIconImage() {
        // TODO Auto-generated method stub
        return ICON;
    }
}
