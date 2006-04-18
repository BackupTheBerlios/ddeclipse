package org.eclipse.fastide.edit;

import org.eclipse.fastide.figures.FastNodeFigure;
import org.eclipse.fastide.model.FastNode;
import org.eclipse.fastide.model.commands.SetFastNodeNameCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class FastNodeDirectEditPolicy extends DirectEditPolicy {

    protected Command getDirectEditCommand(DirectEditRequest request) {
        // TODO Auto-generated method stub
        String name = (String) request.getCellEditor().getValue();
        FastNodeEditPart part = (FastNodeEditPart) getHost();
        SetFastNodeNameCommand command = new SetFastNodeNameCommand(
                (FastNode) part.getModel(), name);
        return command;
    }

    protected void showCurrentEditValue(DirectEditRequest request) {
        // TODO Auto-generated method stub
        String name = (String) request.getCellEditor().getValue();
        ((FastNodeFigure) getHostFigure()).setName(name);
        getHostFigure().getUpdateManager().performUpdate();
    }
}
