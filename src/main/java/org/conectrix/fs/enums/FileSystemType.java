package org.conectrix.fs.enums;

/**
 * The enum File system type.
 */
public enum FileSystemType {
    DRIVE("Drive"),
    FOLDER("Folder"),
    TEXT_FILE("TextFile"),
    ZIP_FILE("ZipFile");

    private final String typeName;

    FileSystemType(String typeName) {
        this.typeName = typeName;
    }

    /**
     * From string file system type.
     *
     * @param type the type
     * @return the file system type
     */
    public static FileSystemType fromString(String type) {
        for (FileSystemType fsType : values()) {
            if (fsType.getTypeName().equalsIgnoreCase(type)) {
                return fsType;
            }
        }
        throw new IllegalArgumentException("Illegal File System Type " + type);
    }

    /**
     * Gets type name.
     *
     * @return the type name
     */
    public String getTypeName() {
        return typeName;
    }
}