import React from "react";
import { Col, Spinner } from "react-bootstrap";

export default class LoadingComponent extends React.Component {
  render() {
    return (
      <Col sm="12" md={{ size: 6, offset: 5 }}>
        <Spinner animation="border" role="status">
          <span className="sr-only">Loading...</span>
        </Spinner>
      </Col>
    );
  }
}
