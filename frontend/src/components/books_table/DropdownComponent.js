import React from "react";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { Link } from "react-router-dom";

export default class DropdownComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let {book, link, onClickDelete} = this.props;
    console.log(link);
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
                pathname: `/books/${book.id}`,
                state: { links: link },
              }}
            >
              Read
            </Link>
          </Dropdown.Item>
          <Dropdown.Item>
            <Link style={{ color: "black" }} to={{ pathname: `/books/update/${book.id}`, 
          state: {links: link}}}>Update</Link>
          </Dropdown.Item>
          <Dropdown.Item onClick={onClickDelete}>Delete</Dropdown.Item>
        </div>
      </DropdownButton>
    );
  }
}