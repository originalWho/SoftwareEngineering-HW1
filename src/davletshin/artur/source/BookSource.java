package davletshin.artur.source;

import davletshin.artur.exception.InsupportableSourceException;
import davletshin.artur.model.Book;

import java.util.List;

/**
 * Created by Artur on 11/20/16.
 */
public class BookSource implements BookSourceI {
    private List<Book> books;

    private BookSource() {
        books = DefaultSource.getInstance().returnBooks();
    }

    private static class Singleton {
        private static final BookSource INSTANCE = new BookSource();
    }

    public static BookSource getInstance() {
        return Singleton.INSTANCE;
    }

    public void parse(String path) throws InsupportableSourceException {
        if (path == null || path.isEmpty()) {
            books = DefaultSource.getInstance().returnBooks();
            throw new InsupportableSourceException("Empty path is not permitted.");
        }
        if (path.length() < 5) {
            throw new InsupportableSourceException("Invalid path.");
        }
        if (path.endsWith(".txt")) {
            TextFileSource.getInstance().parse(path);
            books = TextFileSource.getInstance().returnBooks();
        } else if (path.endsWith(".xml")) {
            XMLSource.getInstance().parse(path);
            books = XMLSource.getInstance().returnBooks();
        } else {
            throw new InsupportableSourceException("This file extension is not supported. Use only .txt and .xml files.");
        }
    }

    public List<Book> returnBooks() {
        return books;
    }
}
