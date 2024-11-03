package com.example.bo.custom.impl;

import com.example.bo.custom.BooksBO;
import com.example.dao.DAOFactory;
import com.example.dao.custom.BooksDAO;
import com.example.dto.BooksDTO;
import com.example.entity.Books;

import java.util.ArrayList;
import java.util.List;

public class BooksBoImpl implements BooksBO {

    BooksDAO bookDAO= (BooksDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKS);

    @Override
    public boolean addBook(BooksDTO dto) {
        return bookDAO.add(new Books(dto.getId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),
                dto.isAvailability()));
    }

    @Override
    public List<BooksDTO> getAllBooks() {
        List<Books> all = bookDAO.getAll();

        List<BooksDTO> allBooks = new ArrayList<>();

        for (Books book : all) {
            allBooks.add(new BooksDTO(book.getId(),book.getTitle(),book.getAuthor(),book.getGenre(),
                    book.isAvailability()));
        }
        return allBooks;
    }

    @Override
    public boolean updateBook(BooksDTO dto) {
        return bookDAO.update(new Books(dto.getId(),dto.getTitle(),dto.getAuthor(),dto.getGenre(),
                dto.isAvailability()));
    }

    @Override
    public boolean isExistBook(String id) {
        return bookDAO.isExists(id);
    }

    @Override
    public BooksDTO searchBook(String id) {
        Books search = bookDAO.search(id);
        BooksDTO booksDTO = new BooksDTO(search.getId(),search.getTitle(),search.getAuthor(),search.getGenre(),search.isAvailability());
        return booksDTO;
    }

    @Override
    public boolean deleteBook(String id) {
        return bookDAO.delete(id);
    }

    @Override
    public boolean borrowBook(String id) {
        return bookDAO.borrowBook(id);
    }

    @Override
    public boolean returnBook(String id) {
        return bookDAO.returnBook(id);
    }

    @Override
    public BooksDTO getBookByTitle(String bookName) {
        Books search = bookDAO.getBookByTitle(bookName);
        BooksDTO booksDTO = new BooksDTO(search.getId(),search.getTitle(),search.getAuthor(),search.getGenre(),search.isAvailability());
        return booksDTO;
    }

    @Override
    public List<Books> getBookByGenre(String genre) {
        return bookDAO.getBookByGenre(genre);
    }

    @Override
    public List<Books> getBookByAvailability() {
        return bookDAO.getBookByAvailability();
    }

    @Override
    public String getLastBookId() {
        return bookDAO.getLastBookId();
    }


}
