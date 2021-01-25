
//Emre Ertürk

package com.example.bookself_main_deneme.model;

public class Book{

    private String bookName, bookGenre, bookAuthor, uid, bookImg;

    public Book() {
    }

    public Book(String bookName, String bookGenre, String bookAuthor, String uid, String bookImg) {
        this.bookName = bookName;
        this.bookGenre = bookGenre;
        this.bookAuthor = bookAuthor;
        this.uid = uid;
        this.bookImg = bookImg;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }
}
