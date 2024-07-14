package org.conectrix.fs.model;

import static org.conectrix.fs.constants.FileSystemConstants.FILE_SEPARATOR;

/**
 * The type Text file.
 */
public class TextFile extends Entity {
    private String content;

    /**
     * Instantiates a new Text file.
     *
     * @param name the name
     * @param path the path
     */
    public TextFile(String name, String path) {
        super(name, "TextFile", path + FILE_SEPARATOR + name);
    }

    /**
     * Write to file.
     *
     * @param content the content
     */
    public void writeToFile(String content) {
        this.content = content;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
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
        return content != null ? content.length() : 0;
    }
}
