package davletshin.artur.source;

import davletshin.artur.model.Book;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Artur on 11/20/16.
 */
public class DefaultSource implements BookSourceI {
    private static final int BOOKS_NUMBER = 10;

    private final List<Book> books;

    private static class Singleton {
        private static final DefaultSource INSTANCE = new DefaultSource();
    }

    private DefaultSource() {
        books = new LinkedList<>();
    }

    public static DefaultSource getInstance() {
        return Singleton.INSTANCE;
    }

    public List<Book> returnBooks() {
        parse(null);
        return books;
    }

    public void parse(String path) {
        if (books.isEmpty()) {
            for (int i = 0; i < BOOKS_NUMBER; i++) {
                Book book = new Book("title" + i * i, "author" + i * 2);
                books.add(book);
            }
        }
    }
}
