package seedu.address.testutil;

import seedu.address.model.menu.Menu;

/**
 * A utility class containing a list of {@code Menu} objects to be used in tests.
 */
public class TypicalMenus {
    public static final Menu MENU_1 = new MenuBuilder().withFood(TypicalFoods.PRATA)
            .withFood(TypicalFoods.MILO).withFood(TypicalFoods.CHEESE_PRATA).build();
    public static final Menu MENU_2 = new MenuBuilder().withFood(TypicalFoods.NASI_GORENG)
            .withFood(TypicalFoods.NUGGETS).build();
    public static final Menu EMPTY_MENU = new MenuBuilder().build();


    private TypicalMenus() {
        //Prevents instantiation
    }
}
