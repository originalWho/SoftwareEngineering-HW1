package davletshin.artur.source;

import davletshin.artur.model.Book;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Artur on 11/20/16.
 */
public class TextFileSource implements BookSourceI {
    private List<Book> books;

    private TextFileSource() {
        books = null;
    }

    private static class Singleton {
        private static final TextFileSource INSTANCE = new TextFileSource();
    }

    public static TextFileSource getInstance() {
        return Singleton.INSTANCE;
    }

    public List<Book> returnBooks() {
        return books;
    }

    public void parse(String path) {
        //TODO implement text file parsing
        books = new LinkedList<>();
    }
}
