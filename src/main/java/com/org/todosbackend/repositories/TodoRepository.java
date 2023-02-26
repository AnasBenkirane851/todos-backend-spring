package com.org.todosbackend.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.org.todosbackend.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	
	Page<Todo> findAll(Pageable pageable);
	Page<Todo> findByCompleted(boolean completed, Pageable pageable);


}
