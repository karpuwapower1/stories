import React from "react";
import { Form, Card, Col, Row, Button } from "react-bootstrap";

export default class AddChapterComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { name, text, index, setChapterParameter, removeChapter } = this.props;
    return (
      <Row className="justify-content-md-center">
      <Col md={8} xl={8} xs={12} sm={10}>
      <Card className="text-center">
        <Card.Header style={{fontSize:"20px"}}>
          Chapter #{index + 1}
        </Card.Header>
        <Card.Body>
          <Card.Text>
            <Form.Group id={index}>
              <Form.Control as="textarea" rows="2"
                type="text"
                placeholder="Name"
                name="name"
                value={name}
                id={index}
                onChange={(e) => setChapterParameter(e, index)}
                required
              />
            </Form.Group>

            <Form.Group id={index}>
              <Form.Control  as="textarea" rows="10"
                type="text"
                placeholder="Text"
                name="text"
                value={text}
                onChange={(e) => setChapterParameter(e, index)}
                required
              />
            </Form.Group>
          </Card.Text>
          <Card.Text style={{textAlign: "left"}}>
          <Button style = {{color : "red"}}
                      variant="link"
                      type="submit"
                      onClick={(e) => removeChapter(e, index)}
                    >
                      Remove chapter
                    </Button>
                    </Card.Text>
        </Card.Body>
      </Card>
      </Col>
      </Row>
    );
  }
}
