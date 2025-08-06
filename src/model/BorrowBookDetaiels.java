package model;

import javafx.scene.control.Button;

/**
 *
 * @author ibrah
 */
public class BorrowBookDetaiels {

    private int userId, bookID;
    private String userName, userImage, bookTitle, bookImage, status, deleiverDate;

    public BorrowBookDetaiels(int userId, int bookID, String userName, String userImage, String bookTitle, String bookImage, String status,String deleiverDate) {
        this.userId = userId;
        this.bookID = bookID;
        this.userName = userName;
        this.userImage = userImage;
        this.bookTitle = bookTitle;
        this.bookImage = bookImage;
        this.status = status;
        this.deleiverDate = deleiverDate;

    }

    public String getDeleiverDate() {
        return deleiverDate;
    }

    public void setDeleiverDate(String deleiverDate) {
        this.deleiverDate = deleiverDate;
    }

    public void approveAction() {
        this.status = "Approved";
    }

    public void rejectAction() {
        this.status = "Rejected";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
