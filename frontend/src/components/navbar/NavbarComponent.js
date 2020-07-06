import { Navbar, Nav, Form, FormControl, Button } from "react-bootstrap";
import React from "react";
import UserAuthorizationComponent from "./UserAuthorizationComponent.js";

export default class NavbarComponent extends React.Component {
  render() {
    return (
      <Navbar collapseOnSelect expand="md" bg="light" variant="light">
        <Navbar.Brand href="/">Navbar</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <Nav.Link href="/">Home</Nav.Link>
            <Nav.Link href="#features">Features</Nav.Link>
            <Nav.Link href="#pricing">Pricing</Nav.Link>
          </Nav>
          <Nav className="ml-auto">
            <UserAuthorizationComponent user={this.props.user} />
          </Nav>
          <Form inline>
            <FormControl type="text" placeholder="Search" className="mr-sm-2" />
            <Button variant="outline-primary">Search</Button>
          </Form>
        </Navbar.Collapse>
      </Navbar>
    );
  }
}
