package com.developersboard.todoreact.backend.bootstrap;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.developersboard.todoreact.backend.service.TodoService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

  private final TodoService todoService;

  @Autowired
  public DatabaseSeeder(TodoService todoService) {
    this.todoService = todoService;
  }

  /**
   * Callback used to run the bean.
   *
   * @param args incoming main method arguments
   * @throws Exception on error
   */
  @Override
  public void run(String... args) throws Exception {
    Todo todo = new Todo("Buy Milk", LocalDate.now().plusDays(2));
    Todo todo2 = new Todo("Clean House", LocalDate.now().plusDays(2));
    Todo todo3 = new Todo("Do Research", LocalDate.now().minusDays(2));
    todo2.setCompleted(true);

    todoService.saveOrUpdate(todo);
    todoService.saveOrUpdate(todo2);
    todoService.saveOrUpdate(todo3);
  }
}
