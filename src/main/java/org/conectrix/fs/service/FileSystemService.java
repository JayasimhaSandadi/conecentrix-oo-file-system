package org.conectrix.fs.service;

import org.conectrix.fs.enums.FileSystemType;
import org.conectrix.fs.exception.FileSystemException;
import org.conectrix.fs.model.Drive;
import org.conectrix.fs.model.Entity;
import org.conectrix.fs.model.Folder;
import org.conectrix.fs.model.TextFile;
import org.conectrix.fs.model.ZipFile;
import org.conectrix.fs.utils.FileSystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.conectrix.fs.constants.FileSystemConstants.FILE_SEPARATOR;
import static org.conectrix.fs.constants.FileSystemConstants.ILLEGAL_FILE_SYSTEM_TYPE;
import static org.conectrix.fs.constants.FileSystemConstants.PATH_ALREADY_EXISTS;
import static org.conectrix.fs.constants.FileSystemConstants.PATH_NOT_FOUND;
import static org.conectrix.fs.enums.FileSystemType.DRIVE;


/**
 * The type File system service.
 */
public class FileSystemService {
    public final Map<String, Entity> entityMap = new HashMap<>();

    /**
     * Helps create file system entities internally as per the type.
     *
     * @param type
     * @param name
     * @param parentPath
     */
    private void internalCreate(FileSystemType type, String name, String parentPath) {
        if (type.equals(DRIVE)) {
            // Drive creation does not require a parent
            if (entityMap.containsKey(name)) throw new FileSystemException(PATH_ALREADY_EXISTS);
            Entity drive = new Drive(name);
            entityMap.put(drive.getPath(), drive);
        } else {
            // Check parent entity existence
            Entity parent = entityMap.get(parentPath);
            if (parent == null) throw new FileSystemException(PATH_NOT_FOUND);
            if (entityMap.containsKey(parentPath + "\\" + name)) throw new FileSystemException(PATH_ALREADY_EXISTS);

            Entity newEntity = switch (type) {
                case FOLDER -> new Folder(name, parentPath);
                case TEXT_FILE -> new TextFile(name, parentPath);
                case ZIP_FILE -> new ZipFile(name, parentPath);
                default -> throw new FileSystemException(ILLEGAL_FILE_SYSTEM_TYPE);
            };
            entityMap.put(newEntity.getPath(), newEntity);

            if (parent instanceof Drive drive) {
                drive.addChild(newEntity);
            } else if (parent instanceof Folder folder) {
                folder.addChild(newEntity);
            }
        }
    }

    /**
     * perform deletes operations recursively on the entity and its children.
     *
     * @param path the path
     */
    public void delete(String path) {
        Entity entity = entityMap.get(path);
        if (entity == null) throw new FileSystemException(PATH_NOT_FOUND);

        // Recursively remove the entity and its children
        recursiveDelete(entity);

        // Finally, remove the entity from the entity map
        entityMap.remove(path);
    }

    /**
     * Recursively delete the entity and its children.
     *
     * @param entity
     */
    private void recursiveDelete(Entity entity) {
        if (entity instanceof Folder folder) {
            // Recursively delete all children
            for (Entity child : new ArrayList<>(folder.getChildren())) {
                recursiveDelete(child);
            }
            entityMap.remove(entity.getPath());
        } else if (entity instanceof ZipFile zipFile) {
            // Recursively delete all children
            for (Entity child : new ArrayList<>(zipFile.getChildren())) {
                recursiveDelete(child);
            }
            entityMap.remove(entity.getPath());
        } else {
            // Remove the entity from the entity map
            entityMap.remove(entity.getPath());
        }


        // Now the entity can be removed from its parent's children
        String parentPath = entity.getPath().substring(0, entity.getPath().lastIndexOf('\\'));
        Entity parent = entityMap.get(parentPath);
        if (parent instanceof Folder folder) {
            folder.getChildren().remove(entity);
        } else if (parent instanceof Drive drive) {
            drive.getChildren().remove(entity);
        } else if (parent instanceof ZipFile zipFile) {
            zipFile.getChildren().remove(entity);
        }
    }

    /**
     * move file system entity from source path to destination path.
     *
     * @param sourcePath      the source path
     * @param destinationPath the destination path
     */
    public void move(String sourcePath, String destinationPath) {

        if (FileSystemUtil.isEmpty(sourcePath) || FileSystemUtil.isEmpty(destinationPath)) {
            throw new FileSystemException("Invalid source or destination path");
        }

        Entity entity = entityMap.get(sourcePath);
        if (!(entity instanceof TextFile textFile)) throw new FileSystemException("Unsupported file type to move");

        if (entityMap.containsKey(destinationPath + FILE_SEPARATOR + entity.getName())) {
            throw new FileSystemException(PATH_ALREADY_EXISTS);
        }

        // Update path and add to the new parent
        delete(sourcePath); // Remove it from the old parent
        String newPath = destinationPath + FILE_SEPARATOR + entity.getName();

        textFile.setPath(newPath);

        entityMap.put(newPath, textFile);

        // Add to the new parent
        Entity newParent = entityMap.get(destinationPath);
        if (newParent instanceof Folder folder) {
            folder.addChild(entity);
        } else if (newParent instanceof Drive drive) {
            drive.addChild(entity);
        } else if (newParent instanceof ZipFile zipFile) {
            zipFile.addChild(textFile);
        }
    }

    /**
     * Write to file.
     *
     * @param path    the path
     * @param content the content
     */
    public void writeToFile(String path, String content) {
        if (FileSystemUtil.isEmpty(path) || FileSystemUtil.isEmpty(content)) {
            throw new FileSystemException("Invalid source or contents");
        }

        Entity entity = entityMap.get(path);
        if (!(entity instanceof TextFile))
            throw new FileSystemException("Path not found or Not a text file");
        ((TextFile) entity).writeToFile(content);
    }


    /**
     * Create api.
     *
     * @param type       the type
     * @param name       the name
     * @param parentPath the parent path
     */
    public void create(String type, String name, String parentPath) {
        if (FileSystemType.fromString(type) == DRIVE) {
            internalCreate(FileSystemType.fromString(type), name, "");
        } else {
            internalCreate(FileSystemType.fromString(type), name, parentPath);
        }
    }

}


