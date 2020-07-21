import React from "react";
import { Col } from "react-bootstrap";
import { Link } from "react-router-dom";
import DropdownComponent from "./DropdownComponent.js";
import DropdownItemComponent from "./DropdownItemComponent.js";

export default class HeaderComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const book = this.props.book;
    return (
      <>
        <Col>
          <Link
            style={{ color: "black" }}
            to={{
              pathname: `/books/users/${book.user.id}`,
              state: { links: book.user._links.author.href },
            }}
            onClick={this.props.changeState}
          >
            {book.user.firstName}{ book.user.lastName}
          </Link>
          <br />
          <Link
            style={{ color: "black" }}
            to={{
              pathname: `/books/${book.id}`,
              state: { links: book._links.self.href },
            }}
          >
            {book.name}
          </Link>
        </Col>
        <Col sm={1}>
          <DropdownComponent
            book={book}
            onClickRead={this.props.onClickRead}
            onClickDelete={this.props.onClickDelete}
          />
        </Col>
      </>
    );
  }
}
