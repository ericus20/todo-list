import React, { Component } from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

class Pagination extends Component {
  render() {
    return (
      <div className="box-tools pull-right">
        <ul className="pagination pagination-sm inline">
          <li>
            <Link to="/">«</Link>
          </li>
          <li>
            <Link to="/">1</Link>
          </li>
          <li>
            <Link to="/">2</Link>
          </li>
          <li>
            <Link to="/">»</Link>
          </li>
        </ul>
      </div>
    );
  }
}

export default Pagination;
