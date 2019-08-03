import React from "react";
import ReactDOM from "react-dom";
import { expect } from "chai";
import { shallow } from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import TodoListItem from "./TodoListItem";

Enzyme.configure({ adapter: new Adapter() });

describe("Testing <TodoListItem /> component", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(<TodoListItem />, div);
    ReactDOM.unmountComponentAtNode(div);
  });

  it("simulates onClickEdit event should set editMode to true", () => {
    const wrapper = shallow(<TodoListItem />);
    wrapper.instance().onClickEdit();
    expect(wrapper.instance().state.editMode).to.equal(true);
  });

  it("simulates onInputEditChange event should set value of the state", () => {
    const wrapper = shallow(<TodoListItem />);
    let input = wrapper.find("input");
    wrapper.instance().onClickEdit();
    wrapper
      .instance()
      .onInputEditChange({ target: { name: "name", value: "testName" } });
    expect(wrapper.instance().state.editMode).to.equal(true);
    expect(input).to.have.lengthOf(1);
    expect(wrapper.instance().state.name).to.equal("testName");
  });
});
