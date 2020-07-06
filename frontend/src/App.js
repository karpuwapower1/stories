import React from "react";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import MainPage from "./pages/MainPage.js";
import LoginPage from "./pages/LoginPage.js";
import RegistrationPage from "./pages/RegistrationPage.js";
import BookPage from "./pages/BookPage.js";

export default class App extends React.Component {

  render() {
    return (
      <Router>
        <Switch>
          <Route path="/" exact component={MainPage}/>
          <Route path="/main" component={MainPage}/>
          <Route path="/login" component={LoginPage}/>
          <Route path="/registration" component={RegistrationPage}/>
         {/* <Route path="user/:id/books" */}
          <Route path="/books/:id" component={BookPage}/>
          {/* <Route path="/books/{id}" component={BookPage}/>
          <Route path="/books" component={BooksPage}/> */}
        </Switch>
      </Router>
    );
  }
}