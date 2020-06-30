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
      console.log(response.data);
      const user = response.data;
      this.setState({ user });
    });
  }

  render() {
	  const books = this.state.user.books;
    return (
      <div>
        <NavbarComponent user={this.state.user} />
		{/* books.map(function(book) {
			<BookComponent book={book} />
		}) */}
        
      </div>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("app"));