/**
 *
 */
package org.eclipse.fastide.model.commands;

import org.eclipse.fastide.model.ConnectionBendpoint;

/**
 * @author …Ú»›÷€
 */
public class CreateBendpointCommand extends BendpointCommand {
    public void execute() {
        ConnectionBendpoint wbp = new ConnectionBendpoint();
        wbp.setRelativeDimensions(getFirstRelativeDimension(),
                getSecondRelativeDimension());
        getConnectionNode().insertBendpoint(getIndex(), wbp);
        super.execute();
    }

    public void undo() {
        super.undo();
        getConnectionNode().removeBendpoint(getIndex());
    }
}
