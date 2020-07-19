import React from "react";
import MainPageMenuComponent from "../components/main_page/MainPageMenuComponent.js";
import LoadingComponent from "../components/general/LoadingComponent.js";
import { Container } from "react-bootstrap";
import TagCloud from "../components/main_page/TagCloud.js";

export default class Main extends React.Component {
  constructor(props) {
    super(props);
    const data = JSON.parse(localStorage.getItem("main_data"));
    const tags = data.tags._embedded ? data.tags._embedded.tupleBackedMaps : [];
    const genres = data.genres._embedded ? data.genres._embedded.tupleBackedMaps : []
    this.state = {
      user: data.user,
      tags: tags,
      links: data._links,
      genres: genres,
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
