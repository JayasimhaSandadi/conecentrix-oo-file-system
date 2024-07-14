package org.conectrix.fs;

import org.conectrix.fs.service.FileSystemService;

/**
 * Perform file system operations.
 */
public class FileSystemDemo {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            FileSystemService fs = new FileSystemService();

            fs.create("Drive", "C:", "");

            fs.create("Folder", "Documents", "C:");

            fs.create("TextFile", "notes.txt", "C:\\Documents");

            fs.create("ZipFile", "archive.zip", "C:\\Documents");

            fs.writeToFile("C:\\Documents\\notes.txt", "Hello, World!");

            System.out.println("Drive memory size : " + fs.entityMap.get("C:").calculateSize());

            System.out.println("Size of Documents: " + fs.entityMap.get("C:\\Documents").calculateSize());

            fs.move("C:\\Documents\\notes.txt", "C:\\Documents\\archive.zip");

            System.out.println("Size of archive.zip: " + fs.entityMap.get("C:\\Documents\\archive.zip").calculateSize());

            fs.delete("C:\\Documents");

            System.out.println("Number of entities, after deleting Users: " + fs.entityMap.size());

            System.out.println("Drive memory size : " + fs.entityMap.get("C:").calculateSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

