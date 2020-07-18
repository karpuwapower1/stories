import React from "react";
import {Link} from "react-router-dom";

export default class NavbarMenuComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    if (this.props.user.id) {
      return <Link to={{pathname: "/add/book", link: this.props.links.add_book}} >Add book</Link>;
    }
    return " ";
  }
}
