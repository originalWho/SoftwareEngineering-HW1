package davletshin.artur;

import davletshin.artur.controller.BookController;
import davletshin.artur.controller.VisitorController;
import davletshin.artur.exception.BookCannotBeBorrowedException;
import davletshin.artur.exception.BookCannotBeReturnedException;
import davletshin.artur.exception.InsupportableSourceException;
import davletshin.artur.model.Book;
import davletshin.artur.model.Visitor;
import davletshin.artur.source.BookSource;
import davletshin.artur.source.BookSourceI;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        preload();

        help();
        System.out.println();

        boolean done = false;
        Scanner in = new Scanner(System.in);
        while (!done) {
            String inputQuery = in.next();
            int visitorIndex;
            int bookIndex;
            String result;
            switch (inputQuery) {
                case "-help":
                    help();
                    break;
                case "-source":
                    String source = in.nextLine();
                    result = chooseBookSource(source);
                    System.out.println(result);
                    break;
                case "-books":
                    boolean showOwners = in.next().equals("y");
                    printBooks(showOwners);
                    break;
                case "-visitors":
                    boolean showBooks = in.next().equals("y");
                    printVisitors(showBooks);
                    break;
                case "-overdue":
                    printVisitorsWithOverdueBooks();
                    break;
                case "-rent":
                    visitorIndex = in.nextInt();
                    bookIndex = in.nextInt();
                    in.nextLine();
                    result = rentBook(visitorIndex, bookIndex);
                    System.out.println(result);
                    break;
                case "-return":
                    visitorIndex = in.nextInt();
                    bookIndex = in.nextInt();
                    in.nextLine();
                    result = returnBook(visitorIndex, bookIndex);
                    System.out.println(result);
                    break;
                case "-exit":
                    done = true;
                    break;
                default:
                    System.out.println("Invalid source. Enter -help to print list of valid commands.");
                    break;
            }
        }
    }

    private static String chooseBookSource(String sourcePath) {
        if (sourcePath == null || sourcePath.isEmpty()) {
            BookController.getInstance().setSource(BookSource.getInstance());
            return "List of preloaded books was set as the book source.";
        }
        sourcePath = sourcePath.trim();
        BookSourceI source = BookSource.getInstance();
        try {
            source.parse(sourcePath);
        } catch (InsupportableSourceException e) {
            return e.getMessage();
        }
        BookController.getInstance().setSource(BookSource.getInstance());
        return sourcePath + " is set as the book source.";
    }

    private static void printBooks(boolean showOwner) {
        List<Book> books = BookController.getInstance().getBooks();
        if (books == null || books.isEmpty()) {
            System.out.println("There are no books.");
            return;
        }
        int index = 0;
        for (Book book : books) {
            System.out.println(++index + ". " +
                    book.getTitle() + " by " + book.getAuthor()
                    + (showOwner && book.isBorrowed()? " (is borrowed by "
                    + book.getBorrowedBy().getName() + " due to " + book.getDueTo() + ")" : "")
            );
        }
    }

    private static void printVisitors(boolean showBooks) {
        List<Visitor> visitors = VisitorController.getInstance().getVisitors();
        if (visitors == null || visitors.isEmpty()) {
            System.out.println("There are no visitors.");
            return;
        }
        int index = 0;
        for (Visitor visitor : visitors) {
            System.out.println(++index + ". " + visitor.getName());
            if (showBooks) {
                for (Book book : visitor.getBorrowedBooks()) {
                    System.out.println(
                            "\t" + book.getTitle() + " by " + book.getAuthor()
                                    + ", due to " + book.getDueTo()
                    );
                }
            }
        }
    }

    private static void printVisitorsWithOverdueBooks() {
        Map<Visitor, List<Book>> visitors = VisitorController.getInstance().getVisitorsWithOverdueBooks();
        if (visitors.isEmpty()) {
            System.out.println("There are no visitors with overdue books.");
            return;
        }
        for (Visitor visitor : visitors.keySet()) {
            System.out.println(visitor.getName() + " has following overdue books:");
            for (Book book : visitors.get(visitor)) {
                System.out.println("\t" + book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    private static String rentBook(int visitorIndex, int bookIndex) {
        Visitor visitor = getVisitorByIndex(visitorIndex);
        Book book = getBookByIndex(bookIndex);

        try {
            Date dueTo = BookController.getInstance().rentBook(visitor, book);
            VisitorController.getInstance().rentBook(visitor, book);
            return visitor.getName() + " has borrowed " + book.getTitle()
                    + " by " + book.getAuthor() + ", due to " + dueTo;
        } catch (BookCannotBeBorrowedException e) {
            return e.getMessage();
        }
    }

    private static String returnBook(int visitorIndex, int bookIndex) {
        Visitor visitor = getVisitorByIndex(visitorIndex);
        Book book = getBookByIndex(bookIndex);

        try {
            BookController.getInstance().returnBook(visitor, book);
            VisitorController.getInstance().returnBook(visitor, book);
        } catch (BookCannotBeReturnedException e) {
            System.out.println(e.getMessage());
        }

        return visitor.getName() + " has returned " + book.getTitle() + " by " + book.getAuthor();
    }

    private static Book getBookByIndex(int bookIndex) {
        Book book;
        try {
            book = BookController.getInstance().getBooks().get(bookIndex - 1);
        } catch (Exception e) {
            book = null;
        }
        return book;
    }

    private static Visitor getVisitorByIndex(int visitorIndex) {
        Visitor visitor;
        try {
            visitor = VisitorController.getInstance().getVisitors().get(visitorIndex - 1);
        } catch (Exception e) {
            visitor = null;
        }
        return visitor;
    }

    private static void preload() {
        BookController bookController = BookController.getInstance();
        VisitorController visitorController = VisitorController.getInstance();
        chooseBookSource(null);

        for (int i = 0; i < 5; i++) {
            Visitor visitor = new Visitor("Visitor " + (i + 1));
            visitorController.getVisitors().add(visitor);
        }

        Date monthAgo = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthAgo);
        calendar.add(Calendar.MONTH, -2);

        rentBook(2, 4);
        rentBook(2, 5);
        rentBook(2, 8);
        rentBook(3, 7);
        rentBook(5, 1);
        bookController.getBooks().get(3).setDueTo(calendar.getTime());
        bookController.getBooks().get(6).setDueTo(calendar.getTime());
    }

    private static void help() {
        System.out.println("-help\t\t\t\t\t\t\t\t\tPrint valid commands.");
        System.out.println("-source [path]\t\t\t\t\t\t\tChoose book source. If path is empty, preloaded books will be set as the book source.");
        System.out.println("-books [y/n]\t\t\t\t\t\t\tPrint list of books in the library. Enter 'y' to show current book owners, else 'n'.");
        System.out.println("-visitors [y/n]\t\t\t\t\t\t\tPrint list of library visitors. Enter 'y' to show books borrowed by a visitor, else 'n'.");
        System.out.println("-overdue\t\t\t\t\t\t\t\tPrint list of visitors with overdue books");
        System.out.println("-rent [visitor index] [book index]\t\tRent a book to a visitor.");
        System.out.println("-return [visitor index] [book index]\tLet a visitor return a book.");
        System.out.println("-exit\t\t\t\t\t\t\t\t\tExit.");
    }
}
