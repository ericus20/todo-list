import React, { Component } from "react";
import AxiosUtil from "../../utils/AxiosUtil";

class TodoListItem extends Component {
  state = {
    id: this.props.id,
    name: this.props.name,
    dueDate: this.props.dueDate,
    completed: this.props.completed,

    editMode: false
  };

  onInputChange = event => {
    this.setState({ [event.target.name]: event.target.checked }, () => {
      this.processFetchUpdate();
    });
  };

  onInputEditChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onKeyDown = event => {
    if (event.key === "Enter") {
      this.processFetchUpdate();
    }
  };

  processFetchUpdate = () => {
    const todo = {
      id: this.state.id,
      name: this.state.name,
      dueDate: this.state.dueDate,
      completed: this.state.completed
    };

    AxiosUtil.put("/", todo)
      .then(res => {
        this.setState({ editMode: false });
      })
      .catch(err => console.log("error getting todos: " + err));
  };

  onClickEdit = event => {
    this.setState({ editMode: true });
  };

  onClickDelete = event => {
    AxiosUtil.delete("/" + this.state.id)
      .then(res => {
        if (res !== undefined) {
          this.props.refresh(this.state.id);
        }
      })
      .catch(err => console.log("error getting todos: " + err));
  };

  render() {
    let checkBoxScope = () => {
      return (
        <React.Fragment>
          <span className="handle ui-sortable-handle">
            <i className="fa fa-ellipsis-v" />
            <i className="fa fa-ellipsis-v" />
          </span>
          <input
            type="checkbox"
            value={this.state.completed}
            name="completed"
            checked={this.state.completed}
            onChange={this.onInputChange}
          />
        </React.Fragment>
      );
    };

    if (this.state.editMode) {
      return (
        <li>
          {checkBoxScope()}
          <input
            type="text"
            hidden={this.state.completed}
            className="text"
            value={this.state.name}
            onChange={this.onInputEditChange}
            onKeyDown={this.onKeyDown}
            name="name"
          />
        </li>
      );
    }

    return (
      <li>
        {checkBoxScope()}
        <span hidden={this.state.completed} className="text">
          {this.state.name}
        </span>
        <span hidden={!this.state.completed} className="text">
          <del>{this.state.name}</del>
        </span>
        <small className={this.props.labelClass}>
          <i className="fa fa-clock-o" /> {this.props.dueDate}
        </small>
        <div className="tools">
          <i
            hidden={this.state.completed}
            className="fa fa-edit"
            onClick={this.onClickEdit}
          />
          <i className="fa fa-trash-o" onClick={this.onClickDelete} />
        </div>
      </li>
    );
  }
}

export default TodoListItem;
