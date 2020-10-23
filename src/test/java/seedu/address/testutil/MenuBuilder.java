package seedu.address.testutil;

import seedu.address.model.food.Food;
import seedu.address.model.menu.Menu;

/**
 * A utility class to help with building Menu objects.
 * Example usage: <br>
 *     {@code Menu ab = new MenuBuilder().withFood("John", "Doe").build();}
 */
public class MenuBuilder {

    private Menu menu;

    public MenuBuilder() {
        menu = new Menu();
    }

    public MenuBuilder(Menu menu) {
        this.menu = menu;
    }

    /**
     * Adds a new {@code Food} to the {@code Menu} that we are building.
     */
    public MenuBuilder withFood(Food food) {
        menu.add(food);
        return this;
    }

    public Menu build() {
        return menu;
    }
}
