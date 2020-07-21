import React from "react";
import { Pagination } from "react-bootstrap";
import PaginationSelectComponent from "./PaginationSelectComponent.js";

export default class PaginationComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let { links, changeState, page, pages, loadPage } = this.props;
    return (
      <Pagination className="justify-content-center">
        <Pagination.First
          disabled={page.number === 0 ? true : false}
          onClick={(e) => {
            links.first ? changeState(links.first.href) : changeState("");
          }}
        />
        <Pagination.Prev
          disabled={page.number === 0 ? true : false}
          onClick={(e) => {
            links.previous ? changeState(links.previous.href) : changeState("");
          }}
        />
        <PaginationSelectComponent
          pages={pages}
          loadPage={loadPage}
          page={page}
          link={links.about.href}
        />
        <Pagination.Next
          disabled={page.number === page.totalPages - 1 ? true : false}
          onClick={(e) => {
            links.next ? changeState(links.next.href) : changeState("");
          }}
        />
        <Pagination.Last
          disabled={page.number === page.totalPages - 1 ? true : false}
          onClick={(e) => {
            links.last ? changeState(links.last.href) : changeState("");
          }}
        />
      </Pagination>
    );
  }
}
