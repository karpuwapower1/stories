import React from "react";
import { Button, Nav } from "react-bootstrap";

export default class NavbarMenuComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    if (this.props.user.id) {
      return <Nav.Link href="/add/book">Add book</Nav.Link>;
    }
    return " ";
  }
}
