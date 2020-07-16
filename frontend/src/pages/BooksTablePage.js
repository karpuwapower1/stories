import React from "react";
import {Button, Table, Container, Nav} from "react-bootstrap";
import {Link} from "react-router-dom";
import LoadingComponent from "../components/general/LoadingComponent.js";
import axios from "axios";

export default class BookTablePage extends React.Component {
  constructor(props) {
    super(props);
    this.state = { books: [], links: [], isLoaded: false, show: false };
    this.deleteBook = this.deleteBook.bind(this);
  }

  styles = {
    color : "black"
  };

  componentDidMount = () => {
    axios
      .get(this.props.location.state.link)
      .then((response) => response.data)
      .then((data) => {
        console.log(data);
        this.setState({
          books: data._embedded.books,
          links: [],
          isLoaded: true,
        });
        
      });
  };
  

  deleteBook=(href, id) =>{
    console.log(href);
    console.log(id)
    try {
     axios.delete(href)
     .then(this.setState ({
       books : this.state.books.filter((book) => book.id != id),
       show : true
     })
     );
    } catch(error) {
      this.sertState({show : false})
     }
  
  }

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />
    }
    return (
      <>
      <Container>
        <Table striped bordered hover style={{textAlign : "center"}}>
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
                  <td><Link style = {this.styles} to={{pathname: `/books/${book.id}`, state:{links: book._links}}}>
                  {book.name} </Link></td>
                  <td> <Link style = {this.styles} to={{pathname: `/users/${book.user.id}`, state:{links: book._links}}}>{book.user.firstName} {book.user.lastName}</Link>
                    </td>
                  <td>
                    {book.genres.map(genre => {
                    return (
                  <Link style = {this.styles} to={{pathname: `/books/genres/${genre.name}`, state:{links: book._links}}}>
                  {genre.name}{" "} 
                  </Link>
                    );
                  })}
                    </td>
                 
                  <td style= {{textAlign : "justify"}}>{book.description}</td>
                  <td><Button size="sm" onClick={this.deleteBook.bind(this, book._links.delete.href, book.id)}> Delete</Button></td>
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
