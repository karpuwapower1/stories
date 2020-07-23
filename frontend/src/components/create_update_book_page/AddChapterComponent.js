import React from "react";
import { Form, Card, Col, Row, Button, Accordion } from "react-bootstrap";

export default class AddChapterComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  cardHeaderStyles = {
    paddingBottom: "0px",
    paddingTop: "0px",
    marginTop: "0px",
    marginBottom: "0px",
    borderRadius: "10px",
  };

  cardBodyStyles = {
    paddingBottom: "5px",
    paddingTop: "5px",
    paddingLeft: "5px",
    paddingRight: "5px",
  };

  render() {
    const {
      name,
      text,
      index,
      number,
      setChapterParameter,
      removeChapter,
    } = this.props;
    return (
      <Card className="text-center" style={{ borderRadius: "15px" }}>
        <Card.Header style={this.cardHeaderStyles}>
          <Accordion.Toggle as={Button} variant="link" eventKey={index}>
            Chapter #{number} {name}
          </Accordion.Toggle>
        </Card.Header>
        <Accordion.Collapse eventKey={index}>
          <Card.Body style={this.cardBodyStyles}>
            <Card.Text>
              <Form.Group id={index} style={{ marginBottom: "5px" }}>
                <Form.Control
                  style={{ borderRadius: "10px" }}
                  as="textarea"
                  rows="1"
                  type="text"
                  placeholder="Name"
                  name="name"
                  value={name}
                  id={index}
                  onChange={(e) => setChapterParameter(e, index)}
                  required
                />
              </Form.Group>

              <Form.Group
                id={index}
                style={{ marginBottom: "5px", borderRadius: "10px" }}
              >
                <Form.Control
                  style={{ borderRadius: "10px" }}
                  as="textarea"
                  rows="3"
                  type="text"
                  placeholder="Text"
                  name="text"
                  value={text}
                  onChange={(e) => setChapterParameter(e, index)}
                  required
                />
              </Form.Group>
            </Card.Text>
          </Card.Body>
        </Accordion.Collapse>
      </Card>
    );
  }
}
