package com.developersboard.todoreact.backend.persistence.repository;

import com.developersboard.todoreact.backend.persistence.domain.Todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public interface TodoRepository extends CrudRepository<Todo, Long> {
}
