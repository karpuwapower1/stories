import { Navbar, Nav, Form, FormControl, Button } from "react-bootstrap";
import React from "react";
import UserAuthorizationComponent from "./UserAuthorizationComponent.js";

class NavbarComponent extends React.Component {
  render() {
    const user = this.props.user;
    return (
      <Navbar bg="light" variant="light">
        <Navbar.Brand href="/">Navbar</Navbar.Brand>
        <Nav className="mr-auto">
          <Nav.Link href="/">Home</Nav.Link>
          <Nav.Link href="#features">Features</Nav.Link>
          <Nav.Link href="#pricing">Pricing</Nav.Link>
          <UserAuthorizationComponent user={this.props.user} />
        </Nav>
        <Form inline>
          <FormControl type="text" placeholder="Search" className="mr-sm-2" />
          <Button variant="outline-primary">Search</Button>
        </Form>
      </Navbar>
    );
  }
}

export default NavbarComponent;
