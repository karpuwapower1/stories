import React from "react";
import Link from "react-router-dom";

export default class BookCarouselLink extends React.Component {
  render() {
    const link = this.props.link;
    const text = this.props.text;
    return (
      <div>
          <a href={link}></a>
          <p style={{ textAlign: "center" }}>{text}</p>
      </div>
    );
  }
}