import React from "react";
import {Form} from "react-bootstrap";
import BookComponent from "./BookComponent.js";
import NavbarComponent from "./navbar/NavbarComponent.js";

class MainPageComponent extends React.Component {
  constructor(props) {
    console.log("constructor");
    super(props);
    this.state = { user: {} };
  }

  componentDidMount() {
    console.log("moutn");
    let user = axios.get("/user").then((response) => {
      const user = response.data;
      this.setState({ user });
    });
  }

  render() {
    const books = this.state.user.books ? this.state.user.books : [];
    return (
      <div>
        <NavbarComponent user={this.state.user} />
        {books.map((book) => {
          return (
            <Form inline>
          <BookComponent book={book} key={book.id} />
          <BookComponent book={book} key={book.id} />
          </Form>
        );
        })}
      </div>
    );
  }
}

export default MainPageComponent;