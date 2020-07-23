import React from "react";
import AddChapterComponent from "./AddChapterComponent.js";
import Select from "react-select";
import InputTagComponent from "./InputTagComponent.js";
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
    const data = JSON.parse(localStorage.getItem("main_data"));
    this.initiateGenres(data);
    this.initiateTags(data);
  }
  
  genres=[]
  tags = [];
  choosedGenres = [];
  values = [];

  initiateGenres = (data) => {
    if (data.genres._embedded) {
      data.genres._embedded.tupleBackedMaps.map((genre) =>
        this.genres.push({ value: {name: genre.name, id: genre.id}, label: genre.name })
      );
    }
  };

  initiateTags = (data) => {
    if (data.tags._embedded) {
      data.tags._embedded.tupleBackedMaps.map((tag) =>
        this.tags.push({ value: {name: tag.name, id: tag.id}, label: tag.name })
      );
    }
  };

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

  prepareValues = (data) => {
    this.values = [];
    if (data) {
      data.map((genre) =>
        this.values.push({ value: {name: genre.name, id: genre.id}, label: genre.name })
      );
    }
  };

  render() {
    let {
      name,
      description,
      chapters,
      setBook,
      setParameter,
      addGenre,
      removeChapter,
      setChapterParameter,
      addChapter,
      addTag,
      deleteTag,
      choosedTags,
      choosedGenres,
    } = this.props;
    this.prepareValues(choosedGenres);
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md={8} xl={8} xs={12} sm={10}>
            <Card className="text-center" style={{ borderRadius: "15px" }}>
              <Form onSubmit={setBook} id="bookForm">
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
                      options={this.genres}
                      onChange={addGenre}
                      value={this.values}
                      isClearable={true}
                      className="basic-multi-select"
                      classNamePrefix="select"
                    />
                    <Form.Group controlId="selectTag">
                    <InputTagComponent addTag={addTag}
                    deleteTag={deleteTag}
                    choosedTags={choosedTags}
                    />
                    </Form.Group>

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
                                number={chapter.number}
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
                        onClick={setBook}
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
