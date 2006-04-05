package org.eclipse.fastide;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction newAction;

    private IWorkbenchAction exitAction;

    private IWorkbenchAction saveAction;

    private IWorkbenchAction saveAsAction;

    private IWorkbenchAction undoAction;

    private IWorkbenchAction redoAction;

    private IWorkbenchAction aboutAction;

    private IWorkbenchAction closeAction;

    private IWorkbenchAction closeAllAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
        newAction = ActionFactory.NEW.create(window);
        register(newAction);
        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
        saveAction = ActionFactory.SAVE.create(window);
        register(saveAction);
        saveAsAction = ActionFactory.SAVE_AS.create(window);
        register(saveAsAction);
        undoAction = ActionFactory.UNDO.create(window);
        register(undoAction);
        redoAction = ActionFactory.REDO.create(window);
        register(redoAction);
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        closeAction = ActionFactory.CLOSE.create(window);
        register(closeAction);
        closeAllAction = ActionFactory.CLOSE_ALL.create(window);
        register(closeAllAction);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("&File",
                IWorkbenchActionConstants.M_FILE);
        MenuManager editMenu = new MenuManager("&Edit",
                IWorkbenchActionConstants.M_EDIT);
        MenuManager navMenu = new MenuManager("&Navigate",
                IWorkbenchActionConstants.M_NAVIGATE);
        MenuManager helpMenu = new MenuManager("&Help",
                IWorkbenchActionConstants.M_HELP);

        fileMenu.add(newAction);
        fileMenu.add(new Separator());
        fileMenu.add(closeAction);
        fileMenu.add(closeAllAction);
        fileMenu.add(new Separator());
        fileMenu.add(saveAction);
        fileMenu.add(saveAsAction);
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        menuBar.add(fileMenu);

        editMenu.add(undoAction);
        editMenu.add(redoAction);
        menuBar.add(editMenu);

        menuBar.add(navMenu);

        helpMenu.add(aboutAction);
        menuBar.add(helpMenu);
    }
}
