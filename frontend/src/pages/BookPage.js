import React from "react";
import LeftMenuComponent from "../components/book_page/LeftMenuComponent.js";
import LoadingComponent from "../components/general/LoadingComponent.js";
import ChapterComponent from "../components/book_page/ChapterComponent.js";
import { Container, Col, Row } from "react-bootstrap";
import axios from "axios";
import Constants from "../constants.js";

export default class BookPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
  }

  initialState = {
    id: "",
    bookName: "",
    bookDescription: "",
    chapters: [],
    isLoaded: false,
  };

  componentDidMount = () => {
    axios
      .get(this.props.location.state.links)
      .then((response) => response.data)
      .then((data) => this.updateState(data))
      .catch((error) => this.setHistory(error))
  };

  updateState = (data) => {
    this.setState({
      id: data.id,
      bookName: data.name,
      bookDescription: data.description,
      chapters: data.chapters,
      isLoaded: true,
    });
  };

  setHistory = (error) => {
    this.props.history.push({
      pathname: Constants.ERROR_PAGE_ROUTE,
      status: { code: error.response.status },
    });
  };

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    const chapters = this.state.chapters ? this.state.chapters : [];
    return (
      <Container>
        <Row>
          <Col xs={3} sm={3} md={3} lg={1}>
            <LeftMenuComponent chapters={chapters} />
          </Col>
          <Col md={7} xs={9} sm={9} lg={11}>
            {chapters.map((chapter) => {
              return <ChapterComponent chapter={chapter} key={chapter.id} />;
            })}
          </Col>
        </Row>
      </Container>
    );
  }
}
