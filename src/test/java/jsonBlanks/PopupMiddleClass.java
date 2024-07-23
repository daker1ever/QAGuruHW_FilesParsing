package jsonBlanks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PopupMiddleClass {


    private final List<MenuItemInnerClass> menuItem;

    @JsonCreator
    public PopupMiddleClass(@JsonProperty("menuitem") List<MenuItemInnerClass> menuItem) {
        this.menuItem = menuItem;
    }

    public List<MenuItemInnerClass> getMenuItem() {
        return menuItem;
    }
}
