package org.conectrix.fs.model;

/**
 * Abstract Entity Class.
 */
public abstract class Entity {
    protected String name;
    protected String path;
    protected String type;
    protected int size;

    /**
     * Instantiates a new Entity.
     *
     * @param name the name
     * @param type the type
     * @param path the path
     */
    protected Entity(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.path = path;
    }

    /**
     * Calculate size int.
     *
     * @return the int
     */
    public abstract int calculateSize();

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return this.size;
    }
}