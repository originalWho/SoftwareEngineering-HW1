package davletshin.artur.controller;

import davletshin.artur.exception.BookCannotBeBorrowedException;
import davletshin.artur.exception.BookCannotBeReturnedException;
import davletshin.artur.model.Book;
import davletshin.artur.model.Visitor;

import java.util.*;

/**
 * Created by Artur on 11/11/16.
 */
public class VisitorController implements VisitorControllerI {
    private final List<Visitor> visitors;

    private static class Singleton {
        private static final VisitorController INSTANCE = new VisitorController();
    }

    public static VisitorController getInstance() {
        return Singleton.INSTANCE;
    }

    private VisitorController() {
        this.visitors = new LinkedList<>();
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

    public Map<Visitor, List<Book>> getVisitorsWithOverdueBooks() {
        if (visitors.isEmpty())
            return null;
        Map<Visitor, List<Book>> visitorsWithOverdueBooks = new HashMap<>();
        for (Visitor visitor : visitors) {
            for (Book book : visitor.getBorrowedBooks()) {
                if (book.isOverdue()) {
                    if (!visitorsWithOverdueBooks.containsKey(visitor)) {
                        visitorsWithOverdueBooks.put(visitor, new ArrayList<>());
                    }
                    visitorsWithOverdueBooks.get(visitor).add(book);
                }
            }
        }
        return visitorsWithOverdueBooks;
    }

    public void rentBook(Visitor visitor, Book book) throws BookCannotBeBorrowedException {
        if (visitor == null || book == null) {
            throw new BookCannotBeBorrowedException("Invalid input");
        }
        if (!BookController.getInstance().getBooks().contains(book)) {
            throw new BookCannotBeBorrowedException("There is no such a book");
        }
        if (!visitors.contains(visitor)) {
            throw new BookCannotBeBorrowedException("There is no such a visitor");
        }
        if (!visitor.canBorrow()) {
            throw new BookCannotBeBorrowedException(
                    "Visitor " + visitor.getName()
                            + " has exceeded the allowed number of borrowed books"
            );
        }
        if (book.isBorrowed() && book.getBorrowedBy() != visitor) {
            throw new BookCannotBeBorrowedException(
                    book.getTitle() + " by " + book.getAuthor()
                            + " is already borrowed by " + book.getBorrowedBy().getName()
            );
        }

        visitor.getBorrowedBooks().add(book);
    }

    public void returnBook(Visitor visitor, Book book) throws BookCannotBeReturnedException {
        if (visitor == null || book == null) {
            throw new BookCannotBeReturnedException("Invalid input");
        }
        if (!BookController.getInstance().getBooks().contains(book)) {
            throw new BookCannotBeReturnedException("There is no such a book");
        }
        if (!visitors.contains(visitor)) {
            throw new BookCannotBeReturnedException("There is no such a visitor");
        }
        /* Depends on where the function is called
        if (!book.isBorrowed()) {
            throw new BookCannotBeReturnedException(
                    book.getTitle() + " by " + book.getAuthor()
                            + " is not borrowed currently"
            );
        }
        */
        if (!visitor.getBorrowedBooks().contains(book)) {
            throw new BookCannotBeReturnedException(
                    visitor.getName() + " is not the current owner of "
                            + book.getTitle() + " by " + book.getTitle()
            );
        }

        visitor.getBorrowedBooks().remove(book);
    }
}
