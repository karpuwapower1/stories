import React from "react";
import { TagCloud } from "react-tagcloud";
import { Container} from "react-bootstrap";
import { Redirect } from "react-router-dom";

export default class Cloud extends React.Component {
  constructor(props) {
    super(props);
    this.createTags();
    this.state = {
      redirect: "",
      link: "",
    };
  }

  tags = [];

  createTags = () => {
    if (this.props.tags) {
      this.props.tags.map((tag) => {
        this.tags.push({ value: tag.name, count: tag.totalQuantity });
      });
    }
  };

  chooseTag = (value) => {
    let tag = this.props.tags.find((tag) => tag.name === value);
    this.setState({
      redirect: `/books/tags/${tag.name}`,
      link: tag._links.tag.href,
    });
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
    if (this.tags) {
      return (
        <Container>
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              height: "40vh",
            }}
          >
            <TagCloud
              minSize={16}
              maxSize={50}
              tags={this.tags}
              onClick={(tag) => this.chooseTag(tag.value)}
              shuffle={true}
            />
          </div>
        </Container>
      );
    }
    return " ";
  }
}
