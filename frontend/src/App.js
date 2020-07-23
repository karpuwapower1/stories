import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import MainPage from "./pages/MainPage.js";
import LoginPage from "./pages/LoginPage.js";
import RegistrationPage from "./pages/RegistrationPage.js";
import BooksTablePage from "./pages/BooksTablePage.js";
import NavbarComponent from "./components/navbar/NavbarComponent.js";
import BookPage from "./pages/BookPage.js";
import AddBookPage from "./pages/AddBookPage.js";
import LoadingComponent from "./components/general/LoadingComponent.js";
import RegistrationConfirmapotionPage from "./pages/RegistrationConfirmationPage.js";
import UpdateBookPage from "./pages/UpdateBookPage.js";
import axios from "axios";
import interseptors from "./Interceptors.js";

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = { isLoaded: false };
  }

  componentDidMount = () => {
    axios
      .get("http://localhost:8080/main")
      .then((response) => response.data)
      .then((data) => {
        localStorage.setItem("main_data", JSON.stringify(data));
        this.setState({ isLoaded: true });
      });
  };

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    return (
      <Router>
        <NavbarComponent />
        <Switch>
          <Route path="/" exact component={MainPage} />
          <Route path="/main" component={MainPage} />
          <Route path="/auth/login" component={LoginPage} />
          <Route path="/auth/registration" component={RegistrationPage} />
          <Route path="/auth/activation"
            component={RegistrationConfirmapotionPage}
          />
          <Route path="/books" exact component={BooksTablePage} />
          <Route path="/books/genres/:name" component={BooksTablePage} />
          <Route path="/books/users/:id" component={BooksTablePage} />
          <Route path="/books/tags/:name" component={BooksTablePage} />
          <Route path="/books/add" exact component={AddBookPage} />
          <Route path="/books/:id" exact component={BookPage} />
          <Route path="/books/update/:id" exact component={UpdateBookPage} />
        </Switch>
      </Router>
    );
  }
}