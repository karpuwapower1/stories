import axios from "axios";
import React from "react";
import ReactDOM from "react-dom";
import BookComponent from "./BookComponent.js";
import NavbarComponent from "./NavbarComponent.js";
import "bootstrap/dist/css/bootstrap.min.css";

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
          return <BookComponent book={book} key={book.id} />;
        })}
      </div>
    );
  }
}

export default MainPageComponent;