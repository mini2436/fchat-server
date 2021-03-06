package xyz.mini2436.fchat.exceptions;

/**
 * 系统异常
 *
 * @author mini2436
 * @date 2021-07-05 09:11
 **/
public class SystemException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public SystemException() {
        super("当前系统发生未知异常");
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public SystemException(String message) {
        super(message);
    }
}
