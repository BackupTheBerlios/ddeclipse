/**
 *
 */
package org.eclipse.fastide.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.fastide.edit.NativeDropRequest;

/**
 * @author …Ú»›÷€
 */
public class TextTransferDropTargetListener extends
        AbstractTransferDropTargetListener {

    public TextTransferDropTargetListener(EditPartViewer viewer, Transfer xfer) {
        super(viewer, xfer);
    }

    protected Request createTargetRequest() {
        return new NativeDropRequest();
    }

    protected NativeDropRequest getNativeDropRequest() {
        return (NativeDropRequest) getTargetRequest();
    }

    protected void updateTargetRequest() {
        getNativeDropRequest().setData(getCurrentEvent().data);
    }
}
