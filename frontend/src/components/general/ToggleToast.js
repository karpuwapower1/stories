import React from "react";
import { Row, Toast, Col } from "react-bootstrap";

export default class ToggleToast extends React.Component {
  styles = {
    position: "fixed",
    bottom: "20px",
    left: "20px",
  };

  render() {
    return (
      <Toast style={this.styles}>
        <Toast.Header>Deleted</Toast.Header>
        <Toast.Body>
          Success</Toast.Body>
      </Toast>
    );
  }
}