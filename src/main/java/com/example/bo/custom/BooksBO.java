package com.example.bo.custom;

import com.example.bo.SuperBO;
import com.example.dto.BooksDTO;
import com.example.entity.Books;

import java.util.List;

public interface BooksBO extends SuperBO {
    boolean addBook(BooksDTO dto);
    List<BooksDTO> getAllBooks();
    boolean updateBook(BooksDTO dto);
    boolean isExistBook(String id);
    BooksDTO searchBook(String id);
    boolean deleteBook(String id);
    boolean borrowBook(String id);
    boolean returnBook(String id);
    BooksDTO getBookByTitle(String bookName);
    List<Books> getBookByGenre(String genre);
    public List<Books> getBookByAvailability();
    public String getLastBookId();
}
