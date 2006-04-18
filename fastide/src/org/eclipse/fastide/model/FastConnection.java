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
public class FastConnection extends FastElement {
    static final long   serialVersionUID = 1;

    private FastSubpart source;

    private FastSubpart target;

    private String      targetTerminal;

    private String      sourceTerminal;

    private boolean     value;

    protected List      bendpoints       = new ArrayList();

    public void attachSource() {
        if (getSource() == null
                || getSource().getSourceConnections().contains(this))
            return;
        getSource().connectOutput(this);
    }

    public void attachTarget() {
        if (getTarget() == null
                || getTarget().getTargetConnections().contains(this))
            return;
        getTarget().connectInput(this);
    }

    public void detachSource() {
        if (getSource() == null)
            return;
        getSource().disconnectOutput(this);
    }

    public void detachTarget() {
        if (getTarget() == null)
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
        firePropertyChange("bendpoint", null, null);
    }

    public void removeBendpoint(int index) {
        getBendpoints().remove(index);
        firePropertyChange("bendpoint", null, null);
    }

    public void setBendpoint(int index, Bendpoint point) {
        getBendpoints().set(index, point);
        firePropertyChange("bendpoint", null, null);
    }

    public void setBendpoints(Vector points) {
        bendpoints = points;
        firePropertyChange("bendpoint", null, null);
    }

    public void setSource(FastSubpart e) {
        Object old = source;
        source = e;
        firePropertyChange("source", old, source);
    }

    public void setSourceTerminal(String s) {
        Object old = sourceTerminal;
        sourceTerminal = s;
        firePropertyChange("sourceTerminal", old, sourceTerminal);
    }

    public void setTarget(FastSubpart e) {
        Object old = target;
        target = e;
        firePropertyChange("target", old, source);
    }

    public void setTargetTerminal(String s) {
        Object old = targetTerminal;
        targetTerminal = s;
        firePropertyChange("targetTerminal", old, sourceTerminal);
    }

    public void setValue(boolean value) {
        if (value == this.value)
            return;
        this.value = value;
        if (target != null)
            target.update();
        firePropertyChange("value", null, null);
    }

    public String toString() {
        return "Wire(" + getSource() + "," + getSourceTerminal() + "->"
                + getTarget() + "," + getTargetTerminal() + ")";
    }

    /**
     * @see org.eclipse.fastide.model.FastElement#getXml(org.w3c.dom.Document)
     */
    public Element getXml(Document doc) {
        Element connection = super.getXml(doc);

        FastNode targetNode = (FastNode) target;
        FastNode sourceNode = (FastNode) source;

        Element temp = doc.createElement("source");
        temp.setAttribute("type", sourceNode.getClass().getSimpleName());
        temp.setAttribute("id", source.getID());
        temp.appendChild(doc.createTextNode(sourceNode.getName()));
        connection.appendChild(temp);

        temp = doc.createElement("target");
        temp.setAttribute("id", target.getID());
        temp.setAttribute("type", targetNode.getClass().getSimpleName());
        temp.appendChild(doc.createTextNode(targetNode.getName()));
        connection.appendChild(temp);
        return connection;
    }
}
