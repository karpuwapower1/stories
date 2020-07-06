import React from "react";

export default class BookCarouselLink extends React.Component {
  render() {
    const link = this.props.link;
    const text = this.props.text;
    return (
      <div>
        <a href={link} style={{ color: "#000" }}>
          <p style={{ textAlign: "center" }}>{text}</p>
        </a>
      </div>
    );
  }
}