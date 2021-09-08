package com.demo.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.Book;

@Repository
@Transactional(readOnly = true)
public interface AdminRepo extends JpaRepository<Book, Long>{
	
}
