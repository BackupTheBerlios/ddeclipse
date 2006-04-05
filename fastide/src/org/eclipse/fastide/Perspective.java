package org.eclipse.fastide;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();

        layout.addStandaloneView(FastideConstants.PROPERTY_SHEET_ID, true,
                IPageLayout.BOTTOM, 0.6f, editorArea);
        layout.addStandaloneView(FastideConstants.CONTENT_OUTLINE_ID, true,
                IPageLayout.RIGHT, 0.7f, editorArea);
    }
}
