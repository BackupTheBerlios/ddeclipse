/**
 * 
 */
package org.eclipse.fastide.model.commands;

import org.eclipse.fastide.model.FastNode;
import org.eclipse.gef.commands.Command;

/**
 * @author ������
 */
public class SetFastNodeNameCommand extends Command {
    private String   oldName;

    private String   newName;

    private FastNode node;

    public SetFastNodeNameCommand(FastNode node, String newName) {
        this.node = node;
        if (newName != null)
            this.newName = newName;
        else
            this.newName = "a";
    }

    public void execute() {
        // TODO Auto-generated method stub
        oldName = node.getName();
        node.setName(newName);
    }

    public void undo() {
        // TODO Auto-generated method stub
        node.setName(oldName);
    }
}
