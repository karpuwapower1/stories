import React from "react";
import { Button, Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import axios from "axios";

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
      .then((response) => {
        localStorage.removeItem("authorization", response.data.jwttoken);
        localStorage.removeItem("main_data", response.data.jwttoken);
        window.location.href = "/main";
      })
      .catch((error) => {
        this.setState({ message: "Invalid email or password" });
      });
  }

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
