package com.developersboard.backend.backend.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.developersboard.backend.backend.persistence.domain.Todo;

@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public interface TodoRepository extends CrudRepository<Todo, Long> {
}
