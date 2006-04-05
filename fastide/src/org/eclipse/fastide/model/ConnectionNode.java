/**
 *
 */
package org.eclipse.fastide.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.Bendpoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ÉòÈÝÖÛ
 */
public class ConnectionNode extends FastElement {
    static final long   serialVersionUID = 1;

    private FastSubpart source;

    private FastSubpart target;

    private String      targetTerminal;

    private String      sourceTerminal;

    private boolean     value;

    protected List      bendpoints       = new ArrayList();

    public void attachSource() {
        if(getSource() == null
                || getSource().getSourceConnections().contains(this))
            return;
        getSource().connectOutput(this);
    }

    public void attachTarget() {
        if(getTarget() == null
                || getTarget().getTargetConnections().contains(this))
            return;
        getTarget().connectInput(this);
    }

    public void detachSource() {
        if(getSource() == null)
            return;
        getSource().disconnectOutput(this);
    }

    public void detachTarget() {
        if(getTarget() == null)
            return;
        getTarget().disconnectInput(this);
    }

    public List getBendpoints() {
        return bendpoints;
    }

    public FastSubpart getSource() {
        return source;
    }

    public String getSourceTerminal() {
        return sourceTerminal;
    }

    public FastSubpart getTarget() {
        return target;
    }

    public String getTargetTerminal() {
        return targetTerminal;
    }

    public boolean getValue() {
        return value;
    }

    public void insertBendpoint(int index, Bendpoint point) {
        getBendpoints().add(index, point);
        firePropertyChange("bendpoint", null, null);//$NON-NLS-1$
    }

    public void removeBendpoint(int index) {
        getBendpoints().remove(index);
        firePropertyChange("bendpoint", null, null);//$NON-NLS-1$
    }

    public void setBendpoint(int index, Bendpoint point) {
        getBendpoints().set(index, point);
        firePropertyChange("bendpoint", null, null);//$NON-NLS-1$
    }

    public void setBendpoints(Vector points) {
        bendpoints = points;
        firePropertyChange("bendpoint", null, null);//$NON-NLS-1$
    }

    public void setSource(FastSubpart e) {
        Object old = source;
        source = e;
        firePropertyChange("source", old, source);//$NON-NLS-1$
    }

    public void setSourceTerminal(String s) {
        Object old = sourceTerminal;
        sourceTerminal = s;
        firePropertyChange("sourceTerminal", old, sourceTerminal);//$NON-NLS-1$
    }

    public void setTarget(FastSubpart e) {
        Object old = target;
        target = e;
        firePropertyChange("target", old, source);//$NON-NLS-1$
    }

    public void setTargetTerminal(String s) {
        Object old = targetTerminal;
        targetTerminal = s;
        firePropertyChange("targetTerminal", old, sourceTerminal);//$NON-NLS-1$
    }

    public void setValue(boolean value) {
        if(value == this.value)
            return;
        this.value = value;
        if(target != null)
            target.update();
        firePropertyChange("value", null, null);//$NON-NLS-1$
    }

    public String toString() {
        return "Wire(" + getSource() + "," + getSourceTerminal() + "->" + getTarget() + "," + getTargetTerminal() + ")";//$NON-NLS-5$//$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#getXml(org.w3c.dom.Document)
     */
    public Element getXml(Document doc) {
        // TODO Auto-generated method stub
        Element connection = super.getXml(doc);

        Element temp = doc.createElement("source");
        temp.appendChild(doc.createTextNode(source.getID()));
        connection.appendChild(temp);

        temp = doc.createElement("target");
        temp.appendChild(doc.createTextNode(target.getID()));
        connection.appendChild(temp);

        temp = doc.createElement("sourceTerminal");
        temp.appendChild(doc.createTextNode(sourceTerminal));
        connection.appendChild(temp);

        temp = doc.createElement("targetTerminal");
        temp.appendChild(doc.createTextNode(targetTerminal));
        connection.appendChild(temp);

        Iterator iter = bendpoints.iterator();
        while (iter.hasNext()) {
            ConnectionBendpoint bendpoint = (ConnectionBendpoint) iter.next();
            connection.appendChild(bendpoint.getXml(doc));
        }
        return connection;
    }
}
