import React from "react";
import { Button, Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import axios from "axios";
import Constants from "../../constants.js";

export default class UserAuthorizationComponent extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      success: false,
      links: this.props.links,
      user: this.props.user
    }
  }

  styles = {
    paddingTop: "0px",
    paddingBottom: "0px",
    paddingLeft: "8px",
    paddingRight: "0px",
  };

  logout = (event) => {
    event.preventDefault();
    axios.post(this.state.links.logout.href)
      .then((response) => this.clearUser())
      .catch((error) =>  this.setHistory(error))
  }

  clearUser = () => {
    localStorage.clear();
    window.location.href = Constants.MAIN_PAGE_ROUTE;
  };

  setHistory = (error) => {
    this.props.history.push({
      path: Constants.ERROR_PAGE_ROUTE,
      state: error.response.status,
    });
  };

  render() {
    if (this.state.user != null) {
      return (
        <Button variant="link" onClick={this.logout}>
          Logout
        </Button>
      );
    }
    return (
      <Nav.Link>
        <Link
          to={{ pathname: "/auth/login", 
          state: { links: this.props.links} }}
        >
          Login
        </Link>
      </Nav.Link>
    );
  }
}
