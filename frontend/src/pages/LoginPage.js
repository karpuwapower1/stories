import React from "react";
import { Form, Button, Container, Col, Row, Alert } from "react-bootstrap";
import { Redirect, useHistory } from "react-router-dom";
import InvalidMessage from "../components/general/InvalidMessage.js";
import axios from "axios";

export default class LoginPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    this.submit = this.submit.bind(this);
    this.setParameter = this.setParameter.bind(this);
  }

  initialState = { password: "", email: "", success: false, message: "" };

  submit = (event) => {
    axios({
      method: "post",
      url: "/login",
      params: {
        username: this.state.email,
        password: this.state.password,
      },
      config: { headers: { "Content-Type": "application/json" } },
    })
      .then((response) => {
        this.setState({ success: true });
      })
      .catch((error) => {
        this.setState({ message: "Invalid email or password" });
      });
    event.preventDefault();
  };

  setParameter = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  render() {
    return this.state.success ? (
      <Redirect to="/main" />
    ) : (
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
                  <Button to="/register" variant="link" type="reset">
                    Register
                  </Button>
                </Form.Group>
              </Form.Row>

              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Form>
          </Col>
        </Row>
      </Container>
    );
  }
}