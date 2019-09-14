package com.developersboard.backend.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.developersboard.backend.backend.persistence.domain.Todo;
import com.developersboard.backend.exception.TodoResourceUnavailableException;

@ActiveProfiles(value = {"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TodoServiceTest {

	@Autowired
	private TodoService todoService;

	@Test
	void saveOrUpdate(TestInfo testInfo) {
		createAndVerifyTodoItem(testInfo);
	}

	@Test
	void updateTodo(TestInfo testInfo) {
		Todo todo = createAndVerifyTodoItem(testInfo);
		todo.setName(todo.getName().concat("Updated"));
		todo.setCompleted(true);
		Todo updatedTodo = todoService.saveOrUpdate(todo);
		Assertions.assertAll("Update should not create new todo", () -> {
			Assertions.assertNotNull(updatedTodo);
			Assertions.assertEquals(updatedTodo.getName(), testInfo.getDisplayName().concat("Updated"));
			Assertions.assertEquals(updatedTodo.getId(), todo.getId());
			Assertions.assertNotNull(updatedTodo.getCreatedAt());
			Assertions.assertNotNull(updatedTodo.getCreatedBy());
			Assertions.assertNotNull(updatedTodo.getUpdatedAt());
			Assertions.assertNotNull(updatedTodo.getUpdatedBy());
		});

		updatedTodo.setName("Updated2");
		updatedTodo.setCompleted(false);
		Todo updatedTodo2 = todoService.saveOrUpdate(updatedTodo);
		Assertions.assertAll("Update should not create new todo", () -> {
			Assertions.assertNotNull(updatedTodo2);
			Assertions.assertEquals(updatedTodo2.getName(), "Updated2");
			Assertions.assertEquals(updatedTodo2.getId(), updatedTodo.getId());
			Assertions.assertNotNull(updatedTodo2.getCreatedAt());
			Assertions.assertNotNull(updatedTodo2.getCreatedBy());
			Assertions.assertNotNull(updatedTodo2.getUpdatedAt());
			Assertions.assertNotNull(updatedTodo2.getUpdatedBy());
		});
		updatedTodo2.setName("Updated3");
		updatedTodo2.setCompleted(true);
		Todo updatedTodo3 = todoService.saveOrUpdate(updatedTodo2);
		Assertions.assertAll("Update should not create new todo", () -> {
			Assertions.assertNotNull(updatedTodo3);
			Assertions.assertEquals(updatedTodo3.getName(), "Updated3");
			Assertions.assertEquals(updatedTodo3.getId(), updatedTodo2.getId());
			Assertions.assertNotNull(updatedTodo3.getCreatedAt());
			Assertions.assertNotNull(updatedTodo3.getCreatedBy());
			Assertions.assertNotNull(updatedTodo3.getUpdatedAt());
			Assertions.assertNotNull(updatedTodo3.getUpdatedBy());
		});
	}

	@Test
	void saveOrUpdateAll(TestInfo testInfo) {
		Todo[] todoItems = {
				new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2)),
				new Todo(testInfo.getDisplayName().concat("1"), LocalDate.now().plusDays(2), true),
		};
		Iterable<Todo> todos = Arrays.asList(todoItems);
		Iterable<Todo> todoIterable = todoService.saveOrUpdateAll(todos);
		ArrayList<Todo> todoList = new ArrayList<>();
		todoIterable.forEach(todoList::add);

		for (Todo todoItem : todoItems) {
			Assertions.assertTrue(todoList.contains(todoItem));
		}
	}

	@Test
	void getAllTodoItems(TestInfo testInfo) {
		Todo todo = createAndVerifyTodoItem(testInfo);
		Iterable<Todo> allTodoItems = todoService.getAllTodoItems();
		ArrayList<Todo> list = new ArrayList<>();
		allTodoItems.forEach(list::add);

		Assertions.assertTrue(list.contains(todo));
	}

	@Test
	void getAllTodoItemsOnEmpty() {
		todoService.deleteAllTodos();
		Assertions.assertThrows(TodoResourceUnavailableException.class, () ->
				todoService.getAllTodoItems(), "Calling get all on empty should throw exception");
	}

	@Test
	void getTodoById(TestInfo testInfo) {
		Todo todo = createAndVerifyTodoItem(testInfo);
		Todo todoById = todoService.getTodoById(todo.getId());
		Assertions.assertAll("Returned object must match as expected", () -> {
			Assertions.assertNotNull(todoById);
			Assertions.assertEquals(todoById, todo, "Equals does not include id and should be true");
		});
	}

	@Test
	void deleteTodoById(TestInfo testInfo) {
		Todo todo = createAndVerifyTodoItem(testInfo);
		Assertions.assertDoesNotThrow(() -> todoService.deleteTodoById(todo.getId()));
	}

	private Todo createAndVerifyTodoItem(TestInfo testInfo) {
		Todo todo = new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2));
		System.out.println(todo.toString());
		Todo savedTodo = todoService.saveOrUpdate(todo);
		Assertions.assertAll("Todo creation should succeed and return valid object", () -> {
			Assertions.assertNotNull(savedTodo);
			Assertions.assertNotNull(savedTodo.getId());
			Assertions.assertNotNull(savedTodo.getCreatedAt());
			Assertions.assertNotNull(savedTodo.getCreatedBy());
			Assertions.assertNotNull(savedTodo.getUpdatedAt());
			Assertions.assertNotNull(savedTodo.getUpdatedBy());
		});
		return todo;
	}
}