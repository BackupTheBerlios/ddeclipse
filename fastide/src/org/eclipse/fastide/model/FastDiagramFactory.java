/**
 *
 */
package org.eclipse.fastide.model;

/**
 * @author ������
 */
public class FastDiagramFactory {
    static FastDiagram root;

    public static FastDiagram createEmptyModel() {
        root = new FastDiagram();
        return root;
    }

    public static FastDiagram getRootElement() {
        if (root == null)
            createEmptyModel();
        return root;
    }
}
