import { Navbar, Nav, Form, FormControl, Button } from "react-bootstrap";
import React from "react";
import UserAuthorizationComponent from "./UserAuthorizationComponent.js";
import axios from "axios";

export default class NavbarComponent extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      user: {}, 
      links: [],
      isLoaded: false
    }
  }
  componentDidMount = () => {axios({
    method:'get',
    url:"http://localhost:8080/user",
    config: { headers: {'Content-Type': 'application/json'}}
   })
   .then( response => (response.data))
   .then (data => {
    this.setState({
     user: data,
     links: data._links,
     isLoaded: true, 
    })
    })
  }

  render() {
  if (!this.state.isLoaded) {
    return ("");
  }
    return (
      <Navbar collapseOnSelect expand="md" bg="light" variant="light">
        <Navbar.Brand href="/" style={{fontSize : "18px"}}>Stories</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="mr-auto">
            <Nav.Link href="/">Home</Nav.Link>
            <Nav.Link href="/add/book">Add book</Nav.Link>
            <Nav.Link href="#pricing">Pricing</Nav.Link>
          </Nav>
          <Nav className="ml-auto">
          <Form inline>
            <FormControl type="text" placeholder="Search" className="mr-sm-2" />
            <Button variant="outline-primary">Search</Button>
          </Form>
            <UserAuthorizationComponent links={this.state.links} />
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    ); 
}
}