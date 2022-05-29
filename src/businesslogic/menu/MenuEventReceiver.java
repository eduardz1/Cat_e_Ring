package businesslogic.menu;

public interface MenuEventReceiver {
    void updateMenuCreated(Menu m);

    void updateSectionAdded(Menu m, Section sec);

    void updateMenuItemAdded(Menu m, MenuItem mi);

    void updateMenuFeaturesChanged(Menu m);

    void updateMenuTitleChanged(Menu m);

    void updateMenuPublishedState(Menu m);

    void updateMenuDeleted(Menu m);

    void updateSectionDeleted(Menu m, Section s, boolean itemsDeleted);

    void updateSectionChangedName(Menu m, Section s);

    void updateSectionsRearranged(Menu m);

    void updateFreeItemsRearranged(Menu m);

    void updateSectionItemsRearranged(Menu m, Section s);

    void updateItemSectionChanged(Menu m, Section s, MenuItem mi);

    void updateItemDescriptionChanged(Menu m, MenuItem mi);

    void updateItemDeleted(Menu m, Section sec, MenuItem mi);
}
