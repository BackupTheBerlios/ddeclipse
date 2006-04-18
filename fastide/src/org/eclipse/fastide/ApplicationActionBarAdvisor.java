package org.eclipse.fastide;

import org.eclipse.fastide.actions.GenerateFastAction;
import org.eclipse.fastide.actions.NewFastDiagramAction;
import org.eclipse.fastide.actions.OpenFastDiagramAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IAction          newAction;

    private IAction          openAction;

    private IAction          runAction;

    private IWorkbenchAction exitAction;

    private IWorkbenchAction saveAction;

    private IWorkbenchAction saveAsAction;

    private IWorkbenchAction undoAction;

    private IWorkbenchAction redoAction;

    private IWorkbenchAction copyAction;

    private IWorkbenchAction pasteAction;

    private IWorkbenchAction aboutAction;

    private IWorkbenchAction closeAction;

    private IWorkbenchAction closeAllAction;

    private IWorkbenchAction helpAction;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
        newAction = new NewFastDiagramAction(window);
        register(newAction);
        openAction = new OpenFastDiagramAction(window);
        register(openAction);
        runAction = new GenerateFastAction(window);
        register(runAction);
        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
        saveAction = ActionFactory.SAVE.create(window);
        register(saveAction);
        saveAsAction = ActionFactory.SAVE_AS.create(window);
        register(saveAsAction);
        copyAction = ActionFactory.COPY.create(window);
        register(copyAction);
        pasteAction = ActionFactory.PASTE.create(window);
        register(pasteAction);
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
        helpAction = ActionFactory.HELP_CONTENTS.create(window);
        register(helpAction);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("&File",
                IWorkbenchActionConstants.M_FILE);
        MenuManager editMenu = new MenuManager("&Edit",
                IWorkbenchActionConstants.M_EDIT);
        MenuManager navMenu = new MenuManager("&Navigate",
                IWorkbenchActionConstants.M_NAVIGATE);
        MenuManager runMenu = new MenuManager("&Run");
        MenuManager helpMenu = new MenuManager("&Help",
                IWorkbenchActionConstants.M_HELP);

        fileMenu.add(newAction);
        fileMenu.add(openAction);
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
        editMenu.add(new Separator());
        editMenu.add(copyAction);
        editMenu.add(pasteAction);
        menuBar.add(editMenu);

        runMenu.add(runAction);
        menuBar.add(runMenu);

        menuBar.add(navMenu);

        helpMenu.add(helpAction);
        helpMenu.add(new Separator());
        helpMenu.add(aboutAction);
        menuBar.add(helpMenu);
    }

    protected void fillCoolBar(ICoolBarManager coolBar) {
        IToolBarManager toolBar = new ToolBarManager(coolBar.getStyle());
        coolBar.add(toolBar);
        toolBar.add(newAction);
        toolBar.add(saveAction);
        toolBar.add(new Separator());
        toolBar.add(undoAction);
        toolBar.add(redoAction);
    }
}
