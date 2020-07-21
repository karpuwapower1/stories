import React from "react";
import { Pagination, Row, Col, Form } from "react-bootstrap";

export default class PaginationComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let {
      links,
      goToFirstPage,
      goToPrevPage,
      goToNextPage,
      goToLastPage,
    } = this.props;
    console.log(links.last);
    return (
      <Row justify-content-md-center>
        <Col xl={4}></Col>
        <Pagination>
          <Pagination.First
            md="1"
            active={links.first ? true : false}
            onClick={goToFirstPage}
          />
          <Pagination.Prev
            active={links.prev ? true : false}
            onClick={goToPrevPage}
            md="1"
          />
          <Form.Control
            md="auto"
            style={{ textAlign: "center" }}
            active={links ? true : false}
          />
          <Pagination.Next
            active={links.next ? true : false}
            onClick={goToNextPage}
            md="1"
          />
          <Pagination.Last
            active={links.last ? true : false}
            onClick={goToLastPage}
            md="1"
          />
        </Pagination>
        <Col xl={4}></Col>
      </Row>
    );
  }
}
