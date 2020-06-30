import { Navbar, Nav, Form, FormControl, Button } from "react-bootstrap";
import React from "react";

class NavbarComponent extends React.Component {
  render() {
      console.log(this.props.user.roles);
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