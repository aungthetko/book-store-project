package com.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.demo.entity.Book;
import com.demo.repository.BookRepository;

@Service
@Transactional
public class BookService {

	private final BookRepository bookRepository;
	private Set<Book> bookCart = new HashSet<Book>();

	public BookService(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	public Set<Book> getBookCart() {
		return bookCart;
	}

	public void setBookCart(Set<Book> bookCart) {
		this.bookCart = bookCart;
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(long id) {
		return bookRepository.findById(id).get();
	}
	
	public void addToCart(Book book) {
		bookCart.add(book);
	}
}
