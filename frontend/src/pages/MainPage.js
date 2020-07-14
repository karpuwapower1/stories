import React from "react";
import MainPageMenuComponent from "../components/main_page/MainPageMenuComponent.js";
import LoadingComponent from "../components/general/LoadingComponent.js";
import { Container } from "react-bootstrap";
import axios from "axios";

export default class Main extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      user : {}, 
      links: [],
      isLoaded: false,
    };
  }

  componentDidMount = () => {
   axios({
    method:'get',
    url:"http://localhost:8080/main",
    config: { headers: {'Content-Type': 'application/json'}
  }
})
   .then(response => (response.data))
   .then (data => {
      this.setState({
     user: data,
     links: data._links,
     isLoaded: true, 
    })
     })
   .catch(error=>{
       this.setState({
         isLoaded: false,
       });
   })
  }

  render() {
    if (!this.state.isLoaded) {
       return <LoadingComponent/>
    }
    return (
      <div>
        <Container>
            <MainPageMenuComponent popularBooksLink={this.state.links.popular.href} 
            lastUpdatedBooksLink = {this.state.links.update.href} />
        </Container>
      </div>
    );
  }
}