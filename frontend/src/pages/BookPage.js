import React from "react";
import LeftMenuComponent from "../components/book_page/LeftMenuComponent.js";
import LoadingComponent from "../components/general/LoadingComponent.js";
import ChapterComponent from "../components/book_page/ChapterComponent.js";
import { Container, Col, Row } from "react-bootstrap";
import axios from "axios";

export default class BookPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: "",
      bookName: "",
      bookDescription: "",
      chapters: [],
      isLoaded: false,
    };
  }

  initialState = { book: [] };

  componentDidMount = () => {
    axios({
      method: "GET",
      url: `/books/${this.props.match.params.id}`,
      config: { headers: { "Content-Type": "application/json" } },
    })
      .then((response) => response.data)
      .then((data) => {
        this.setState({
          id: data.id,
          bookName: data.name,
          bookDescription: data.description,
          chapters: data.chapters,
          isLoaded: true,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    const chapters = this.state.chapters ? this.state.chapters : [];
    return (
        <Container fluid>
      <Row>
        <Col xs={1}>
          <LeftMenuComponent />
        </Col>
        <Col>
          {chapters.map((chapter) => {
            return (
              <ChapterComponent chapter = {chapter} key = {chapter.id} />
            );
          })}
        </Col>
      </Row>
      </Container>
    );
  }
}
