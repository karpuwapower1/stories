
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
    this.state = { user: {},
                    popularBooks: [],
                    updatedBooks: [] };
  }

  async componentDidMount() {
    console.log("moutn");
    const response = await fetch('/');
    const body = response.json();
    const user = body.user;
    const updatedBooks = response.updatedBooks;
    const popularBooks = response.popularBooks;
    this.state({user: boby.user, popularBooks: popularBooks, updatedBooks: updatedBooks});
    // let user = axios.get("/user").then((response) => {
    //   const user = response.data;
    //   this.setState({ user });
    // });
  }

  render() {
    // const books = this.state.user.books ? this.state.user.books : [];
    console.log(this.state.user);
    console.log(this.state.popularBooks);
    console.log(this.state.updatedBooks);
    return (
      <div>
        hello
        {/* <NavbarComponent user={this.state.user} />
        {books.map((book) => {
          return <BookComponent book={book} key={book.id} />;
        })} */}
      </div>
    );
  }
}

export default Main;