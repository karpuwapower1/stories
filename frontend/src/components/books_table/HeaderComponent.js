import React from "react";
import { Col } from "react-bootstrap";
import { Link } from "react-router-dom";
import DropdownComponent from "./DropdownComponent.js";

export default class HeaderComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let {book, link} = this.props;
    console.log(book);
    console.log(link);
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
            {book.user.firstName} { book.user.lastName}
          </Link>
          <br />
          <Link
            style={{ color: "black" }}
            to={{
              pathname: `/books/${book.id}`,
              state: { links:link },
            }}
          >
            {book.name}
          </Link>
        </Col>
        <Col sm={1}>
          <DropdownComponent
            book={book}
            link={link}
            onClickDelete={this.props.onClickDelete}
          />
        </Col>
      </>
    );
  }
}
