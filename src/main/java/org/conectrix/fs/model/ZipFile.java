package org.conectrix.fs.model;

import java.util.ArrayList;
import java.util.List;

import static org.conectrix.fs.constants.FileSystemConstants.FILE_SEPARATOR;

/**
 * The type Zip file.
 */
public class ZipFile extends Entity {
    private final List<Entity> children = new ArrayList<>();

    /**
     * Instantiates a new Zip file.
     *
     * @param name the name
     * @param path the path
     */
    public ZipFile(String name, String path) {
        super(name, "ZipFile", path + FILE_SEPARATOR + name);
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
        return children.stream()
                .mapToInt(Entity::calculateSize)
                .sum() / 2;// Zip file size is half of the contained entities' sizes
    }
}
