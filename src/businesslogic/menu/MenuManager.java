package businesslogic.menu;

import java.util.ArrayList;

public class MenuManager {
    private String[] menuFeatures;
    private Menu currentMenu;
    private ArrayList<MenuEventReceiver> eventReceivers;
}
