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
import { Link } from "react-router-dom";
import LoadingComponent from "../components/general/LoadingComponent.js";
import PaginationComponent from "../components/books_table/PaginationComponent.js";
import "./BookTablePage.css";
import axios from "axios";

export default class AllUsersPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users: [],
      page: {},
      paginationLinks: [],
    };
    this.loadData(this.props.location.state.link);
  }

  currentUserId = JSON.parse(localStorage.getItem("main_data")).user.id;

  loadData = (link) => {
    axios
      .get(link)
      .then((response) => response.data)
      .then((data) => {
        this.setData(data);
      });
  };

  loadPage = (event, link) => {
    axios
      .get(link, { params: { page: event.value } })
      .then((response) => response.data)
      .then((data) => {
        this.setData(data);
      });
  };

  setData = (data) => {
    if (data._embedded) {
      this.setState({
        users: data._embedded.users,
        paginationLinks: data._links,
        page: data.page,
      });
    }
    this.setState({ isLoaded: true });
  };

  deleteUser = (href, id) => {
    try {
      axios.delete(href).then(
        this.setState({
          users: this.state.users.filter((user) => user.id !== id),
        })
      );
      if (this.currentUserId === id) {
        localStorage.clear();
        window.location.href = "/main";
      }
    } catch (error) {
      this.setState({ show: false });
    }
  };

  updateUser = (href, user) => {
    try {
      axios.put(href, this.prepareData(user)).then(response => response);
    } catch (error) {
      this.setState({ show: false });
    }
  };

  prepareData = (user) => {
    let data = new FormData();
    let updateUser = {id : user.id, enabled: user.enabled}
   let roles = [];
   user.roles.map(role => roles.push({id: role.id, name:role.name}));
    data.append("user", JSON.stringify(updateUser));
    data.append("roles", JSON.stringify(roles));
    return data;
  }

  changeAuthority = (href, id) => {
    let users = this.state.users;
    users.map(user => {
      if (user.id == id) {
        user = this.updateUserRoles(user, href);
        this.updateView(user, users);
      }
    });
  };

  updateUserRoles = (user, href) => {
    let roles = user.roles;
    roles.map((role, index) => 
      role.name === "ROLE_ADMIN" ? user.roles.splice(index) :  user.roles.push({id: 1, name:"ROLE_ADMIN"})
    );
    this.updateUser(href, user);
    return user;
  }

  updateView = (user, users) => {
    this.currentUserId === user.id ?  this.redirect() : this.setState({users});
  }

  redirect = () => {
      localStorage.removeItem("main_data");
      window.location.href = "/main";
  }

  changeEnable = (href, id) => {
    let users = this.state.users;
    users.map(user => {
      if (user.id == id) {
        user = this.updateUserEnabled(user, href);
      }
    })
    this.setState({users});
  };

  updateUserEnabled = (user, href) => {
    user.enabled = !user.enabled;
    if (!user.enabled && this.currentUserId == user.id) {
      this.disableUser(user, href);
    }
    this.updateUser(href, user);
    return user;
  }

  disableUser = (user, href) => {
    this.logout();
    this.updateUser(href, user);
    localStorage.clear();
    window.location.href = "/main";
  }

  logout = () => {
    axios.post(JSON.parse(localStorage.getItem("main_data"))._links.logout)
      .then((response) => {
        
      })
      .catch((error) => {
       console.log(error);
      });
  }

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    return (
      <>
      <Container>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>User</th>
              <th>Authority</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {this.state.users.map((user) => {
              return (
                <tr key={user.id}>
                  <td>
                    <Link
                      to={{
                        pathname: `/books/users/${user.id}`,
                        state: { link: user._links.author.href },
                      }}
                    >
                      {user.firstName} {user.lastName}
                    </Link>
                  </td>
                  <td>
                    <Form id="custom-switch-authority" inline={true}>
                      User
                      <Form.Check
                        type="switch"
                        id={user.id}
                        label="Admin"
                        defaultChecked={
                          user.roles.find((role) => role.name === "ROLE_ADMIN")
                            ? true
                            : false
                        }
                        onChange={this.changeAuthority.bind(this,
                          user._links.self.href,
                          user.id)}
                      />
                    </Form>
                  </td>
                  <td>
                    <Form id="custom-switch-enable" inline={true}>
                      Lock
                      <Form.Check
                        type="switch"
                        id={user.id + user.firstName}
                        label="Unlock"
                        defaultChecked={
                          user.enabled
                            ? true
                            : false
                        }
                        onChange={this.changeEnable.bind(this,
                          user._links.self.href,
                          user.id)}
                      />
                    </Form>
                  </td>
                  <td>
                    <Button
                      variant="link"
                      style={{color: "red"}}
                      onClick={this.deleteUser.bind(
                        this,
                        user._links.self.href,
                        user.id
                      )}
                    >
                      Delete
                    </Button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </Table>
        </Container>

        <PaginationComponent
          links={this.state.paginationLinks}
          page={this.state.page}
          changeState={this.changeState}
          // pages={this.pages}
          loadPage={this.loadPage}
        />
      </>
    );
  }
}