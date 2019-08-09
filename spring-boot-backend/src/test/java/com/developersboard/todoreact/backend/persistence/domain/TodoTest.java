package com.developersboard.todoreact.backend.persistence.domain;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class TodoTest {

  @Test
  void toStringForTodo(TestInfo testInfo) {
    LocalDate date = LocalDate.now().plusDays(2);
    String expected =
          "Todo(id=null, name=toStringForTodo(TestInfo), dueDate=" + date + ", completed=false)";
    Todo todo = new Todo(testInfo.getDisplayName(), date);
    Assertions.assertEquals(todo.toString(), expected);
  }
}