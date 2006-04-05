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
public class FunctionNode extends SimpleNode {
    public static String       IN               = "IN";

    public static String       OUT              = "OUT";

    static final long          serialVersionUID = 1;

    private static final Image ICON             = createImage(SimpleNode.class,
                                                        "icons/xor16.gif");

    public FunctionNode() {
        this.size = new Dimension(50, 50);
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
        return "Function Node";
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#update()
     */
    public void update() {
        // TODO Auto-generated method stub
        boolean bit = getInput(IN);
        setOutput(OUT, bit);
    }
}
