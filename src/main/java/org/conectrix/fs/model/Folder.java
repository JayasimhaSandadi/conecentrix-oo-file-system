package org.conectrix.fs.model;

import java.util.ArrayList;
import java.util.List;

import static org.conectrix.fs.constants.FileSystemConstants.FILE_SEPARATOR;

/**
 * The type Folder.
 */
public class Folder extends Entity {
    private final List<Entity> children = new ArrayList<>();

    /**
     * Instantiates a new Folder.
     *
     * @param name the name
     * @param path the path
     */
    public Folder(String name, String path) {
        super(name, "Folder", path + FILE_SEPARATOR + name);
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

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int calculateSize() {
        return children.stream()
                .mapToInt(Entity::calculateSize)
                .sum();
    }
}
