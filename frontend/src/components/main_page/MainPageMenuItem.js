import React from "react";
import { Card, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

export default class MainPageMenuItem extends React.Component {
  styles = {
    fontSize: "50px",
    color: "black",
  };
  render() {
    return (
      <Container>
            <Link 
              style={this.styles}
              to={{
                pathname: "/books",
                state: { link: this.props.link },
              }}
            >
              <Card border="light" style={{ fontStyle: "italic", display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "40vh",}}>
              {this.props.text}
              </Card>
            </Link>
      </Container>
    );
  }
}