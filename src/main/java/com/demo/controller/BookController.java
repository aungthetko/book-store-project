package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import com.demo.cart.Cart;
import com.demo.cart.CartService;
import com.demo.entity.Book;
import com.demo.order.Order;
import com.demo.order.OrderService;
import com.demo.service.BookService;

@Controller
public class BookController {
	
	private final BookService bookService;
	private final CartService cartService;
	private final OrderService orderService;

	public BookController(BookService bookService, CartService cartService, OrderService orderService) {
		super();
		this.bookService = bookService;
		this.cartService = cartService;
		this.orderService = orderService;
	}
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("listBooks", bookService.getAllBooks());
		return "index";
	}
	
	@GetMapping("/showDetails/{id}")
	public ModelAndView showBookDetails(@PathVariable(name = "id") int id) {
		ModelAndView modelAndView = new ModelAndView("book_details");
		Book book = bookService.getBookById(id);
		modelAndView.addObject("book", book);
		return modelAndView;
	}
	
	@RequestMapping("/addToCart/{id}")
	public String addToCart(@PathVariable("id") Long id, @ModelAttribute Cart cart, Model model) {
		cartService.addToCart(id, cart);
		model.addAttribute("totalPrice", cartService.totalPrice());
		model.addAttribute("listCart", cartService.findAllCart());
		return "cart";
	}
	
	@RequestMapping("/clear")
	public String clearAll() {
		cartService.clearAll();
		return "cart";
	}

	@RequestMapping("checkout")
	public String showCheckOut(Model model) {
		Order order = new Order();
	    model.addAttribute("order", order);
	    return "check-out";
	}
	
	@PostMapping(value = "/saveorder")
	public String saveOrder(@ModelAttribute("order") Order order, Model model) {
	    orderService.save(order);
	    model.addAttribute("listOrder", orderService.findAllOrder());
	    cartService.clearAll();
	    if (orderService.findAllOrder() != null) {
			orderService.removeAll();
		}
	    return "confirm-order";
	}
}
