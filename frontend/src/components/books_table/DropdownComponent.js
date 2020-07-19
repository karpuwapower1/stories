import React from "react";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import DropdownItemComponent from "./DropdownItemComponent.js";

export default class DropdownComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <DropdownButton
        variant="link"
        style={{ textAlign: "right", alignItem: "right" }}
      >
        <div style={{ fontSize: "12px" }}>
          <Dropdown.Item>
            <Link
              style={{ color: "black" }}
              to={{
                pathname: `/books/${this.props.book.id}`,
                state: { links: this.props.book._links.self.href },
              }}
            >
              Read
            </Link>
          </Dropdown.Item>
          <DropdownItemComponent
            onClick={this.props.onClickDelete}
            title="Delete"
          />
        </div>
      </DropdownButton>
    );
  }
}
