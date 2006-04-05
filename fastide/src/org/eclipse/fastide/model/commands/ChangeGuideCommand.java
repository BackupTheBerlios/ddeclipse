/**
 *
 */
package org.eclipse.fastide.model.commands;

import org.eclipse.gef.commands.Command;
import org.eclipse.fastide.model.FastGuide;
import org.eclipse.fastide.model.FastSubpart;

/**
 * @author …Ú»›÷€
 */
public class ChangeGuideCommand extends Command {
    private FastSubpart part;

    private FastGuide   oldGuide, newGuide;

    private int         oldAlign, newAlign;

    private boolean     horizontal;

    public ChangeGuideCommand(FastSubpart part, boolean horizontalGuide) {
        super();
        this.part = part;
        horizontal = horizontalGuide;
    }

    protected void changeGuide(FastGuide oldGuide, FastGuide newGuide,
            int newAlignment) {
        if(oldGuide != null && oldGuide != newGuide) {
            oldGuide.detachPart(part);
        }
        // You need to re-attach the part even if the oldGuide and the newGuide
        // are the same
        // because the alignment could have changed
        if(newGuide != null) {
            newGuide.attachPart(part, newAlignment);
        }
    }

    public void execute() {
        // Cache the old values
        oldGuide = horizontal ? part.getHorizontalGuide() : part
                .getVerticalGuide();
        if(oldGuide != null)
            oldAlign = oldGuide.getAlignment(part);

        redo();
    }

    public void redo() {
        changeGuide(oldGuide, newGuide, newAlign);
    }

    public void setNewGuide(FastGuide guide, int alignment) {
        newGuide = guide;
        newAlign = alignment;
    }

    public void undo() {
        changeGuide(newGuide, oldGuide, oldAlign);
    }
}
