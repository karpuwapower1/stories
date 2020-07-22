import React from "react";
import {Link} from "react-router-dom";

export default class NavbarMenuComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    if (this.props.user != null) {
      return <Link to={{pathname: "/books/add", state : {link: this.props.links.add_book.href}}} >Add book</Link>;
    }
    return " ";
  }
}
