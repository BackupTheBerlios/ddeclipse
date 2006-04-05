/**
 *
 */
package org.eclipse.fastide.model;

/**
 * @author …Ú»›÷€
 */
public class FastDiagramFactory {
    static FastDiagram root;

    public static Object createEmptyModel() {
        root = new FastDiagram();
        return root;
    }

    public Object getRootElement() {
        if(root == null)
            createEmptyModel();
        return root;
    }
}
