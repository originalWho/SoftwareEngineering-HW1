package davletshin.artur.source;

import davletshin.artur.model.Book;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Artur on 11/20/16.
 */
public class XMLSource implements BookSourceI {
    private List<Book> books;

    private XMLSource() {
        books = null;
    }

    private static class Singleton {
        private static final XMLSource INSTANCE = new XMLSource();
    }

    public static XMLSource getInstance() {
        return Singleton.INSTANCE;
    }

    public List<Book> returnBooks() {
        return books;
    }

    public void parse(String path) {
        //TODO implement XML file parsing
        books = new LinkedList<>();
    }
}
