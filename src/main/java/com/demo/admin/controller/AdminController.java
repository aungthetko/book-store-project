package com.demo.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo.admin.service.AdminUserService;
import com.demo.entity.Book;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminUserService adminUserService;
	
	public AdminController(AdminUserService adminUserService) {
		super();
		this.adminUserService = adminUserService;
	}

	@GetMapping
	public String adminHomePage(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("listBooks", adminUserService.getAllBooks());
		return "home";
	}
	
	@PostMapping(path = "/add")
	public ResponseEntity<Book> addNewBook(@RequestBody Book book){
		try {
			Book newBook = adminUserService.addBook(book);
			return new ResponseEntity<>(newBook, HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveBook")
	public String saveBook(@ModelAttribute("book") Book book) {
		adminUserService.addBook(book);
		return "redirect:/admin";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteBook(@PathVariable(name = "id") long id) {
		adminUserService.deleteById(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView showBookDetails(@PathVariable(name = "id") int id) {
		ModelAndView modelAndView = new ModelAndView("book_edit");
		Book book = adminUserService.getBookById(id);
		modelAndView.addObject("book", book);
		
		return modelAndView;
	}
}
