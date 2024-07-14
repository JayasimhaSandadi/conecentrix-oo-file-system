package org.conectrix.fs.model;


import java.util.ArrayList;
import java.util.List;

/**
 * The type Drive.
 */
public class Drive extends Entity {
    private final List<Entity> children = new ArrayList<>();

    /**
     * Instantiates a new Drive.
     *
     * @param name the name
     */
    public Drive(String name) {
        super(name, "Drive", name); // Path is just the name for drives
    }

    /**
     * Add child.
     *
     * @param child the child
     */
    public void addChild(Entity child) {
        children.add(child);
    }

    /**
     * Gets children.
     *
     * @return the children
     */
    public List<Entity> getChildren() {
        return children;
    }


    @Override
    public int calculateSize() {
        int size = 0;
        for (Entity child : children) {
            size += child.calculateSize();
        }
        return size;
    }

}
