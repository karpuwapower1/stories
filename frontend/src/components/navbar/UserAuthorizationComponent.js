import React from "react";
import {Nav } from "react-bootstrap";


class NavbarComponent extends React.Component {
  render() {
    if (this.props.user.roles) {
      return <Nav.Link href="/logout">Logout</Nav.Link>;
    } else {
      return (
        <div>
          <Nav.Link href="/login">Login</Nav.Link>
          <Nav.Link href="/register">Registration</Nav.Link>
        </div>
      );
    }
  }
}

export default NavbarComponent;