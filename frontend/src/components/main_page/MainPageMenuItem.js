import React from "react";
import { Figure, Container } from "react-bootstrap";
import { Link } from "react-router-dom";
import ShadowsIntoLightTwo from "../../fonts/ShadowsIntoLightTwo.ttf";

export default class MainPageMenuItem extends React.Component {
  styles = {
    fontSize: "50px",
    color: "black",
    fontFamily: { ShadowsIntoLightTwo },
  };
  render() {
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
          <Figure>
            <Link
              style={this.styles}
              to={{
                pathname: "/books/popular",
                state: { link: this.props.link },
              }}
            >
              {this.props.text}
            </Link>
          </Figure>
        </div>
      </Container>
    );
  }
}
