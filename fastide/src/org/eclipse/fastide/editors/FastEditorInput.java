package org.eclipse.fastide.editors;

import org.eclipse.core.runtime.IPath;
import org.eclipse.fastide.model.FastDiagram;
import org.eclipse.fastide.model.FastDiagramFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;

public class FastEditorInput implements IPathEditorInput {
    private IPath path;

    private FastDiagram diagram;

    public FastEditorInput(IPath path) {
        this.path = path;
        diagram = FastDiagramFactory.getRootElement();
    }

    public boolean exists() {
        // TODO Auto-generated method stub
        return path.toFile().exists();
    }

    public ImageDescriptor getImageDescriptor() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return path.toString();
    }

    public IPersistableElement getPersistable() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getToolTipText() {
        // TODO Auto-generated method stub
        return "Fast Editor";
    }

    public Object getAdapter(Class adapter) {
        // TODO Auto-generated method stub
        return null;
    }

    public IPath getPath() {
        // TODO Auto-generated method stub
        return path;
    }

    public FastDiagram getDiagram() {
        return diagram;
    }

    public void setDiagram(FastDiagram diagram) {
        this.diagram = diagram;
    }
}
