import React from "react";
import {
  Container,
  Card,
  Row,
  Col,
  Button,
  Table,
  Form,
} from "react-bootstrap";
import LoadingComponent from "../components/general/LoadingComponent.js";
import PaginationComponent from "../components/books_table/PaginationComponent.js";
import "./BookTablePage.css";
import axios from "axios";

export default class AllUsersPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: {},
      firstName: {},
      lastName: {},
      password: "",
      roles: [],
      enabled: "",
      isLoaded: false
    }
  }

  componentDidMount = () => {
    axios
      .get(this.props.location.state.link)
      .then((response) => response.data)
      .then((data) => {
        this.setData(data);
      });
  };

  setData = (data) => {
    this.setState({
      id: data.id,
      firstName: data.firstName,
      lastName: data.lastName,
      roles: data.roles,
      enabled: data.enabled,
      isLoaded: true,
    });
  };

  deleteUser = (event) => {
       event.preventDefault();
    try {
      axios.delete(this.props.location.state.link).then((response) => {
        localStorage.clear();
        window.location.href = "/main";
      });
    } catch (error) {
      this.setState({ show: false });
    }
  };

  updateUser = (event) => {
    event.preventDefault();
    try {
      axios
        .put(this.props.location.state.link, this.prepareData());
    } catch (error) {
      this.setState({ show: false });
    }
  };

  prepareData = () => {
    let data = new FormData();
    let updateUser = {
      id: this.state.id,
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      password: this.state.password,
      enabled: this.state.enabled
    };
    let roles = [];
    this.state.roles.map(role => roles.push({id: role.id, name:role.name}));
    data.append("user", JSON.stringify(updateUser));
    data.append("roles", JSON.stringify(roles));
    return data;
  };

  setParameter = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  render() {
    const { password, firstName, lastName } = this.state;
    if (!this.state.isLoaded) {
        return <LoadingComponent />
    }
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md md="5" style={{ textAlign: "center" }}>
            <h1>Info</h1>
            <Form id="loginForm">
              <Form.Group controlId="formBasicPassword">
                <Form.Control
                  type="text"
                  placeholder="First name"
                  name="firstName"
                  value={firstName}
                  onChange={this.setParameter}
                />
              </Form.Group>

              <Form.Group controlId="formBasicPassword">
                <Form.Control
                  type="text"
                  placeholder="Last name"
                  name="lastName"
                  value={lastName}
                  onChange={this.setParameter}
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
                />
              </Form.Group>

              <Button variant="primary" type="submit" onClick={(e) => this.updateUser(e)}>
                Submit
              </Button>

              <div>
                <Button
                  variant="danger"
                  type="submit"
                  onClick={(e) =>this.deleteUser(e)}
                >
                  Delete
                </Button>
              </div>
            </Form>
          </Col>
        </Row>
      </Container>
    );
  }
}
