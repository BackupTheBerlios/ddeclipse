/**
 *
 */
package org.eclipse.fastide.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.fastide.FastMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author ÉòÈÝÖÛ
 */
public class FastDiagram extends FastSubpart {
    static final long     serialVersionUID     = 1;

    public static String  ID_ROUTER            = "router";       //$NON-NLS-1$

    public static Integer ROUTER_MANUAL        = new Integer(0);

    public static Integer ROUTER_MANHATTAN     = new Integer(1);

    public static Integer ROUTER_SHORTEST_PATH = new Integer(2);

    private static int    count;

    protected List        children             = new ArrayList();

    protected FastRuler   leftRuler, topRuler;

    protected Integer     connectionRouter     = null;

    private boolean       rulersVisibility     = false;

    private boolean       snapToGeometry       = false;

    private boolean       gridEnabled          = false;

    private double        zoom                 = 1.0;

    public FastDiagram() {
        size.width = 100;
        size.height = 100;
        location.x = 20;
        location.y = 20;
        createRulers();
    }

    public void addChild(FastElement child) {
        addChild(child, -1);
    }

    public void addChild(FastElement child, int index) {
        if (index >= 0)
            children.add(index, child);
        else
            children.add(child);
        fireChildAdded(CHILDREN, child, new Integer(index));
    }

    protected void createRulers() {
        leftRuler = new FastRuler(false);
        topRuler = new FastRuler(true);
    }

    public List getChildren() {
        return children;
    }

    public Integer getConnectionRouter() {
        if (connectionRouter == null)
            connectionRouter = ROUTER_MANUAL;
        return connectionRouter;
    }

    public Image getIconImage() {
        return null;
    }

    public String getNewID() {
        return Integer.toString(count++);
    }

    public double getZoom() {
        return zoom;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        if (getClass().equals(FastDiagram.class)) {
            ComboBoxPropertyDescriptor cbd = new ComboBoxPropertyDescriptor(
                    ID_ROUTER,
                    FastMessages.PropertyDescriptor_FastDiagram_ConnectionRouter,
                    new String[] {
                            FastMessages.PropertyDescriptor_FastDiagram_Manual,
                            FastMessages.PropertyDescriptor_FastDiagram_Manhattan,
                            FastMessages.PropertyDescriptor_FastDiagram_ShortestPath });
            cbd.setLabelProvider(new ConnectionRouterLabelProvider());
            return new IPropertyDescriptor[] { cbd };
        }
        return super.getPropertyDescriptors();
    }

    public Object getPropertyValue(Object propName) {
        if (propName.equals(ID_ROUTER))
            return connectionRouter;
        return super.getPropertyValue(propName);
    }

    public FastRuler getRuler(int orientation) {
        FastRuler result = null;
        switch (orientation) {
            case PositionConstants.NORTH:
                result = topRuler;
                break;
            case PositionConstants.WEST:
                result = leftRuler;
                break;
        }
        return result;
    }

    public boolean getRulerVisibility() {
        return rulersVisibility;
    }

    public boolean isGridEnabled() {
        return gridEnabled;
    }

    public boolean isSnapToGeometryEnabled() {
        return snapToGeometry;
    }

    private void readObject(java.io.ObjectInputStream s) throws IOException,
            ClassNotFoundException {
        s.defaultReadObject();
    }

    public void removeChild(FastElement child) {
        children.remove(child);
        fireChildRemoved(CHILDREN, child);
    }

    public void setConnectionRouter(Integer router) {
        Integer oldConnectionRouter = connectionRouter;
        connectionRouter = router;
        firePropertyChange(ID_ROUTER, oldConnectionRouter, connectionRouter);
    }

    public void setPropertyValue(Object id, Object value) {
        if (ID_ROUTER.equals(id))
            setConnectionRouter((Integer) value);
        else
            super.setPropertyValue(id, value);
    }

    public void setRulerVisibility(boolean newValue) {
        rulersVisibility = newValue;
    }

    public void setGridEnabled(boolean isEnabled) {
        gridEnabled = isEnabled;
    }

    public void setSnapToGeometry(boolean isEnabled) {
        snapToGeometry = isEnabled;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public String toString() {
        return FastMessages.FastDiagram_LabelText;
    }

    private class ConnectionRouterLabelProvider extends
            org.eclipse.jface.viewers.LabelProvider {

        public ConnectionRouterLabelProvider() {
            super();
        }

        public String getText(Object element) {
            if (element instanceof Integer) {
                Integer integer = (Integer) element;
                if (ROUTER_MANUAL.intValue() == integer.intValue())
                    return FastMessages.PropertyDescriptor_FastDiagram_Manual;
                if (ROUTER_MANHATTAN.intValue() == integer.intValue())
                    return FastMessages.PropertyDescriptor_FastDiagram_Manhattan;
                if (ROUTER_SHORTEST_PATH.intValue() == integer.intValue())
                    return FastMessages.PropertyDescriptor_FastDiagram_ShortestPath;
            }
            return super.getText(element);
        }
    }

    /**
     * @see org.eclipse.fastide.model.FastSubpart#getXml(org.w3c.dom.Document)
     */
    public Element getXml(Document doc) {
        // TODO Auto-generated method stub
        Element diagram = super.getXml(doc);

        Element temp = doc.createElement("connectionRouter");
        temp.appendChild(doc.createTextNode(connectionRouter.toString()));
        diagram.appendChild(temp);

        temp = doc.createElement("rulersVisibility");
        temp
                .appendChild(doc.createTextNode(Boolean
                        .toString(rulersVisibility)));
        diagram.appendChild(temp);

        temp = doc.createElement("snapToGeometry");
        temp.appendChild(doc.createTextNode(Boolean.toString(snapToGeometry)));
        diagram.appendChild(temp);

        temp = doc.createElement("gridEnabled");
        temp.appendChild(doc.createTextNode(Boolean.toString(gridEnabled)));
        diagram.appendChild(temp);

        temp = doc.createElement("subnodes");
        Iterator iter = children.iterator();
        while (iter.hasNext()) {
            FastElement elem = (FastElement) iter.next();
            System.out.println(elem.toString());
            temp.appendChild(elem.getXml(doc));
        }
        diagram.appendChild(temp);

        return diagram;
    }
}
