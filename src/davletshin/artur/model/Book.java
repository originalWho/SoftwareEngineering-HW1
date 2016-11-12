package davletshin.artur.model;

import java.util.Date;

/**
 * Created by Artur on 11/11/16.
 */
public class Book {
    private final String title;
    private final String author;
    private Visitor borrowedBy;
    private Date checkedOutOn;
    private Date dueTo;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.borrowedBy = null;
        this.checkedOutOn = null;
        this.dueTo = null;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Visitor getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(Visitor borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public boolean isBorrowed() {
        return borrowedBy != null;
    }

    public Date getCheckedOutOn() {
        return checkedOutOn;
    }

    public void setCheckedOutOn(Date checkedOutOn) {
        this.checkedOutOn = checkedOutOn;
    }

    public Date getDueTo() {
        return dueTo;
    }

    public void setDueTo(Date dueTo) {
        this.dueTo = dueTo;
    }

    public boolean isOverdue() {
        if (borrowedBy == null)
            return false;
        Date currentDate = new Date();
        return currentDate.after(dueTo);
    }
}
