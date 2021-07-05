package xyz.mini2436.fchat.exceptions;

/**
 * 消息异常,该异常内容用于前端通知
 *
 * @author mini2436
 * @date 2021-07-05 22:15
 **/
public class MessageException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public MessageException() {
        super("默认消息通知");
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public MessageException(String message) {
        super(message);
    }
}
