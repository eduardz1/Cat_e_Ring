package main.businesslogic;

import main.businesslogic.event.EventManager;
import main.businesslogic.menu.MenuManager;
import main.businesslogic.procedure.ProcedureManager;
import main.businesslogic.shift.ShiftManager;
import main.businesslogic.summarysheet.SummarySheetManager;
import main.businesslogic.user.UserManager;
import main.persistence.MenuPersistence;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private final MenuManager menuMgr;
    private final ProcedureManager recipeMgr;
    private final UserManager userMgr;
    private final EventManager eventMgr;
    private final SummarySheetManager summarySheetMgr;
    private final ShiftManager shiftMgr;

    // Do we need MenuPersistance as a variable?
    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new ProcedureManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        summarySheetMgr = new SummarySheetManager();
        shiftMgr = new ShiftManager();
        menuMgr.addEventReceiver(new MenuPersistence());
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public ProcedureManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() {
        return eventMgr;
    }

    public ShiftManager getShiftManager() {
        return shiftMgr;
    }

    public SummarySheetManager getSummarySheetManager() {
        return summarySheetMgr;
    }

}