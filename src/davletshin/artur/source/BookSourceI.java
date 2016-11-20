package davletshin.artur.source;

import davletshin.artur.exception.InsupportableSourceException;
import davletshin.artur.model.Book;

import java.util.List;

/**
 * Created by Artur on 11/20/16.
 */
public interface BookSourceI {
    void parse(String path) throws InsupportableSourceException;
    List<Book> returnBooks();
}
