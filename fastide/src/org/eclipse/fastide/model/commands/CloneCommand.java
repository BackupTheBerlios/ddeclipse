/**
 *
 */
package org.eclipse.fastide.model.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.fastide.model.ConnectionBendpoint;
import org.eclipse.fastide.model.ConnectionNode;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.FastGuide;
import org.eclipse.fastide.model.FastSubpart;
import org.eclipse.fastide.model.FunctionNode;
import org.eclipse.fastide.model.JoinpointNode;
import org.eclipse.fastide.model.PredicateNode;
import org.eclipse.fastide.model.SimpleNode;

/**
 * @author …Ú»›÷€
 */
public class CloneCommand extends Command {
    private List        parts, newTopLevelParts, newConnections;

    private FastDiagram parent;

    private Map         bounds, indices, connectionPartMap;

    private ChangeGuideCommand vGuideCommand, hGuideCommand;

    private FastGuide          hGuide, vGuide;

    private int                hAlignment, vAlignment;

    public CloneCommand() {
        super("clone");
        parts = new LinkedList();
    }

    public void addPart(FastSubpart part, Rectangle newBounds) {
        parts.add(part);
        if(bounds == null) {
            bounds = new HashMap();
        }
        bounds.put(part, newBounds);
    }

    public void addPart(FastSubpart part, int index) {
        parts.add(part);
        if(indices == null) {
            indices = new HashMap();
        }
        indices.put(part, new Integer(index));
    }

    protected void clonePart(FastSubpart oldPart, FastDiagram newParent,
            Rectangle newBounds, List newConnections, Map connectionPartMap,
            int index) {
        FastSubpart newPart = null;

        if(oldPart instanceof FunctionNode) {
            newPart = new FunctionNode();
            newPart.setPropertyValue(SimpleNode.NAME_PROP, oldPart
                    .getPropertyValue(SimpleNode.NAME_PROP));
        } else if(oldPart instanceof JoinpointNode) {
            newPart = new JoinpointNode();
            newPart.setPropertyValue(SimpleNode.NAME_PROP, oldPart
                    .getPropertyValue(SimpleNode.NAME_PROP));
        } else if(oldPart instanceof PredicateNode) {
            newPart = new PredicateNode();
            newPart.setPropertyValue(SimpleNode.NAME_PROP, oldPart
                    .getPropertyValue(SimpleNode.NAME_PROP));
        }

        if(oldPart instanceof FastDiagram) {
            Iterator i = ((FastDiagram) oldPart).getChildren().iterator();
            while (i.hasNext()) {
                // for children they will not need new bounds
                clonePart((FastSubpart) i.next(), (FastDiagram) newPart, null,
                        newConnections, connectionPartMap, -1);
            }
        }

        Iterator i = oldPart.getTargetConnections().iterator();
        while (i.hasNext()) {
            ConnectionNode connection = (ConnectionNode) i.next();
            ConnectionNode newConnection = new ConnectionNode();
            newConnection.setValue(connection.getValue());
            newConnection.setTarget(newPart);
            newConnection.setTargetTerminal(connection.getTargetTerminal());
            newConnection.setSourceTerminal(connection.getSourceTerminal());
            newConnection.setSource(connection.getSource());

            Iterator b = connection.getBendpoints().iterator();
            Vector newBendPoints = new Vector();

            while (b.hasNext()) {
                ConnectionBendpoint bendPoint = (ConnectionBendpoint) b.next();
                ConnectionBendpoint newBendPoint = new ConnectionBendpoint();
                newBendPoint.setRelativeDimensions(bendPoint
                        .getFirstRelativeDimension(), bendPoint
                        .getSecondRelativeDimension());
                newBendPoint.setWeight(bendPoint.getWeight());
                newBendPoints.add(newBendPoint);
            }

            newConnection.setBendpoints(newBendPoints);
            newConnections.add(newConnection);
        }

        if(index < 0) {
            newParent.addChild(newPart);
        } else {
            newParent.addChild(newPart, index);
        }

        newPart.setSize(oldPart.getSize());

        if(newBounds != null) {
            newPart.setLocation(newBounds.getTopLeft());
        } else {
            newPart.setLocation(oldPart.getLocation());
        }

        // keep track of the new parts so we can delete them in undo
        // keep track of the oldpart -> newpart map so that we can properly
        // attach
        // all connections.
        if(newParent == parent)
            newTopLevelParts.add(newPart);
        connectionPartMap.put(oldPart, newPart);
    }

    public void execute() {
        connectionPartMap = new HashMap();
        newConnections = new LinkedList();
        newTopLevelParts = new LinkedList();

        Iterator i = parts.iterator();

        FastSubpart part = null;
        while (i.hasNext()) {
            part = (FastSubpart) i.next();
            if(bounds != null && bounds.containsKey(part)) {
                clonePart(part, parent, (Rectangle) bounds.get(part),
                        newConnections, connectionPartMap, -1);
            } else if(indices != null && indices.containsKey(part)) {
                clonePart(part, parent, null, newConnections,
                        connectionPartMap, ((Integer) indices.get(part))
                                .intValue());
            } else {
                clonePart(part, parent, null, newConnections,
                        connectionPartMap, -1);
            }
        }

        // go through and set the source of each connection to the proper
        // source.
        Iterator c = newConnections.iterator();

        while (c.hasNext()) {
            ConnectionNode conn = (ConnectionNode) c.next();
            FastSubpart source = conn.getSource();
            if(connectionPartMap.containsKey(source)) {
                conn.setSource((FastSubpart) connectionPartMap.get(source));
                conn.attachSource();
                conn.attachTarget();
            }
        }

        if(hGuide != null) {
            hGuideCommand = new ChangeGuideCommand(
                    (FastSubpart) connectionPartMap.get(parts.get(0)), true);
            hGuideCommand.setNewGuide(hGuide, hAlignment);
            hGuideCommand.execute();
        }

        if(vGuide != null) {
            vGuideCommand = new ChangeGuideCommand(
                    (FastSubpart) connectionPartMap.get(parts.get(0)), false);
            vGuideCommand.setNewGuide(vGuide, vAlignment);
            vGuideCommand.execute();
        }
    }

    public void setParent(FastDiagram parent) {
        this.parent = parent;
    }

    public void redo() {
        for (Iterator iter = newTopLevelParts.iterator(); iter.hasNext();)
            parent.addChild((FastSubpart) iter.next());
        for (Iterator iter = newConnections.iterator(); iter.hasNext();) {
            ConnectionNode conn = (ConnectionNode) iter.next();
            FastSubpart source = conn.getSource();
            if(connectionPartMap.containsKey(source)) {
                conn.setSource((FastSubpart) connectionPartMap.get(source));
                conn.attachSource();
                conn.attachTarget();
            }
        }
        if(hGuideCommand != null)
            hGuideCommand.redo();
        if(vGuideCommand != null)
            vGuideCommand.redo();
    }

    public void setGuide(FastGuide guide, int alignment, boolean isHorizontal) {
        if(isHorizontal) {
            hGuide = guide;
            hAlignment = alignment;
        } else {
            vGuide = guide;
            vAlignment = alignment;
        }
    }

    public void undo() {
        if(hGuideCommand != null)
            hGuideCommand.undo();
        if(vGuideCommand != null)
            vGuideCommand.undo();
        for (Iterator iter = newTopLevelParts.iterator(); iter.hasNext();)
            parent.removeChild((FastSubpart) iter.next());
    }
}
