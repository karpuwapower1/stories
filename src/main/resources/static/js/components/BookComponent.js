import axios from "axios";
import React from "react";
import ReactDOM from "react-dom";
import { Card, Row, Col, Button } from "react-bootstrap";

class BookComponent extends React.Component {
  render() {
   let book = this.props.book;
    return (
      <Card>
        <Card.Body>
          <Row>
            <Col xs={3}>
              <Card.Img variant="top" src="holder.js/100px180" />
            </Col>
            <Col xs={9}>
              <Card.Title>{book.name}</Card.Title>
              <Card.Text>
                {book.description}
              </Card.Text>
            </Col>
          </Row>
          <div>
            <Button variant="link" className="mr-sm-2">
              Read
            </Button>
          </div>
        </Card.Body>
      </Card>
    );
  }
}

export default BookComponent;
