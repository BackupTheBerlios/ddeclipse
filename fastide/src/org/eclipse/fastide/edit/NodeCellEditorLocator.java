/**
 * 
 */
package org.eclipse.fastide.edit;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fastide.figures.FastNodeFigure;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

/**
 * @author …Ú»›÷€
 */
public class NodeCellEditorLocator implements CellEditorLocator {
    private FastNodeFigure fastNode;

    public NodeCellEditorLocator(FastNodeFigure FastNode) {
        setName(FastNode);
    }

    public void relocate(CellEditor celleditor) {
        Text text = (Text) celleditor.getControl();
        Rectangle rect = fastNode.getClientArea();
        fastNode.translateToAbsolute(rect);
        org.eclipse.swt.graphics.Rectangle trim = text.computeTrim(0, 0, 0, 0);
        rect.translate(trim.x, trim.y);
        rect.width += trim.width;
        rect.height += trim.height;
        text.setBounds(rect.x, rect.y, rect.width, rect.height);
    }

    /**
     * Returns the FastNode figure.
     */
    protected FastNodeFigure getLabel() {
        return fastNode;
    }

    /**
     * Sets the Sticky note figure.
     * 
     * @param FastNode
     *            The FastNode to set
     */
    protected void setName(FastNodeFigure fastNode) {
        this.fastNode = fastNode;
    }
}
