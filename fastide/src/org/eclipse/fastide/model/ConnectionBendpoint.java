/**
 *
 */
package org.eclipse.fastide.model;

import java.io.Serializable;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author …Ú»›÷€
 */
public class ConnectionBendpoint implements Bendpoint, Serializable, XmlModel {
    private float     weight           = 0.5f;

    private Dimension d1, d2;

    static final long serialVersionUID = 1;

    public ConnectionBendpoint() {
    }

    public Dimension getFirstRelativeDimension() {
        return d1;
    }

    public Point getLocation() {
        return null;
    }

    public Dimension getSecondRelativeDimension() {
        return d2;
    }

    public float getWeight() {
        return weight;
    }

    public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
        d1 = dim1;
        d2 = dim2;
    }

    public void setWeight(float w) {
        weight = w;
    }

    /**
     * @see org.fastide.model.XmlModel#getXml(org.w3c.dom.Document)
     */
    public Element getXml(Document doc) {
        // TODO Auto-generated method stub
        Element bendpoint = doc.createElement("bendpoint");

        Element temp = doc.createElement("weight");
        temp.appendChild(doc.createTextNode(Float.toString(weight)));
        bendpoint.appendChild(temp);

        temp = doc.createElement("d1");
        temp.setAttribute("width", Integer.toString(d1.width));
        temp.setAttribute("height", Integer.toString(d1.height));
        bendpoint.appendChild(temp);

        temp = doc.createElement("d2");
        temp.setAttribute("width", Integer.toString(d2.width));
        temp.setAttribute("height", Integer.toString(d2.height));
        bendpoint.appendChild(temp);
        return bendpoint;
    }
}
