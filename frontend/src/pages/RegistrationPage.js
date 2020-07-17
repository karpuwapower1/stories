import React from "react";
import { Form, Button, Container, Col, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import InvalidMessage from "../components/general/InvalidMessage.js";
import axios from "axios";

export default class RegistrationPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    this.submit = this.submit.bind(this);
    this.setParameter = this.setParameter.bind(this);
  }

  initialState = {
    firstName: "",
    lastName: "",
    password: "",
    confirmPassword: "",
    email: "",
    success: false,
    message: "",
  };

  submit(event) {
    event.preventDefault();
    axios({
      method: "POST",
      url: this.props.location.state.links.register.href, 
      params: {
        username: this.state.email,
        password: this.state.password,
        confirmPassword: this.state.confirmPassword,
        firstName: this.state.firstName,
        lastName: this.state.lastName,
      }
    })
      .then((response) => {
        this.setState({
          message: `A confirmation link was send to ${this.state.email}. Follow the sent link to complite registration`,
          firstName: "",
          lastName: "",
          email: "",
          password: "",
          confirmPassword: "",
        });
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
    const {
      email,
      password,
      confirmPassword,
      firstName,
      lastName,
      message,
    } = this.state;
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md md="5" style={{ textAlign: "center" }}>
            <h1>Registration</h1>
            <Form onSubmit={this.submit} id="loginForm">
              <Form.Group controlId="formBasicEmail">
                <Form.Control
                  type="email"
                  placeholder="Email"
                  name="email"
                  value={email}
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
                  value={password}
                  onChange={this.setParameter}
                  required
                />
              </Form.Group>

              <Form.Group controlId="formBasicPassword">
                <Form.Control
                  type="password"
                  placeholder="Confirm password"
                  name="confirmPassword"
                  value={confirmPassword}
                  onChange={this.setParameter}
                  required
                />
              </Form.Group>

              <Form.Group controlId="formBasicPassword">
                <Form.Control
                  type="text"
                  placeholder="First name"
                  name="firstName"
                  value={firstName}
                  onChange={this.setParameter}
                  required
                />
              </Form.Group>

              <Form.Group controlId="formBasicPassword">
                <Form.Control
                  type="text"
                  placeholder="Last name"
                  name="lastName"
                  value={lastName}
                  onChange={this.setParameter}
                  required
                />
              </Form.Group>

              <InvalidMessage message={message} />

              <Button variant="primary" type="submit" onClick={this.submit}>
                Submit
              </Button>
              <div>
                <p>Already have an account?</p>
                <Link
                  to={{
                    pathname: "/auth/login",
                    state: {
                      links: this.props.location.state.links,
                    },
                  }}
                >
                  Login
                </Link>
              </div>
            </Form>
          </Col>
        </Row>
      </Container>
    );
  }
}
