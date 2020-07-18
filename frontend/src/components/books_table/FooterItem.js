import React from "react";
import {Button} from "react-bootstrap";
import {Link } from "react-router-dom";

export default class FooterComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <>
        <Button variant="light" size="sm"> 
        <Link  style={{color:"black"}}
          to={{
            pathname: this.props.pathname,
            state: this.props.link,
          }}
          onClick={this.props.onClick}
        >
          {this.props.title}
        </Link>
        </Button>{" "}
        </>
    );
  }
}
