import { Navbar, Nav, Form, FormControl, Button } from "react-bootstrap";
import React from "react";
import UserAuthorizationComponent from "./UserAuthorizationComponent.js";
import LoadingComponent from "../general/LoadingComponent.js";
import NavbarMenuComponent from "./NavbarMenuComponent.js";
import Constants from "../../constants.js";

export default class NavbarComponent extends React.Component {
  constructor(props) {
    super(props);
    const data = JSON.parse(
      localStorage.getItem(Constants.MAIN_DATA_STORAGE_NAME)
    );
    const tags = data.tags._embedded ? data.tags._embedded.tupleBackedMaps : [];
    const genres = data.genres._embedded
      ? data.genres._embedded.tupleBackedMaps
      : [];
    this.state = {
      user: data.user,
      tags: tags,
      genres: genres,
      links: data._links,
      isLoaded: true,
    };
  }

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    console.log(this.state.links);
    return (
      <Navbar collapseOnSelect expand="md" bg="light" variant="light">
        <Navbar.Brand href="/" style={{ fontSize: "18px" }}>
          Stories
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <NavbarMenuComponent
            user={this.state.user}
            links={this.state.links}
          />
          <Nav className="ml-auto">
            <Form inline>
              <FormControl
                type="text"
                placeholder="Search"
                className="mr-sm-2"
              />
              <Button variant="outline-primary">Search</Button>
            </Form>
            <UserAuthorizationComponent
              user={this.state.user}
              links={this.state.links}
            />
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  }
}
