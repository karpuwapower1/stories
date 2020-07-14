import React from "react";
import LeftMenuComponent from "../components/book_page/LeftMenuComponent.js";
import LoadingComponent from "../components/general/LoadingComponent.js";
import ChapterComponent from "../components/book_page/ChapterComponent.js";
import { Container, Col, Row } from "react-bootstrap";
import axios from "axios";
import '../css/toggle.css';

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
    console.log(this.props.location.state.links.self.href);
    axios({
      method: "GET",
      url: this.props.location.state.links.self.href,
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
        <Container >
      <Row>
        <Col xs={3} sm={3} md={3} lg = {1}>
          <LeftMenuComponent chapters = {chapters}/>
        </Col>
        <Col md={9} xs={9} sm={9} lg={11}>
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
