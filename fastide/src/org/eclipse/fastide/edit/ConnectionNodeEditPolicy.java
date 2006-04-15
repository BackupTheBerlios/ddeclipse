/**
 *
 */
package org.eclipse.fastide.edit;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.fastide.model.FastConnection;
import org.eclipse.fastide.model.commands.ConnectionCommand;

/**
 * @author …Ú»›÷€
 */
public class ConnectionNodeEditPolicy extends ConnectionEditPolicy {

    protected Command getDeleteCommand(GroupRequest request) {
        ConnectionCommand c = new ConnectionCommand();
        c.setConnectionNode((FastConnection) getHost().getModel());
        return c;
    }
}
