package com.example.dao.custom;

import com.example.dao.CrudDAO;
import com.example.entity.Books;

import java.util.List;

public interface BooksDAO extends CrudDAO<Books> {
    boolean borrowBook(String id);
    public boolean returnBook(String id);
    Books getBookByTitle(String bookName);
    public List<Books> getBookByGenre(String genre);
    public List<Books> getBookByAvailability();
    public String getLastBookId();
}
