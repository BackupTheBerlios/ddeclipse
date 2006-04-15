/**
 *
 */
package org.eclipse.fastide.model.commands;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.gef.commands.Command;
import org.eclipse.fastide.model.FastConnection;
import org.eclipse.fastide.model.FastSubpart;

/**
 * @author …Ú»›÷€
 */
public class ConnectionCommand extends Command {
    protected FastSubpart    oldSource;

    protected String         oldSourceTerminal;

    protected FastSubpart    oldTarget;

    protected String         oldTargetTerminal;

    protected FastSubpart    source;

    protected String         sourceTerminal;

    protected FastSubpart    target;

    protected String         targetTerminal;

    protected FastConnection conn;

    public ConnectionCommand() {
        super("create connection");
    }

    public boolean canExecute() {
        if (target != null) {
            Vector conns = target.getConnections();
            Iterator i = conns.iterator();
            while (i.hasNext()) {
                FastConnection conn = (FastConnection) i.next();
                if (targetTerminal != null && conn.getTargetTerminal() != null)
                    if (conn.getTargetTerminal().equals(targetTerminal)
                            && conn.getTarget().equals(target))
                        return false;
            }
        }
        return true;
    }

    public void execute() {
        if (source != null) {
            conn.detachSource();
            conn.setSource(source);
            conn.setSourceTerminal(sourceTerminal);
            conn.attachSource();
        }
        if (target != null) {
            conn.detachTarget();
            conn.setTarget(target);
            conn.setTargetTerminal(targetTerminal);
            conn.attachTarget();
        }
        if (source == null && target == null) {
            conn.detachSource();
            conn.detachTarget();
            conn.setTarget(null);
            conn.setSource(null);
        }
    }

    public String getLabel() {
        return "create connection";
    }

    public FastSubpart getSource() {
        return source;
    }

    public java.lang.String getSourceTerminal() {
        return sourceTerminal;
    }

    public FastSubpart getTarget() {
        return target;
    }

    public String getTargetTerminal() {
        return targetTerminal;
    }

    public FastConnection getConnectionNode() {
        return conn;
    }

    public void redo() {
        execute();
    }

    public void setSource(FastSubpart newSource) {
        source = newSource;
    }

    public void setSourceTerminal(String newSourceTerminal) {
        sourceTerminal = newSourceTerminal;
    }

    public void setTarget(FastSubpart newTarget) {
        target = newTarget;
    }

    public void setTargetTerminal(String newTargetTerminal) {
        targetTerminal = newTargetTerminal;
    }

    public void setConnectionNode(FastConnection c) {
        conn = c;
        oldSource = c.getSource();
        oldTarget = c.getTarget();
        oldSourceTerminal = c.getSourceTerminal();
        oldTargetTerminal = c.getTargetTerminal();
    }

    public void undo() {
        source = conn.getSource();
        target = conn.getTarget();
        sourceTerminal = conn.getSourceTerminal();
        targetTerminal = conn.getTargetTerminal();

        conn.detachSource();
        conn.detachTarget();

        conn.setSource(oldSource);
        conn.setTarget(oldTarget);
        conn.setSourceTerminal(oldSourceTerminal);
        conn.setTargetTerminal(oldTargetTerminal);

        conn.attachSource();
        conn.attachTarget();
    }
}
