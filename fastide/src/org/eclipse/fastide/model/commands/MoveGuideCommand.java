/**
 *
 */
package org.eclipse.fastide.model.commands;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.fastide.model.FastGuide;
import org.eclipse.fastide.model.FastSubpart;

/**
 * @author …Ú»›÷€
 */
public class MoveGuideCommand extends Command {
    private int       pDelta;

    private FastGuide guide;

    public MoveGuideCommand(FastGuide guide, int positionDelta) {
        super("move guide");
        this.guide = guide;
        pDelta = positionDelta;
    }

    public void execute() {
        guide.setPosition(guide.getPosition() + pDelta);
        Iterator iter = guide.getParts().iterator();
        while (iter.hasNext()) {
            FastSubpart part = (FastSubpart) iter.next();
            Point location = part.getLocation().getCopy();
            if(guide.isHorizontal()) {
                location.y += pDelta;
            } else {
                location.x += pDelta;
            }
            part.setLocation(location);
        }
    }

    public void undo() {
        guide.setPosition(guide.getPosition() - pDelta);
        Iterator iter = guide.getParts().iterator();
        while (iter.hasNext()) {
            FastSubpart part = (FastSubpart) iter.next();
            Point location = part.getLocation().getCopy();
            if(guide.isHorizontal()) {
                location.y -= pDelta;
            } else {
                location.x -= pDelta;
            }
            part.setLocation(location);
        }
    }
}
