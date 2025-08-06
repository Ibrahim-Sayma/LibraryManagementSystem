package model;

public class Book {

    private String title, author, category, isbn, publishDate, language, publisher, BookImagePath;
    private int pageCount, copyCount, id;

    public Book(String title, String author, String category, String isbn, String publishDate, String language, String publisher, String BookImagePath, int pageCount, int copyCount) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.language = language;
        this.publisher = publisher;
        this.BookImagePath = BookImagePath;
        this.pageCount = pageCount;
        this.copyCount = copyCount;
    }

    public Book( int id,String title, String author, String category, String isbn, String publishDate, String language, String publisher, String BookImagePath, int pageCount, int copyCount) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.language = language;
        this.publisher = publisher;
        this.BookImagePath = BookImagePath;
        this.pageCount = pageCount;
        this.copyCount = copyCount;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "book{" + "title=" + title + ", author=" + author + ", category=" + category + ", isbn=" + isbn + ", publishDate=" + publishDate + ", language=" + language + ", publisher=" + publisher + ", BookImagePath=" + BookImagePath + ", pageCount=" + pageCount + ", copyCount=" + copyCount + '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBookImagePath() {
        return BookImagePath;
    }

    public void setBookImagePath(String BookImagePath) {
        this.BookImagePath = BookImagePath;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCopyCount() {
        return copyCount;
    }

    public void setCopyCount(int copyCount) {
        this.copyCount = copyCount;
    }

}
