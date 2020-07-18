import React from "react";
import { Link } from "react-router-dom";

export default class CardHeaderComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Link
        style={{ color: "black" }}
        to={{
          pathname: `/books/users/${this.props.pathname}`,
          state: { links: this.props.link },
        }}
        onClick={(e) => this.props.changeState(this.props.link)}
      >
        {this.props.linkName}
      </Link>
    );
  }
}
