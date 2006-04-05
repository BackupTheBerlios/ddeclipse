/**
 *
 */
package org.eclipse.fastide.model;

import java.util.Iterator;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.fastide.FastMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author …Ú»›÷€
 */
public abstract class SimpleNode extends FastSubpart {
    private static int                     count;

    static final long                      serialVersionUID = 1;

    private String                         name;

    protected static IPropertyDescriptor[] newDescriptors   = null;

    public static String                   NAME_PROP        = "name";

    static {
        PropertyDescriptor pValueProp = new TextPropertyDescriptor(NAME_PROP,
                FastMessages.PropertyDescriptor_SimpleNode_Name);
        if(descriptors != null) {
            newDescriptors = new IPropertyDescriptor[descriptors.length + 1];
            for (int i = 0; i < descriptors.length; i++)
                newDescriptors[i] = descriptors[i];
            newDescriptors[descriptors.length] = pValueProp;
        } else
            newDescriptors = new IPropertyDescriptor[] { pValueProp };
    }

    public SimpleNode() {
        name = "any";
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
        firePropertyChange(NAME_PROP, null, name);
    }

    /**
     * @see com.fastide.editors.gef.models.FastSubpart#getNewID()
     */
    protected String getNewID() {
        // TODO Auto-generated method stub
        return Integer.toString(count++);
    }

    /**
     * @see org.eclipse.fastide.model.FastSubpart#getIconImage()
     */
    public Image getIconImage() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.fastide.model.FastSubpart#setPropertyValue(java.lang.Object,
     *      java.lang.Object)
     */
    public void setPropertyValue(Object id, Object value) {
        // TODO Auto-generated method stub
        if(NAME_PROP.equals(id)) {
            name = (String) value;
        } else
            super.setPropertyValue(id, value);
    }

    /**
     * @see org.eclipse.fastide.model.FastSubpart#getSize()
     */
    public Object getPropertyValue(Object param) {
        // TODO Auto-generated method stub
        if(NAME_PROP.equals(param))
            return name;
        else
            return super.getPropertyValue(param);
    }

    public boolean hasInput() {
        return !inputs.isEmpty();
    }

    /**
     * @see org.eclipse.fastide.model.FastSubpart#getXml(org.w3c.dom.Document)
     */
    public Element getXml(Document doc) {
        // TODO Auto-generated method stub
        Element simple = super.getXml(doc);

        Element temp = doc.createElement("name");
        if(name == null)
            name = "";
        temp.appendChild(doc.createTextNode(name));
        simple.appendChild(temp);

        temp = doc.createElement("inputs");
        Iterator iter = inputs.values().iterator();
        while (iter.hasNext()) {
            ConnectionNode conn = (ConnectionNode) iter.next();
            temp.appendChild(conn.getXml(doc));
        }
        simple.appendChild(temp);

        temp = doc.createElement("outputs");
        iter = outputs.iterator();
        while (iter.hasNext()) {
            ConnectionNode conn = (ConnectionNode) iter.next();
            temp.appendChild(conn.getXml(doc));
        }
        simple.appendChild(temp);

        return simple;
    }
}
