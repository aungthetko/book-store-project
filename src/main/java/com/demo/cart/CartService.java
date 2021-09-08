package com.demo.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.entity.Book;
import com.demo.repository.BookRepository;

@Service
public class CartService {
	
	private final CartRepo cartRepo;
	private final BookRepository bookRepository;

	public CartService(CartRepo cartRepo, BookRepository bookRepository) {
		super();
		this.cartRepo = cartRepo;
		this.bookRepository = bookRepository;
	}
	
	public void addToCart(Long id, Cart cartBook) {
		Book book = bookRepository.findById(id).get();
		cartBook.setBook(book);
		cartBook.setBookName(book.getTitle());
		cartBook.setPrice(book.getPrice());
		cartRepo.save(cartBook);
	}
	
	public List<Cart> findAllCart() {
		return cartRepo.findAll();
	}
	
	public double totalPrice() {
		return cartRepo.findAll().stream()
				.map(Cart::getPrice)
				.mapToDouble(d -> d)
				.sum();
	}
	
	public void clearAll() {
		cartRepo.deleteAll();
	}
}
