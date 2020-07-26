import React from "react";
import { Container, Button, Table, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
import LoadingComponent from "../components/general/LoadingComponent.js";
import PaginationComponent from "../components/books_table/PaginationComponent.js";
import Constants from "../constants.js";
import axios from "axios";

export default class AllUsersPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    this.loadData(this.props.location.state.link);
  }

  pages = [];

  initialState = {
    users: [],
    page: {},
    paginationLinks: [],
  };

  currentUserId = JSON.parse(
    localStorage.getItem(Constants.MAIN_DATA_STORAGE_NAME)
  ).user.id;

  loadData = (link) => {
    axios
      .get(link)
      .then((response) => response.data)
      .then((data) => this.setData(data))
      .catch((error) => this.setHistory(error));
  };

  loadPage = (event, link) => {
    axios
      .get(link, { params: { page: event.value } })
      .then((response) => response.data)
      .then((data) => this.setData(data))
      .catch((error) => this.setHistory(error));
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
    axios
      .delete(href)
      .then(
        this.setState({
          users: this.state.users.filter((user) => user.id !== id),
        })
      )
      .catch((error) => this.setHistory(error));
    if (this.currentUserId === id) {
      this.clearUser();
    }
  };

  setHistory = (error) => {
    this.props.history.push({
      pathname: Constants.ERROR_PAGE_ROUTE,
      status: { code: error.response.status },
    });
  };

  updateUser = (href, user) => {
    axios
      .put(href, this.prepareData(user))
      .catch((error) => this.setHistory(error));
  };

  prepareData = (user) => {
    let data = new FormData();
    let updateUser = { id: user.id, enabled: user.enabled };
    let roles = [];
    user.roles.map((role) => roles.push({ id: role.id, name: role.name }));
    data.append(Constants.RESPONSE_USER_NAME, JSON.stringify(updateUser));
    data.append(Constants.RESPONSE_ROLES_NAME, JSON.stringify(roles));
    return data;
  };

  changeAuthority = (href, id) => {
    let users = this.state.users;
    users.map((user) => {
      if (user.id == id) {
        user = this.updateUserRoles(user, href);
        this.updateView(user, users);
      }
    });
  };

  updateUserRoles = (user, href) => {
    let roles = user.roles;
    roles.map((role, index) =>
      role.name === Constants.ADMIN_ROLE
        ? user.roles.splice(index)
        : user.roles.push({ id: 1, name: Constants.ADMIN_ROLE })
    );
    this.updateUser(href, user);
    return user;
  };

  updateView = (user, users) => {
    this.currentUserId === user.id ? this.redirect() : this.setState({ users });
  };

  redirect = () => {
    localStorage.removeItem(Constants.MAIN_DATA_STORAGE_NAME);
    window.location.href = Constants.MAIN_PAGE_ROUTE;
  };

  changeEnable = (href, id) => {
    let users = this.state.users;
    users.map((user) => {
      if (user.id == id) {
        user = this.updateUserEnabled(user, href);
      }
    });
    this.setState({ users });
  };

  updateUserEnabled = (user, href) => {
    user.enabled = !user.enabled;
    if (!user.enabled && this.currentUserId == user.id) {
      this.disableUser(user, href);
    }
    this.updateUser(href, user);
    return user;
  };

  disableUser = (user, href) => {
    this.logout();
    this.updateUser(href, user);
    this.clearUser();
  };

  clearUser = () => {
    localStorage.clear();
    window.location.href = Constants.MAIN_PAGE_ROUTE;
  };

  logout = () => {
    axios
      .post(
        JSON.parse(localStorage.getItem(Constants.MAIN_DATA_STORAGE_NAME))
          ._links.logout
      )
      .catch((error) => this.setHistory(error));
  };

  loadPages = () => {
    this.pages = [];
    for (let i = 0; i < this.state.page.totalPages; i++) {
      this.pages.push({ value: i, label: i + 1 });
    }
  };

  changeState = (link) => {
    this.loadData(link);
  };

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    this.loadPages();
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
                            user.roles.find(
                              (role) => role.name === Constants.ADMIN_ROLE
                            )
                              ? true
                              : false
                          }
                          onChange={this.changeAuthority.bind(
                            this,
                            user._links.self.href,
                            user.id
                          )}
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
                          defaultChecked={user.enabled ? true : false}
                          onChange={this.changeEnable.bind(
                            this,
                            user._links.self.href,
                            user.id
                          )}
                        />
                      </Form>
                    </td>
                    <td>
                      <Button
                        variant="link"
                        style={{ color: "red" }}
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
          pages={this.pages}
          loadPage={this.loadPage}
        />
      </>
    );
  }
}
