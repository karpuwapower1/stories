import React from "react";
import { Form, Button, Container, Col, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import InvalidMessage from "../components/general/InvalidMessage.js";
import axios from "axios";

export default class LoginPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
  }

  initialState = { password: "", email: "", message: "" };

  submit = (event) => {
    event.preventDefault();
    axios({
      method: "POST",
      url: this.props.location.state.links.login.href, 
      params :{
      username: this.state.email,
      password: this.state.password,}
      })
      .then((response) => {
        localStorage.setItem("authorization", response.data.jwttoken);
        window.location.href = "/main";
      })
      .catch((error) => {
        this.setState({ message: "Invalid email or password" });
      });
  }

  setParameter = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  render() {
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md md="5" style={{ textAlign: "center" }}>
            <h1>Login</h1>
            <Form onSubmit={this.submit} id="loginForm">
              <Form.Group controlId="formBasicEmail">
                <Form.Control
                  type="email"
                  placeholder="Enter email"
                  name="email"
                  value={this.state.email}
                  onChange={this.setParameter}
                  required
                />
              </Form.Group>

              <Form.Group controlId="formBasicPassword">
                <Form.Control
                  autoComplete="off"
                  type="password"
                  placeholder="Password"
                  name="password"
                  value={this.state.password}
                  onChange={this.setParameter}
                  required
                />
              </Form.Group>

              <InvalidMessage message={this.state.message} />

              <Form.Row>
                <Form.Group
                  as={Col}
                  controlId="formBasicCheckbox"
                  style={{ textAlign: "left" }}
                >
                  <Form.Check type="checkbox" label="Remember me" />
                </Form.Group>

                <Form.Group
                  as={Col}
                  controlId="formBasicLink"
                  style={{ textAlign: "right" }}
                >
                  <Link
                    to={{
                      pathname: "/auth/registration",
                      state: {
                        links: this.props.location.state.links,
                      },
                    }}
                  >
                    Register
                  </Link>
                </Form.Group>
              </Form.Row>

              <Button variant="primary" type="submit" onClick={this.submit}>
                Login
              </Button>
            </Form>
          </Col>
        </Row>
      </Container>
    );
  }
}