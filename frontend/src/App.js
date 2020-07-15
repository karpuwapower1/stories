import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import MainPage from "./pages/MainPage.js";
import LoginPage from "./pages/LoginPage.js";
import RegistrationPage from "./pages/RegistrationPage.js";
import BooksTablePage from "./pages/BooksTablePage.js";
import NavbarComponent from "./components/navbar/NavbarComponent.js"
import BookPage from "./pages/BookPage.js";
import AddBookPage from "./pages/AddBookPage.js";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import interseptors from "./Interceptors.js"

export default class App extends React.Component {
  render() {
    return (
      <Router>
      <NavbarComponent />
        <Switch>
          <Route path="/" exact component={MainPage} />
          <Route path="/main" component={MainPage} />
          <Route path="/auth/login" component={LoginPage} />
          <Route path="/auth/registration" component={RegistrationPage} />
          <Route path="/books/popular" component={BooksTablePage} />
          <Route path="/books/:id" component={BookPage} />
          <Route path="/add/book" component={AddBookPage} />
          {/* <Route path="user/:id/books" */}
          {/* <Route path="/books/{id}" component={BookPage}/>
          <Route path="/books" component={BooksPage}/> */}
        </Switch>
      </Router>
    );
  }
}