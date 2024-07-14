package org.conectrix.fs.exception;

/**
 * The type File system exception.
 */
public class FileSystemException extends RuntimeException {

    /**
     * Instantiates a new File system exception.
     *
     * @param message the message
     */
    public FileSystemException(String message) {
        super(message);
    }

    /**
     * Instantiates a new File system exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public FileSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
