package com.demo.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.admin.repository.AdminRepo;
import com.demo.entity.Book;


@Service
public class AdminUserService {

	private final AdminRepo adminRepo;

	public AdminUserService(AdminRepo adminRepo) {
		super();
		this.adminRepo = adminRepo;
	}
	
	public List<Book> getAllBooks(){
		return adminRepo.findAll();
	}
	
	public Book addBook(Book book) {
		return adminRepo.save(book);
	}
	
	public void deleteById(long id) {
		adminRepo.deleteById(id);
	}
	
	public Book getBookById(long id) {
		return adminRepo.findById(id).get();
	}
}
