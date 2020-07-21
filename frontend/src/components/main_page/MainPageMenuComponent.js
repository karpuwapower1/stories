import React from "react";
import { Col, Row, Card } from "react-bootstrap";
import MainPageMenuItem from "./MainPageMenuItem.js";

export default class MainPageMenuComponent extends React.Component {
  render() {
    return (
      <Row>
        <Col xs={6}>
          
          <MainPageMenuItem
           link={this.props.popularBooksLink} text="Popular"
          />
         
        </Col>
        <Col xs={6}>
       
          <MainPageMenuItem
            link={this.props.lastUpdatedBooksLink}
            text="Update"
          />
        </Col>
      </Row>
    );
  }
}
