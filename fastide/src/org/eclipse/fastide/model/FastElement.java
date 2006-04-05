/**
 *
 */
package org.eclipse.fastide.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author …Ú»›÷€
 */
public class FastElement implements IPropertySource, Cloneable, Serializable,
        XmlModel {

    public static final String                INPUTS           = "inputs";

    public static final String                OUTPUTS          = "outputs";

    public static final String                CHILDREN         = "Children";

    transient protected PropertyChangeSupport listeners        = new PropertyChangeSupport(
                                                                       this);

    static final long                         serialVersionUID = 1;

    protected void firePropertyChange(String prop, Object old, Object newValue) {
        listeners.firePropertyChange(prop, old, newValue);
    }

    protected void fireChildAdded(String prop, Object child, Object index) {
        listeners.firePropertyChange(prop, index, child);
    }

    protected void fireChildRemoved(String prop, Object child) {
        listeners.firePropertyChange(prop, child, null);
    }

    protected void fireStructureChange(String prop, Object child) {
        listeners.firePropertyChange(prop, null, child);
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
     */
    public Object getEditableValue() {
        // TODO Auto-generated method stub
        return this;
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
     */
    public IPropertyDescriptor[] getPropertyDescriptors() {
        // TODO Auto-generated method stub
        return new IPropertyDescriptor[0];
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
     */
    public Object getPropertyValue(Object id) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
     */
    public boolean isPropertySet(Object id) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
     */
    public void resetPropertyValue(Object id) {
        // TODO Auto-generated method stub

    }

    /**
     * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
     *      java.lang.Object)
     */
    public void setPropertyValue(Object id, Object value) {
        // TODO Auto-generated method stub

    }

    /**
     * DOCUMENT ME!
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        listeners = new PropertyChangeSupport(this);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        listeners.removePropertyChangeListener(l);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        listeners.addPropertyChangeListener(l);
    }

    public void update() {

    }

    public Element getXml(Document doc) {
        Element node = doc.createElement("node");

        node.setAttribute("type", getClass().getSimpleName());

        return node;
    }
}
