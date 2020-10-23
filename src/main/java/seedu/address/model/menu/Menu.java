package seedu.address.model.menu;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.food.Food;
import seedu.address.model.food.exceptions.DuplicateFoodException;
import seedu.address.model.food.exceptions.FoodNotFoundException;
import seedu.address.storage.JsonAdaptedFood;

/**
 * A list of foods that enforces uniqueness between its elements and does not allow nulls.
 * A food is considered unique by comparing using {@code Food#equals(Food)}. As such, adding and updating of
 * foods uses Food#equals(Food) for equality so as to ensure that the food being added or updated is
 * unique in terms of identity in the Menu. The removal of a food also uses Food#equals(Object) so
 * as to ensure that the food with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Food#equals(Object) (Food)
 */
public class Menu implements Iterable<Food> {

    public static final String MESSAGE_EMPTY_MENU = "Menu cannot be empty!";
    private final ObservableList<Food> internalList = FXCollections.observableArrayList();
    private final ObservableList<Food> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent food as the given argument.
     */
    public boolean contains(Food toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a food to the list.
     * The food must not already exist in the list.
     */
    public void add(Food toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFoodException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the food {@code target} in the list with {@code editedFood}.
     * {@code target} must exist in the list.
     * The food identity of {@code editedFood} must not be the same as another existing food in the list.
     */
    public void setFood(Food target, Food editedFood) {
        requireAllNonNull(target, editedFood);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodNotFoundException();
        }

        if (!target.equals(editedFood) && contains(editedFood)) {
            throw new DuplicateFoodException();
        }

        internalList.set(index, editedFood);
    }

    /**
     * Removes the equivalent food from the list.
     * The food must exist in the list.
     */
    public void remove(Food toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FoodNotFoundException();
        }
    }

    public void setFoods(Menu replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }


    /**
     * Replaces the contents of this list with {@code foods}.
     * {@code foods} must not contain duplicate foods.
     */
    public void setFoods(List<Food> foods) {
        requireAllNonNull(foods);
        if (!foodsAreUnique(foods)) {
            throw new DuplicateFoodException();
        }

        internalList.setAll(foods);
    }

    /**
     * Replaces the contents of this list with {@code foods}.
     * {@code foods} must not contain duplicate foods.
     */
    public void setOrderedFoodList(List<JsonAdaptedFood> foods) {
        requireAllNonNull(foods);
        List<Food> foodList = foods.stream().map(x -> {
            try {
                return x.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        if (!foodsAreUnique(foodList)) {
            throw new DuplicateFoodException();
        }

        internalList.setAll(foodList);
    }

    /**
     * Gets the contents of the list.
     * {@code foods} must not contain duplicate foods.
     */
    public List<JsonAdaptedFood> getFoods() {
        requireAllNonNull(internalList);
        ArrayList<JsonAdaptedFood> foodList = new ArrayList<>();

        for (Food food : internalList) {
            foodList.add(new JsonAdaptedFood(food));
        }
        return foodList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Food> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Food> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Menu // instanceof handles nulls
                        && internalList.equals(((Menu) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code foods} contains only unique foods.
     */
    private boolean foodsAreUnique(List<Food> foods) {
        for (int i = 0; i < foods.size() - 1; i++) {
            for (int j = i + 1; j < foods.size(); j++) {
                if (foods.get(i).equals(foods.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
