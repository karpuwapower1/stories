import axios from "axios";
import React from "react";
import ReactDOM from "react-dom";
import BookComponent from "./components/BookComponent.js";
import NavbarComponent from "./components/NavbarComponent.js";
import "bootstrap/dist/css/bootstrap.min.css";

class Main extends React.Component {
  constructor(props) {
    super(props);
	this.state = { user: {}};
  }

  componentDidMount() {
    let user = axios.get("/user").then((response) => {
      const user = response.data;
      this.setState({ user });
    });
  }

  render() {
	  console.log(this.state.user.books);
	  const books = this.state.user.books;
   console.log(books);
    return (
      <div>
        <NavbarComponent user={this.state.user} />
		books.map(function(book) {
			<BookComponent book={book} />
		})
        
      </div>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("app"));