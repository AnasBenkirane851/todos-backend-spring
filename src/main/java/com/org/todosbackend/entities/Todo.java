package com.org.todosbackend.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name="TODOS")
@NoArgsConstructor @AllArgsConstructor @ToString
public class Todo {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="completed")
	private boolean completed;

	public Todo(String title, String description, boolean completed) {
		this.title = title;
		this.description = description;
		this.completed = completed;
	}
	
	
}
