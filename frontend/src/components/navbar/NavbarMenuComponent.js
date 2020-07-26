import React from "react";
import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import Constants from "../../constants.js";

export default class NavbarMenuComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    if (this.props.user != null) {
      return (
        <Nav>
          <Nav.Link>
            <Link
              to={{
                pathname: `/books/users/${this.props.user.id}`,
                state: { link: this.props.links.home.href },
              }}
              style={{ color: "black" }}
            >
              Home
            </Link>
          </Nav.Link>
          <Nav.Link>
            <Link
              to={{
                pathname: "/books/add",
                state: { link: this.props.links.add_book.href },
              }}
              style={{ color: "black" }}
            >
              Add book
            </Link>
          </Nav.Link>
          {this.props.user.roles.find(
            (role) => role.name === Constants.ADMIN_ROLE
          ) ? (
            <Nav.Link>
              <Link
                to={{
                  pathname: "/users",
                  state: { link: this.props.links.all_users.href },
                }}
                style={{ color: "black" }}
              >
                All users
              </Link>
            </Nav.Link>
          ) : (
            ""
          )}
          <Nav.Link>
            <Link
              to={{
                pathname: `/users/${this.props.user.id}/update`,
                state: { link: this.props.user._links.self.href },
              }}
              style={{ color: "black" }}
            >
              Settings
            </Link>
          </Nav.Link>
        </Nav>
      );
    }
    return " ";
  }
}
