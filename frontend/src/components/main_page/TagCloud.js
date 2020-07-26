import React from "react";
import { TagCloud } from "react-tagcloud";
import { Container, Row, Col } from "react-bootstrap";
import { Redirect } from "react-router-dom";
var randomColor = require("randomcolor");

export default class Cloud extends React.Component {
  constructor(props) {
    super(props);
    this.createCloudItem();
    this.state = {
      redirect: "",
      link: "",
    };
  }
  
  cloudItem = [];

  createCloudItem = () => {
    if (this.props.tags) {
      this.props.tags.map((tag) => {
        this.cloudItem.push({ value: tag.name, count: tag.totalQuantity });
      });
    }
    if (this.props.genres) {
      this.props.genres.map((genre) => {
        this.cloudItem.push({ value: genre.name, count: genre.totalQuantity });
      });
    }
  };

  chooseTag = (value) => {
    let item = this.searchThroughGenres(value);
    if (item) {
      this.setRedirectState(
        `/books/genres/${item.name}`,
        item._links.genre.href
      );
    } else {
      item = this.searchThroughTags(value);
      this.setRedirectState(`/books/tags/${item.name}`, item._links.tag.href);
    }
  };

  setRedirectState = (pathname, link) => {
    this.setState({
      redirect: pathname,
      link: link,
    });
  };

  searchThroughGenres = (value) => {
    return this.props.genres.find((genre) => genre.name === value);
  };

  searchThroughTags = (value) => {
    return this.props.tags.find((tag) => tag.name === value);
  };

  render() {
    if (this.state.redirect) {
      return (
        <Redirect
          to={{
            pathname: this.state.redirect,
            state: { link: this.state.link },
          }}
        />
      );
    }
    return (
      <Container style={{ textAlign: "center" }}>
        <Row>
          <Col xl={2}> </Col>
          <Col>
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                height: "40vh",
              }}
            >
              <TagCloud
                style={{ color: "#969393" }}
                disableRandomColor={true}
                minSize={16}
                maxSize={40}
                tags={this.cloudItem}
                onClick={(tag) => this.chooseTag(tag.value)}
                shuffle={true}
              />
            </div>
          </Col>
          <Col xl={2}> </Col>
        </Row>
      </Container>
    );
  }
}
