package davletshin.artur.exception;

/**
 * Created by Artur on 11/12/16.
 */
public class BookCannotBeReturnedException extends Exception {
    public BookCannotBeReturnedException() {
    }

    public BookCannotBeReturnedException(String message) {
        super(message);
    }
}
