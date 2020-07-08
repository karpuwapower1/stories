import React from "react";
import BookCarousel from "../components/main_page/BookCarousel.js";
import LoadingComponent from "../components/general/LoadingComponent.js";
import NavbarComponent from "../components/navbar/NavbarComponent.js";
import { Row, Col, Container } from "react-bootstrap";
import axios from "axios";

export default class Main extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      user: {},
      popularBooks: [],
      updatedBooks: [],
      isLoaded: false,
    };
  }

  componentDidMount = () => {axios({
    method:'get',
    url:'/main',
    config: { headers: {'Content-Type': 'application/json'}}
   })
   .then( response => (response.data))
   .then (data => {
    this.setState({
     user: data.userDto,
     popularBooks: data.mostPopular,
     updatedBooks: data.lastUpdate,
     isLoaded: true, 
    })
    })
   .catch(error=>{
       console.log(error)
   })
  }

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    console.log(this.state.user);
    console.log(this.state.mostPopular);
    const popularBooks = this.state.popularBooks
      ? Array.from(this.state.popularBooks.content)
      : [];
    const updatedBooks = this.state.updatedBooks
      ? Array.from(this.state.updatedBooks.content)
      : [];
    return (
      <div>
        <NavbarComponent user={this.state.user} />
        <Container>
            <h3 style={{ textAlign: "center" }}>Popular Books </h3>
            <BookCarousel books={popularBooks} />
        
            <h3 style={{ textAlign: "center" }}>Last update </h3>
            <BookCarousel books={popularBooks} />
            </Container>
      </div>
    );
  }
}
