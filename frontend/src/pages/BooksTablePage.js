import React from "react";
import { Container, Card, Row, Col, Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import LoadingComponent from "../components/general/LoadingComponent.js";
import HeaderComponent from "../components/books_table/HeaderComponent.js";
import FooterTagComponent from "../components/books_table/FooterTagComponent.js";
import FooterGenreComponent from "../components/books_table/FooterGenreComponent.js";
import SortComponent from "../components/books_table/SortComponent.js";
import PaginationComponent from "../components/books_table/PaginationComponent.js";
import Constants from "../constants.js";
import axios from "axios";

export default class BookTablePage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    this.loadData(this.state.link);
  }

  pages = [];
  sorts = [];
  initialState = {
    books: [],
    page: {},
    paginationLinks: [],
    sorts: [],
    link: this.props.location.state.link,
    isLoaded: false,
    redirect: "",
    user: JSON.parse(localStorage.getItem(Constants.MAIN_DATA_STORAGE_NAME))
      .user,
  };

  loadData = (link) => {
    axios
      .get(link)
      .then((response) => response.data)
      .then((data) => this.setData(data))
      .catch((error) => this.setHistory(error));
  };

  setHistory = (error) => {
    this.props.history.push({
      pathname: Constants.ERROR_PAGE_ROUTE,
      status: { code: error.response.status },
    });
  };

  loadPage = (event, link) => {
    axios
      .get(link, { params: { page: event.value } })
      .then((response) => response.data)
      .then((data) => this.setData(data))
      .catch((error) => this.setHistory(error));
  };

  loadSortPage = (event, link) => {
    axios
      .get(link, {
        params: { sort: event.value.sort, order: event.value.order },
      })
      .then((response) => response.data)
      .then((data) => this.setData(data))
      .catch((error) => this.setHistory(error));
  };

  setData = (data) => {
    if (data.books._embedded) {
      this.setState({
        books: data.books._embedded.bookWithoutContextResponses,
        paginationLinks: data.books._links,
        page: data.books.page,
        sorts: data.sorts,
      });
    }
    this.setState({ isLoaded: true });
  };

  deleteBook = (href, id) => {
    axios
      .delete(href)
      .then(
        this.setState({
          books: this.state.books.filter((book) => book.id !== id),
          show: true,
        })
      )
      .catch((error) => this.setHistory(error));
  };

  changeState = (link) => {
    this.loadData(link);
  };

  loadPages = () => {
    this.pages = [];
    for (let i = 0; i < this.state.page.totalPages; i++) {
      this.pages.push({ value: i, label: i + 1 });
    }
  };

  loadSorts = (sorts) => {
    this.sorts = [];
    this.state.sorts.map((sort) => {
      this.sorts.push({
        value: { sort: sort, order: "desc" },
        label: `${sort} desc`,
      });
      this.sorts.push({
        value: { sort: sort, order: "asc" },
        label: `${sort} asc`,
      });
    });
  };

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    if (!this.state.books || this.state.books.length == 0) {
      return (
        <h1
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "80vh",
          }}
        >
          No such books
        </h1>
      );
    }
    this.loadSorts();
    this.loadPages();
    return (
      <>
        <Container>
          <SortComponent
            sorts={this.sorts}
            loadSortPage={this.loadSortPage}
            link={this.state.paginationLinks.self.href}
          />
          <Row>
            <Col xl={1}></Col>
            <Col>
              {this.state.books.map((book) => {
                return (
                  <Card
                    key={book.id}
                    style={{
                      marginTop: "10px",
                      marginBottom: "10px",
                      borderRadius: "30px",
                    }}
                  >
                    <Card.Header
                      style={{ textAlign: "center", borderBottomWidth: "0px" }}
                    >
                      <Col sm={1}></Col>
                      <Row>
                        <HeaderComponent
                          book={book}
                          user={this.state.user}
                          link={book._links.self.href}
                          changeState={(e) =>
                            this.changeState(book.user._links.author.href)
                          }
                          onClickDelete={this.deleteBook.bind(
                            this,
                            book._links.self.href,
                            book.id
                          )}
                        />
                      </Row>
                    </Card.Header>
                    <Card.Body>{book.description}</Card.Body>
                    <Card.Footer style={{ borderTopWidth: "0px" }}>
                      <Row>
                        <Col>
                          <FooterGenreComponent
                            genres={book.genres}
                            path={"/books/genres/"}
                            changeState={this.changeState}
                          />
                          <FooterTagComponent
                            tags={book.tags}
                            path={"/books/tags/"}
                            changeState={this.changeState}
                          />
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
            <Col xl={1}></Col>
          </Row>
        </Container>

        <PaginationComponent
          links={this.state.paginationLinks}
          page={this.state.page}
          changeState={this.changeState}
          pages={this.pages}
          loadPage={this.loadPage}
        />
      </>
    );
  }
}
