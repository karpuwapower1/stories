import React from "react";
import { Button, Table, Container, Nav } from "react-bootstrap";
import { Link, location } from "react-router-dom";
import LoadingComponent from "../components/general/LoadingComponent.js";
import axios from "axios";

export default class BookTablePage extends React.Component {
  constructor(props) {
    super(props);
    console.log("constuctor");
    this.state = { books: [], link : this.props.location.state.link, isLoaded: false, show: false };
    this.deleteBook = this.deleteBook.bind(this);
  }

  styles = {
    color: "black",
  };

  componentDidMount = () => {
    this.loadData(this.state.link);
  };

  loadData = (link) => {
    axios
      .get(link)
      .then((response) => response.data)
      .then((data) => {
        if (data._embedded) {
          console.log(data);
          this.setState({ books: data._embedded.bookWithoutContextDtoes });
        }
        this.setState({ isLoaded: true });
      });
  }

  deleteBook = (href, id) => {
    console.log(href);
    console.log(id);
    try {
      axios.delete(href).then(
        this.setState({
          books: this.state.books.filter((book) => book.id != id),
          show: true,
        })
      );
    } catch (error) {
      this.sertState({ show: false });
    }
  };

  changeState = (link) => {
    console.log(link);
   this.loadData(link);
}



  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    return (
      <>
        <Container>
          <Table striped bordered hover style={{ textAlign: "center" }}>
            <thead>
              <tr>
                <th>Book</th>
                <th>Author</th>
                <th>Genre</th>
                <th>Description</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {this.state.books.map((book) => {
                return (
                  <tr key={book.id}>
                    <td>
                      <Link
                        style={this.styles}
                        to={{
                          pathname: `/books/${book.id}`,
                          state: { links: book._links.self.href },
                        }}
                      >
                        {book.name}{" "}
                      </Link>
                    </td>
                    <td>
                      {" "}
                      <Link
                        style={this.styles}
                        to={{
                          pathname: `/books/users/${book.user.id}`,
                          state: { links: book.user._links.author.href },
                        }}
                        onClick={(e) => this.changeState(book.user._links.author.href)}
                      >
                        {book.user.firstName} {book.user.lastName}
                      </Link>
                    </td>
                    <td>
                      {book.genres ? book.genres._embedded.genres.map((genre) => {
                        return (
                          <Link
                            style={this.styles}
                            to={{
                              pathname: `/books/genres/${genre.name}`,
                              state: { links: genre._links.genre.href },
                             
                            }}  onClick={(e) => this.changeState(genre._links.genre.href)}
                          >
                            {genre.name}{" "}
                          </Link>
                        );
                      })
                    : " "
                    }
                    </td>
                    <td style={{ textAlign: "justify" }}>{book.description}</td>
                    <td>
                      <Button
                        size="sm"
                        onClick={this.deleteBook.bind(
                          this,
                          book._links.delete.href,
                          book.id
                        )}
                      >
                        {" "}
                        Delete
                      </Button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </Table>
        </Container>
      </>
    );
  }
}
