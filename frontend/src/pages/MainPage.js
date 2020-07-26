import React from "react";
import MainPageMenuComponent from "../components/main_page/MainPageMenuComponent.js";
import LoadingComponent from "../components/general/LoadingComponent.js";
import { Container } from "react-bootstrap";
import Constants from "../constants.js";
import TagCloud from "../components/main_page/TagCloud.js";

export default class Main extends React.Component {
  constructor(props) {
    super(props);
   this.state = this.initialState;
   this.initiateState();
  }

  initialState = {
    user: {},
    tags: [],
    links: [],
    genres: [],
    isLoaded: false,
  }

  initiateState = () => {
    let data = JSON.parse(localStorage.getItem(Constants.MAIN_DATA_STORAGE_NAME));
    this.state = {
      user: data.user,
      tags: data.tags._embedded ? data.tags._embedded.tupleBackedMaps : [],
      links: data._links,
      genres: data.genres._embedded  ? data.genres._embedded.tupleBackedMaps
      : [],
      isLoaded: true,
    };
  }

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    return (
      <Container>
        <MainPageMenuComponent
          popularBooksLink={this.state.links.popular.href}
          lastUpdatedBooksLink={this.state.links.update.href}
        />
        <TagCloud tags={this.state.tags} genres={this.state.genres} />
      </Container>
    );
  }
}
