import React from "react";
import { Form, Button, Container, Col, Row } from "react-bootstrap";
import {Redirect} from "react-router-dom";
import InvalidMessage from "../components/general/InvalidMessage.js";
import axios from "axios";

export default class RegistrationPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        token: "",
        message: `Confirmation token was sent to your email`,
        redirect: "",
    }
  }

  submit = (event) => {
    event.preventDefault();
    axios({
      method: "POST",
      url: this.props.location.state.links.activation.href,
      params: {
        token: this.state.token,
      },
    })
      .then((response) => {
        this.setState({
        redirect: "/main"
        })
      })
      .catch((error) => {
        this.setState({ message: "Invalid token" });
      });
  };

  setParameter = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  render() {
    if (this.state.redirect) {
      return <Redirect to={{pathname: this.state.redirect, links: this.props.location.state.links}} />
    }
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md md="5" style={{ textAlign: "center" }}>
            <h1>Registration confirmation</h1>
            <InvalidMessage message={this.state.message} />
            <Form onSubmit={this.submit} id="confirm">
              <Form.Group controlId="confirmationToken">
                <Form.Control
                  type="text"
                  autoComplete= "off"
                  placeholder="Token"
                  name="token"
                  value={this.state.token}
                  onChange={this.setParameter}
                  required
                />
              </Form.Group>
              <Button variant="primary" type="submit" onClick={this.submit}>
                Submit
              </Button>
            </Form>
          </Col>
        </Row>
      </Container>
    );
  }
}