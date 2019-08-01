package com.developersboard.todoreact.backend.bootstrap;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.developersboard.todoreact.backend.service.TodoService;

import java.time.LocalDate;
import java.util.Arrays;

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
   */
  @Override
  public void run(String... args) {

    Todo[] todoItems = {
            new Todo("Buy Milk", LocalDate.now().plusDays(2)),
            new Todo("Clean House", LocalDate.now().plusDays(2), true),
            new Todo("Do Research", LocalDate.now().minusDays(2))
    };
    Iterable<Todo> todos = Arrays.asList(todoItems);

    Iterable<Todo> todoIterable = todoService.saveOrUpdateAll(todos);
    // fail if preload does not succeed.
    assert todoIterable != null;
  }
}
