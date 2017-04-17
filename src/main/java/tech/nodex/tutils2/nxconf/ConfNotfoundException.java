package tech.nodex.tutils2.nxconf;

/**
 * Created by cz on 2017-3-7.
 */
public class ConfNotfoundException extends RuntimeException {
    public ConfNotfoundException() {
    }

    public ConfNotfoundException(String message) {
        super(message);
    }

    public ConfNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfNotfoundException(Throwable cause) {
        super(cause);
    }

    public ConfNotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
