package com.org.todosbackend.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.todosbackend.entities.Todo;
import com.org.todosbackend.repositories.TodoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class TodosController {
	@Autowired
	private TodoRepository todosRepository ;
	
	@GetMapping("/todos")
	public ResponseEntity<Map<String, Object>> todos(@RequestParam(required = false) String filter, @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
		
		  try {
			  List<Todo> todos = new ArrayList<Todo>();
		      Pageable paging = PageRequest.of(page, size);
		      Page<Todo> pageTodos;
			  if (filter == null) {
			      pageTodos = todosRepository.findAll(paging);

			  } else {
				  boolean completed = filter.equals("completed")? true : false;
			      pageTodos = todosRepository.findByCompleted(completed,paging);  
			  }
		      todos = pageTodos.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("todos", todos);
		      response.put("currentPage", pageTodos.getNumber());
		      response.put("totalItems", pageTodos.getTotalElements());
		      response.put("totalPages", pageTodos.getTotalPages());

		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
	
	  @PostMapping("/todos")
	  public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
	    try {
	      Todo _todo= todosRepository.save(new Todo(todo.getId(),todo.getTitle(), todo.getDescription(), todo.isCompleted()));
	      return new ResponseEntity<>(_todo, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  @DeleteMapping("/todos")
	  public ResponseEntity<HttpStatus> deleteAllTodos() {
	    try {
	    	todosRepository.deleteAll();
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  @DeleteMapping("/todos/{id}")
	  public ResponseEntity<HttpStatus> deleteTodo(@PathVariable("id") long id) {
	    try {
	    	todosRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  @PutMapping("/todos-complete/{id}")
	  public ResponseEntity<Todo> markTodoCompleted(@PathVariable("id") long id) {
	    Optional<Todo> todoData = todosRepository.findById(id);
	    if (todoData.isPresent()) {
	      Todo _todo = todoData.get();
	      _todo.setCompleted(true);
	      return new ResponseEntity<>(todosRepository.save(_todo), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  
	  @PutMapping("/todos-uncomplete/{id}")
	  public ResponseEntity<Todo> markTodoUncompleted(@PathVariable("id") long id) {
	    Optional<Todo> todoData = todosRepository.findById(id);
	    if (todoData.isPresent()) {
	      Todo _todo = todoData.get();
	      _todo.setCompleted(false);
	      return new ResponseEntity<>(todosRepository.save(_todo), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

}
