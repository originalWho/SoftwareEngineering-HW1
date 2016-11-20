package davletshin.artur.controller;

import davletshin.artur.exception.BookCannotBeBorrowedException;
import davletshin.artur.exception.BookCannotBeReturnedException;
import davletshin.artur.model.Book;
import davletshin.artur.model.Visitor;
import davletshin.artur.source.BookSourceI;

import java.util.Date;
import java.util.List;

/**
 * Created by Artur on 11/12/16.
 */
public interface BookControllerI {
    List<Book> getBooks();
    void setSource(BookSourceI bookSource);
    Date rentBook(Visitor visitor, Book book) throws BookCannotBeBorrowedException;
    void returnBook(Visitor visitor, Book book) throws BookCannotBeReturnedException;
}
