import React from "react";

export default class ChapterComponent extends React.Component {
  render() {
    const name = this.props.chapter.name;
    const text = this.props.chapter.text;
    return (
      <div>
        <h1 style={{ textAlign: "center" }}> {name} {this.props.chapter.number}</h1>
        <p style={{ textAlign: "justify" }}> {text} </p>
        <a name={this.props.chapter.id}></a>
      </div>
    );
  }
}
