import React from "react";
import { Figure, Image, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

export default class MainPageMenuItem extends React.Component {
  render() {
    return (
      <Container style={{ textAlign: "center" }}>
        <Figure>
          <Link to={{pathname: "/books/popular", state:{link: this.props.link}}}>
            <div
              style={
                ({ position: "relative" },
                { width: "100%" },
                { textAlign: "center" })
              }
            >
              <Image
                fluid
                alt="book.picture"
                src="https://picsum.photos/2400/1200"
                rounded
              />
              <div
                style={
                  ({ position: "absolute" },
                  { left: "0px" },
                  { bottom: "0px" },
                  { width: "100%" })
                }
              >
                {this.props.text}
              </div>
            </div>
          </Link>
        </Figure>
      </Container>
    );
  }
}
