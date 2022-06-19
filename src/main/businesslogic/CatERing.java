package main.businesslogic;

import main.businesslogic.event.EventManager;
import main.businesslogic.menu.MenuManager;
import main.businesslogic.procedure.ProcedureManager;
import main.businesslogic.shift.ShiftManager;
import main.businesslogic.summarysheet.SummarySheetManager;
import main.businesslogic.user.UserManager;
import main.persistence.MenuPersistence;
import main.persistence.ShiftPersistence;
import main.persistence.SummarySheetPersistence;

public class CatERing {
    private static CatERing singleInstance;
    private final MenuManager menuMgr;
    private final ProcedureManager procedureMgr;
    private final UserManager userMgr;
    private final EventManager eventMgr;
    private final SummarySheetManager summarySheetMgr;
    private final ShiftManager shiftMgr;
    // Do we need MenuPersistence as a variable?
    private CatERing() {
        menuMgr = new MenuManager();
        procedureMgr = new ProcedureManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        summarySheetMgr = new SummarySheetManager();
        shiftMgr = new ShiftManager();
        menuMgr.addEventReceiver(new MenuPersistence());
        summarySheetMgr.addEventReceiver(new SummarySheetPersistence());
        shiftMgr.addEventReceiver(new ShiftPersistence());
    }

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public ProcedureManager getProcedureManager() {
        return procedureMgr;
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
