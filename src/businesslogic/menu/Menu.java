package businesslogic.menu;

import businesslogic.user.User;
import java.util.ArrayList;

public class Menu {
    private String title;
    private boolean published;
    private boolean inUse;
    private String[] features;
    private boolean[] featureValues;
    private ArrayList<MenuItem> freeItems;
    private ArrayList<Section> sections;

    private User owner;
}
