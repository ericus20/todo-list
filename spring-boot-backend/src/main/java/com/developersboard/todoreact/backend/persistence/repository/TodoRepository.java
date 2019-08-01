package com.developersboard.todoreact.backend.persistence.repository;

import com.developersboard.todoreact.backend.persistence.domain.Todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
}
