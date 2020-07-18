import React from "react";
import { Container, Col, Row, Form, Button, Card } from "react-bootstrap";
import axios from "axios";
import "../css/toggle.css";
import AddChapterComponent from "../components/AddChapterComponent.js";

export default class AddBookPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    console.log(this.props);
  }

  initialState = {
    name: "",
    description: "",
    chapters: [],
  };

  addBook = (e) => {
    e.preventDefault();
    try {
      axios.post("http://localhost:8080/books", this.createReturnData());
      this.setState(this.initialState);
    } catch (error) {
      console.log(error);
    }
  };

  createReturnData = () => {
    const data = new FormData();
    const book = { name: this.state.name, description: this.state.description };
    data.append("book", JSON.stringify(book));
    data.append("chapters", JSON.stringify(this.state.chapters));
    return data;
  };

  setParameter = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  setChapterParameter = (event, i) => {
    let update = [...this.state.chapters];
    update[i][event.target.name] = event.target.value;
    this.setState({ chapters: update });
  };

  addChapter = (event) => {
    event.preventDefault();
    this.setState((state) => ({
      chapters: [...this.state.chapters, { name: "", text: "" }],
    }));
  };

  removeChapter = (event, index) => {
    event.preventDefault();
    const chapters = [...this.state.chapters];
    chapters.splice(index, 1);
    this.setState({ chapters });
  };

  render() {
    let { name, description, chapters } = this.state;
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md={8} xl={8} xs={12} sm={10}>
            <Card className="text-center">
              <Card.Header style={{ fontSize: "30px" }}>Book</Card.Header>
              <Card.Body>
                <Form onSubmit={this.addBook} id="bookForm">
                  <Card.Text>
                    <Form.Group controlId="formBasicBookName">
                      <Form.Control
                        as="textarea"
                        rows="2"
                        type="text"
                        placeholder="Name"
                        name="name"
                        value={name}
                        onChange={this.setParameter}
                        required
                      />
                    </Form.Group>

                    <Form.Group controlId="formBasicBookDescription">
                      <Form.Control
                        as="textarea"
                        rows="7"
                        type="text"
                        placeholder="Description"
                        name="description"
                        value={description}
                        onChange={this.setParameter}
                        required
                      />
                    </Form.Group>

                    {chapters.map((chapter, index) => {
                      let { name, text } = chapter;
                      return (
                        <AddChapterComponent
                          name={name}
                          text={text}
                          index={index}
                          key={index}
                          removeChapter={this.removeChapter}
                          setChapterParameter={this.setChapterParameter}
                        />
                      );
                    })}
                  </Card.Text>
                  <Card.Text style={{ textAlign: "right" }}>
                    <Button
                      style={{ color: "green" }}
                      variant="link"
                      type="submit"
                      onClick={this.addChapter}
                    >
                      Add chapter
                    </Button>
                  </Card.Text>
                  <Card.Footer className="text-muted">
                    <Button
                      variant="primary"
                      type="submit"
                      onClick={this.addBook}
                      size="lg"
                    >
                      Add Book
                    </Button>
                  </Card.Footer>
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    );
  }
}
