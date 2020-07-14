import React from "react";

export default class ChapterComponent extends React.Component {
  render() {
    const {name, text, number} = this.props.chapter;
    return (
      <div>
        <a name={number}></a>
        <h1 style={{ textAlign: "center" }}> {number}. {name} </h1>
        <p style={{ textAlign: "justify" }}> {text} </p>
      </div>
    );
  }
}