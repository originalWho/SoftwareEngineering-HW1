package davletshin.artur.exception;

/**
 * Created by Artur on 11/12/16.
 */
public class BookCannotBeBorrowedException extends Exception {
    public BookCannotBeBorrowedException() {
        super();
    }

    public BookCannotBeBorrowedException(String message) {
        super(message);
    }
}
