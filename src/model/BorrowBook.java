package model;

public class BorrowBook {

    int UserId, BookId;
    

    public BorrowBook(int UserId, int BookId) {
        this.UserId = UserId;
        this.BookId = BookId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int BookId) {
        this.BookId = BookId;
    }

}
