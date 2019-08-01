import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { Link } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import AxiosUtil from "../../utils/AxiosUtil";

class AddTodoItem extends Component {
  state = {
    name: "",
    dueDate: new Date(),
    redirectToDashboard: false
  };

  /**
   * Handle input change event on adding new todo item.
   */
  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  /**
   * Handles changes made specifically to date picker.
   */
  handleChange = date => {
    this.setState({ dueDate: date });
  };

  /**
   * Handles form submission to create a new todo.
   */
  onSubmit = event => {
    event.preventDefault();

    const todo = {
      name: this.state.name,
      dueDate: this.state.dueDate
    };
    AxiosUtil.post("/", todo)
      .then(res => {
        if (res) this.setState({ redirectToDashboard: true });
      })
      .catch(err => console.log("error getting todos: " + err));
  };

  render() {
    if (this.state.redirectToDashboard) {
      return <Redirect to="/" />;
    }

    return (
      <div className="row box box-aqua">
        <h1 align="center">Add Todo Item</h1>
        <div className="col-md-6 col-md-offset-3">
          <form onSubmit={this.onSubmit}>
            <div className="form-group">
              <label htmlFor="name">Todo Item Name</label>
              <input
                type="text"
                className="form-control"
                value={this.state.name}
                id="name"
                name="name"
                onChange={this.onChange}
                placeholder="Todo Item Name"
              />
            </div>
            <div className="form-group">
              <label htmlFor="dueDate">
                <span style={{ marginRight: "10px" }}>Due Date </span>
              </label>
              <DatePicker
                className="form-control"
                selected={this.state.dueDate}
                onChange={this.handleChange}
                minDate={this.state.dueDate}
                value={this.state.dueDate}
                name="dueDate"
                id="dueDate"
                placeholderText="Select Due Date"
              />
            </div>
            <button type="submit" className="btn btn-success">
              <i className="fa fa-plus" /> Add Todo Item
            </button>
            <Link
              style={{ marginLeft: "20px" }}
              to="/"
              className="btn btn-danger"
            >
              Cancel
            </Link>
          </form>
        </div>
      </div>
    );
  }
}

export default AddTodoItem;
