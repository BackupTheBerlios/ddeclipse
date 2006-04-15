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
public class PredicateNode extends FastNode {
    public static String       IN_1             = "IN1";

    public static String       IN_2             = "IN2";

    public static String       OUT_1            = "OUT1";

    public static String       OUT_2            = "OUT2";

    static final long          serialVersionUID = 1;

    private static final Image ICON             = createImage(FastNode.class,
                                                        "icons/or16.gif");

    public PredicateNode() {
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
        return "Predicate Node";
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#update()
     */
    public void update() {
        // TODO Auto-generated method stub
        boolean bit1 = getInput(IN_1);
        boolean bit2 = getInput(IN_2);
        setOutput(OUT_1, bit1 | bit2);
        setOutput(OUT_2, bit1 | bit2);
    }
}
