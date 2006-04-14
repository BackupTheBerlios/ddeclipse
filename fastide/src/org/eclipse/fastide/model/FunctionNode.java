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
    public static String IN_1 = "IN1";

    public static String IN_2 = "IN2";

    public static String IN_3 = "IN3";

    public static String IN_4 = "IN4";

    public static String OUT_1 = "OUT1";

    public static String OUT_2 = "OUT2";

    public static String OUT_3 = "OUT3";

    public static String OUT_4 = "OUT4";

    static final long serialVersionUID = 1;

    private static final Image ICON = createImage(SimpleNode.class,
            "icons/xor16.gif");

    public FunctionNode() {
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
        return "Function Node";
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#update()
     */
    public void update() {
        // TODO Auto-generated method stub
        boolean bit1 = getInput(IN_1);
        boolean bit2 = getInput(IN_2);
        boolean bit3 = getInput(IN_3);
        boolean bit4 = getInput(IN_4);
        boolean bit = bit1 | bit2 | bit3 | bit4;
        setOutput(OUT_1, bit);
        setOutput(OUT_2, bit);
        setOutput(OUT_3, bit);
        setOutput(OUT_4, bit);
    }
}
