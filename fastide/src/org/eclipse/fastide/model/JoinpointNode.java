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
public class JoinpointNode extends SimpleNode {
    public static String       OUT              = "OUT";

    public static String       IN_1             = "IN1";

    public static String       IN_2             = "IN2";

    static final long          serialVersionUID = 1;

    private static final Image ICON             = createImage(SimpleNode.class,
                                                        "icons/and16.gif");

    public JoinpointNode() {
        this.size = new Dimension(40, 40);
        this.location = new Point(20, 20);
    }

    /**
     * @see com.fastide.editors.gef.models.FastSubpart#getIconImage()
     */
    public Image getIconImage() {
        // TODO Auto-generated method stub
        return ICON;
    }

    public String toString() {
        return "Joinpoint Node";
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#update()
     */
    public void update() {
        // TODO Auto-generated method stub
        boolean bit[] = new boolean[2];
        bit[0] = getInput(IN_1);
        bit[1] = getInput(IN_2);
        setOutput(OUT, bit[0] & bit[1]);
    }
}
