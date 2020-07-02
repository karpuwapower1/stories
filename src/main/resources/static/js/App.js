
import React from "react";
import axios from "axios";
import ReactDOM from "react-dom";
import NavbarComponent from "./components/NavbarComponent.js";
import BookComponent from "./components/BookComponent.js";
import "bootstrap/dist/css/bootstrap.min.css";

class Main extends React.Component {

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

ReactDOM.render(<Main />, document.getElementById("app"));