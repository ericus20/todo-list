import React from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import { BrowserRouter as Router, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import AddTodoItem from "./components/Todo/AddTodoItem";

function App() {
  return (
    <Router>
      <Route exact path="/" component={Dashboard} />
      <Route exact path="/addTodoItem" component={AddTodoItem} />
    </Router>
  );
}

export default App;
