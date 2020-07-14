import React from "react";
import {Button, Nav} from "react-bootstrap";
import {Link, Redirect} from "react-router-dom";
import axios from "axios"

export default class UserAuthorizationComponent extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      success : false,
      links : [],
    }
    this.logout = this.logout.bind(this);
  }

  styles = {
    paddingTop: "0px",
    paddingBottom: "0px",
    paddingLeft: "8px",
    paddingRight: "0px"
  };

  componentDidMount = () => {
    this.setState({
      success : false,
      links : this.props.links ? this.props.links : [],
    })
  }

  logout(event) {
    event.preventDefault();
    axios({
      method: "post",
      url: this.state.links.logout.href,
      config: { headers: { "Content-Type": "application/json"}}
    })
      .then((response) => {
        localStorage.setItem("authorization", response.data.jwttoken);
        window.location.href = "/main";
      })
      .catch((error) => {
        this.setState({ message: "Invalid email or password" });
      });
  }

  render() {
    const links = this.state.links ? this.state.links : [];
    if (links.login) {
      return (
       <Nav.Link>
       <Link to={{pathname: "/auth/login", state:{links: this.props.links}}}>Login</Link>
       </Nav.Link>
      );
      }
      return (
        <Button variant="link" onClick={this.logout}>Logout</Button>
      );
  }
}