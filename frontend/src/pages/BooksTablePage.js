import React from "react";
import { Container, Card, Row, Col, Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import LoadingComponent from "../components/general/LoadingComponent.js";
import HeaderComponent from "../components/books_table/HeaderComponent.js";
import FooterItem from "../components/books_table/FooterItem.js";
import axios from "axios";

export default class BookTablePage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      books: [],
      link: this.props.location.state.link,
      isLoaded: false,
      show: false,
      redirect: "",
    };
    this.deleteBook = this.deleteBook.bind(this);
  }

  componentDidMount = () => {
    this.loadData(this.state.link);
  };

  loadData = (link) => {
    axios
      .get(link)
      .then((response) => response.data)
      .then((data) => {
        console.log(data);
        if (data._embedded) {
          this.setState({ books: data._embedded.bookWithoutContextDtoes });
        }
        this.setState({ isLoaded: true });
      });
  };

  deleteBook = (href, id) => {
    console.log(href);
    try {
      axios.delete(href).then(
        this.setState({
          books: this.state.books.filter((book) => book.id !== id),
          show: true,
        })
      );
    } catch (error) {
      this.sertState({ show: false });
    }
  };

  changeState = (link) => {
    this.loadData(link);
  };

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    return (
      <>
        <Container>
          <Row>
            <Col xl={2}></Col>
            <Col>
              {this.state.books.map((book) => {
                return (
                  <Card
                    border="dark"
                    style={{
                      marginTop: "20px",
                      marginBottom: "20px",
                      borderRadius: "20px",
                    }}
                  >
                    <Card.Header
                      style={{ textAlign: "center", borderBottomWidth: "0px" }}
                    >
                      <Col sm={1}></Col>
                      <Row>
                        <HeaderComponent
                          book={book}
                          changeState={(e) =>
                            this.changeState(book.user._links.author.href)
                          }
                          onClickDelete={this.deleteBook.bind(
                            this,
                            book._links.delete.href,
                            book.id
                          )}
                        />
                      </Row>
                    </Card.Header>
                    <Card.Body>{book.description}</Card.Body>
                    <Card.Footer style={{ borderTopWidth: "0px" }}>
                      <Row>
                        <Col>
                          {book.genres._embedded
                            ? book.genres._embedded.genres.map((genre) => {
                                return (
                                  <FooterItem
                                    pathname={`/books/genres/${genre.name}`}
                                    link={genre._links.genre.href}
                                    onClick={(e) =>
                                      this.changeState(genre._links.genre.href)
                                    }
                                    title={genre.name}
                                  />
                                );
                              })
                            : " "}

                          {book.tags._embedded
                            ? book.tags._embedded.tags.map((tag) => {
                                return (
                                  <FooterItem
                                    pathname={`/books/tags/${tag.name}`}
                                    link={tag._links.tag.href}
                                    onClick={(e) =>
                                      this.changeState(tag._links.tag.href)
                                    }
                                    title={tag.name}
                                  />
                                );
                              })
                            : " "}
                        </Col>
                        <Col xs={2} style={{ alignItem: "right" }}>
                          <Button style={{ borderRadius: "18px" }}>
                            <Link
                              style={{ color: "white" }}
                              to={{
                                pathname: `/books/${book.id}`,
                                state: {
                                  links: book._links.self.href,
                                },
                              }}
                            >
                              Read
                            </Link>
                          </Button>
                        </Col>
                      </Row>
                    </Card.Footer>
                  </Card>
                );
              })}
            </Col>
            <Col xl={2}></Col>
          </Row>
        </Container>
      </>
    );
  }
}
