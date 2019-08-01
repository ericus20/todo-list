import React, { Component } from "react";
import { Link } from "react-router-dom";

import TodoList from "./Todo/TodoList";
import Pagination from "./Todo/Pagination";

class Dashboard extends Component {
  render() {
    return (
      <div className="row box box-green">
        <div className="col-md-6 col-md-offset-3">
          <div>
            <div
              className="box-header ui-sortable-handle"
              style={{ cursor: "move" }}
            >
              <i className="ion ion-clipboard" />
              <h3 className="box-title">To Do List</h3>
              <Pagination />
            </div>
            <TodoList />
            <div className="box-footer clearfix no-border">
              <Link
                to="/addTodoItem"
                type="button"
                className="btn btn-default pull-right"
              >
                <i className="fa fa-plus" /> Add item
              </Link>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Dashboard;
