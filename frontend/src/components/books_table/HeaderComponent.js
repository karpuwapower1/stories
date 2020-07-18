import React from "react";
import {
  Button,
  Table,
  Container,
  Card,
  DropdownButton,
  Dropdown,
  Row,
  Col,
} from "react-bootstrap";
import DropdownComponent from "./DropdownComponent.js";
import CardHeaderComponent from "./CardHeaderComponent.js";
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
          <CardHeaderComponent
            pathname={`/books/users/${book.user.id}`}
            links={book.user._links.author.href}
            changeState={this.changeState}
            linkName={book.user.firstName + book.user.lastName}
          />
          <br />
          <CardHeaderComponent
            pathname={`/books/${book.id}`}
            links={book._links.self.href}
            changeState={this.changeState}
            linkName={book.name}
          />
        </Col>
        <Col sm={1}>
          <DropdownComponent
            onClickRead={this.props.onClickRead}
            onClickDelete={this.props.onClickDelete}
          />
        </Col>
      </>
    );
  }
}
