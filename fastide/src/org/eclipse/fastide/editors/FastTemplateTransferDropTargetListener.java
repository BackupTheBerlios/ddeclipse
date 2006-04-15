/**
 *
 */
package org.eclipse.fastide.editors;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.fastide.model.FastElementFactory;

/**
 * @author …Ú»›÷€
 */
public class FastTemplateTransferDropTargetListener extends
        TemplateTransferDropTargetListener {

    public FastTemplateTransferDropTargetListener(EditPartViewer viewer) {
        super(viewer);
    }

    protected CreationFactory getFactory(Object template) {
        if (template instanceof String)
            return new FastElementFactory((String) template);
        return null;
    }
}
