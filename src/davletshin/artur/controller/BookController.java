package davletshin.artur.controller;

import davletshin.artur.exception.BookCannotBeBorrowedException;
import davletshin.artur.exception.BookCannotBeReturnedException;
import davletshin.artur.model.Book;
import davletshin.artur.model.Visitor;
import davletshin.artur.source.BookSourceI;

import java.util.*;

/**
 * Created by Artur on 11/11/16.
 */
public class BookController implements BookControllerI {
    private List<Book> books;

    private static class Singleton {
        private static final BookController INSTANCE = new BookController();
    }

    private BookController() {
        this.books = new LinkedList<>();
    }

    public static BookController getInstance() {
        return Singleton.INSTANCE;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Date rentBook(Visitor visitor, Book book) throws BookCannotBeBorrowedException {
        if (visitor == null || book == null) {
            throw new BookCannotBeBorrowedException("Invalid source.");
        }
        if (!books.contains(book)) {
            throw new BookCannotBeBorrowedException("There is no such a book.");
        }
        if (!VisitorController.getInstance().getVisitors().contains(visitor)) {
            throw new BookCannotBeBorrowedException("There is no such a visitor.");
        }
        if (!visitor.canBorrow()) {
            throw new BookCannotBeBorrowedException(
                    "Visitor " + visitor.getName()
                    + " has exceeded the allowed number of borrowed books."
            );
        }
        if (book.isBorrowed()) {
            throw new BookCannotBeBorrowedException(
                    book.getTitle() + " by " + book.getAuthor()
                            + " is already borrowed by " + book.getBorrowedBy().getName()
            );
        }

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);

        book.setCheckedOutOn(today);
        book.setDueTo(calendar.getTime());
        book.setBorrowedBy(visitor);

        return calendar.getTime();
    }

    public void returnBook(Visitor visitor, Book book) throws BookCannotBeReturnedException {
        if (visitor == null || book == null) {
            throw new BookCannotBeReturnedException("Invalid source.");
        }
        if (!books.contains(book)) {
            throw new BookCannotBeReturnedException("There is no such a book.");
        }
        if (!VisitorController.getInstance().getVisitors().contains(visitor)) {
            throw new BookCannotBeReturnedException("There is no such a visitor.");
        }
        if (!book.isBorrowed()) {
            throw new BookCannotBeReturnedException(
                    book.getTitle() + " by " + book.getAuthor()
                            + " is not borrowed currently."
            );
        }
        if (!visitor.getBorrowedBooks().contains(book)) {
            throw new BookCannotBeReturnedException(
                    visitor.getName() + " is not the current owner of "
                            + book.getTitle() + " by " + book.getTitle()
            );
        }

        book.setBorrowedBy(null);
        book.setDueTo(null);
        book.setCheckedOutOn(null);
    }

    public void setSource(BookSourceI bookSource) {
        books = bookSource.returnBooks();
    }
}
