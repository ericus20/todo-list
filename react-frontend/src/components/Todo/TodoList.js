import React, { Component } from "react";
import TodoListItem from "./TodoListItem";
import AxiosUtil from "../../utils/AxiosUtil";

class TodoList extends Component {
  state = {
    todos: []
  };

  /**
   * Load all the todos into the state of the
   * application from server side on mount.
   * @param {*} props
   */
  componentDidMount(props) {
    AxiosUtil.get("/")
      .then(res => {
        this.setState({ todos: res.data });
      })
      .catch(err => console.log("error getting todos: " + err));
  }

  /**
   * Check to assign the appropriate label based on the due date.
   */
  checkDueDatePassedLabel = date => {
    return new Date(date) < new Date(new Date().toDateString());
  };

  /**
   * After the removal of a todo from the server side,
   * todo object is internally removed from the state.
   */
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
          version={todo.version}
          completed={todo.completed}
          createdAt={todo.createdAt}
          createdBy={todo.createdBy}
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
