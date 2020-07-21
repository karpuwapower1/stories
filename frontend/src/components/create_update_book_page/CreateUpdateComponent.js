import React from "react";
import AddChapterComponent from "./AddChapterComponent.js";
import Select from "react-select";
import {
  Container,
  Col,
  Row,
  Form,
  Button,
  Card,
  Accordion,
} from "react-bootstrap";

export default class CreateUpdateBookComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  cardHeaderStyles = {
    paddingBottom: "0px",
    paddingTop: "0px",
    borderRadius: "15px",
  };

  cardBodyStyles = {
    paddingBottom: "5px",
    paddingTop: "5px",
    paddingLeft: "5px",
    paddingRight: "5px",
    paddingBottom: "10px",
    paddingTop: "10px",
    borderRadius: "15px",
  };

  render() {
    let {
      name,
      description,
      chapters,
      stateGenres,
      selectGenres,
      addBook,
      setParameter,
      addGenre,
      removeChapter,
      setChapterParameter,
      addChapter,
      buttonTitle,
    } = this.props;
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md={8} xl={8} xs={12} sm={10}>
            <Card className="text-center" style={{ borderRadius: "15px" }}>
              <Form onSubmit={addBook} id="bookForm">
                <Card.Header style={this.cardHeaderStyles}>
                  <h1>Book {name}</h1>
                </Card.Header>
                <Card.Body>
                  <Card.Text>
                    <Form.Group controlId="formBasicBookName">
                      <Form.Control
                        style={{ borderRadius: "10px" }}
                        as="textarea"
                        rows="1"
                        type="text"
                        placeholder="Name"
                        name="name"
                        value={name}
                        onChange={setParameter}
                        required
                      />
                    </Form.Group>

                    <Form.Group controlId="formBasicBookDescription">
                      <Form.Control
                        style={{ borderRadius: "10px" }}
                        as="textarea"
                        rows="3"
                        type="text"
                        placeholder="Description"
                        name="description"
                        value={description}
                        onChange={setParameter}
                        required
                      />
                    </Form.Group>

                    <Select
                      placeholder="Genres"
                      isMulti
                      name="genres"
                      options={selectGenres}
                      onChange={addGenre}
                      className="basic-multi-select"
                      classNamePrefix="select"
                    />

                    <Accordion>
                      {chapters.map((chapter, index) => {
                        let { name, text } = chapter;
                        return (
                          <Row className="justify-content-md-center">
                            <Col>
                              <AddChapterComponent
                                name={name}
                                text={text}
                                index={index}
                                key={index}
                                removeChapter={removeChapter}
                                setChapterParameter={setChapterParameter}
                              />
                            </Col>
                            <Col md={3}>
                              <Button
                                style={{
                                  color: "red",
                                  borderColor: "red",
                                  borderRadius: "10px",
                                }}
                                variant="link"
                                type="submit"
                                onClick={(e) => removeChapter(e, index)}
                              >
                                Remove chapter
                              </Button>
                            </Col>
                          </Row>
                        );
                      })}
                    </Accordion>
                  </Card.Text>
                </Card.Body>

                <Card.Footer style={this.cardHeaderStyles}>
                  <Row>
                    <Col md={6}>
                      <Button
                        style={{
                          borderColor: "#00f4ffad",
                          borderRadius: "10px",
                        }}
                        variant="link"
                        color="blue"
                        type="submit"
                        onClick={addChapter}
                      >
                        Add Chapter
                      </Button>
                    </Col>
                    <Col md={6}>
                      <Button
                        style={{
                          borderColor: "#00f4ffad",
                          borderRadius: "10px",
                        }}
                        variant="link"
                        color="blue"
                        type="submit"
                        onClick={addBook}
                      >
                        Add Book
                      </Button>
                    </Col>
                  </Row>
                </Card.Footer>
              </Form>
            </Card>
          </Col>
        </Row>
      </Container>
    );
  }
}
