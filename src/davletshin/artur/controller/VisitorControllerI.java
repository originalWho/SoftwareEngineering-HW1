package davletshin.artur.controller;

import davletshin.artur.exception.BookCannotBeBorrowedException;
import davletshin.artur.exception.BookCannotBeReturnedException;
import davletshin.artur.model.Book;
import davletshin.artur.model.Visitor;

import java.util.List;
import java.util.Map;

/**
 * Created by Artur on 11/12/16.
 */
public interface VisitorControllerI {
    List<Visitor> getVisitors();
    Map<Visitor, List<Book>> getVisitorsWithOverdueBooks();
    void rentBook(Visitor visitor, Book book) throws BookCannotBeBorrowedException;
    void returnBook(Visitor visitor, Book book) throws BookCannotBeReturnedException;
}
