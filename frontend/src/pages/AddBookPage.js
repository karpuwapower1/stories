import React from "react";
import {
  Container,
  Col,
  Row,
  Form,
  Button,
  Card,
  Accordion,
} from "react-bootstrap";
import axios from "axios";
import "../css/toggle.css";
import AddChapterComponent from "../components/AddChapterComponent.js";
import Select from "react-select";

export default class AddBookPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    const data = JSON.parse(localStorage.getItem("main_data"));
    this.initiateGenres(data);
  }

  genres = [];

  initiateGenres = (data) => {
    if (data.genres._embedded) {
      data.genres._embedded.tupleBackedMaps.map((genre) =>
        this.genres.push({ value: {name: genre.name, id: genre.id}, label: genre.name })
      );
    }
  };

  tags = this.getTagsFromLocalStorage;

  getTagsFromLocalStorage = () => {
    const data = JSON.parse(localStorage.getItem("main_data"));
    return data.tags._embedded ? data.tags._embedded.tupleBackedMaps : [];
  };

  initialState = {
    name: "",
    description: "",
    chapters: [],
    genres: [],
    tags: [],
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
  };

  cardBodyStyles = {
    paddingBottom: "10px",
    paddingTop: "10px",
    borderRadius: "15px",
  };

  addBook = (e) => {
    e.preventDefault();
    try {
      axios.post(this.props.location.state.link, this.createReturnData());
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
    data.append("genres", JSON.stringify(this.state.genres));
    return data;
  };

  setParameter = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  addGenre = (event) => {
    this.setState({ genres: event ? event.map(x => x.value) : [] });
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
    console.log(this.state.genres)
    let { name, description, chapters, genres } = this.state;
    return (
      <Container>
        <Row className="justify-content-md-center">
          <Col md={8} xl={8} xs={12} sm={10}>
            <Card className="text-center" style={{ borderRadius: "15px" }}>
              <Form onSubmit={this.addBook} id="bookForm">
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
                        onChange={this.setParameter}
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
                        onChange={this.setParameter}
                        required
                      />
                    </Form.Group>

                    
                        <Select
                        placeholder="Genres"
                         isMulti
                         name="genres"
                         options={this.genres}
                         onChange={this.addGenre}
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
                                removeChapter={this.removeChapter}
                                setChapterParameter={this.setChapterParameter}
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
                                onClick={(e) => this.removeChapter(e, index)}
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
                        onClick={this.addChapter}
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
                        onClick={this.addBook}
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
