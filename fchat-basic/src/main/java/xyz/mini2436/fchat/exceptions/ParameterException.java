package xyz.mini2436.fchat.exceptions;

/**
 * 参数转换异常
 *
 * @author mini2436
 * @date 2021-07-05 09:13
 **/
public class ParameterException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ParameterException() {
        super("参数或参数转换发生异常");
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ParameterException(String message) {
        super(message);
    }
}
