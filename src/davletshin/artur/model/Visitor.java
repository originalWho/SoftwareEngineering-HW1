package davletshin.artur.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Artur on 11/11/16.
 */
public class Visitor {
    private final String name;
    private final List<Book> borrowedBooks;

    public Visitor(String name) {
        this.name = name;
        borrowedBooks = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean canBorrow() {
        return borrowedBooks.size() < 3;
    }
}
