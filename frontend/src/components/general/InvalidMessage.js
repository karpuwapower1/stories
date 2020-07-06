import React from "react";
import { Alert } from "react-bootstrap";

export default class InvalidMessage extends React.Component {
  render() {
    if (this.props.message === "") {
      return (null)
    }
    return <Alert variant="danger">{this.props.message}</Alert>;
  }
}