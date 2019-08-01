import React, { Component } from "react";
import TodoListItem from "./TodoListItem";
import AxiosUtil from "../../utils/AxiosUtil";
// import axios from "axios";

class TodoList extends Component {
  state = {
    todos: []
  };

  componentDidMount(props) {
    AxiosUtil.get("/")
      .then(res => {
        this.setState({ todos: res.data });
      })
      .catch(err => console.log("error getting todos: " + err));
  }

  checkDueDatePassedLabel = date => {
    return new Date(date) < new Date(new Date().toDateString());
  };

  refreshListToRemove = id => {
    let todos = this.state.todos.filter(todo => todo.id !== id);
    this.setState({ todos: todos });
  };

  render() {
    let todoListItems = this.state.todos.map(todo => {
      let dueDateLabelClass = this.checkDueDatePassedLabel(todo.dueDate)
        ? "label label-danger"
        : "label label-success";

      return (
        <TodoListItem
          key={todo.id}
          id={todo.id}
          name={todo.name}
          dueDate={todo.dueDate}
          completed={todo.completed}
          labelClass={dueDateLabelClass}
          refresh={this.refreshListToRemove}
        />
      );
    });

    return (
      <div className="box-body">
        <ul className="todo-list ui-sortable">{todoListItems}</ul>
      </div>
    );
  }
}

export default TodoList;
