import React from "react";
import Select from "react-select";

export default class PaginationSelectComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let { page, pages, loadPage, link } = this.props;
    return (
      <Select
        maxMenuHeight={60}
        placeholder={`Page ${page.number + 1} of ${page.totalPages}`}
        name="page"
        options={pages}
        onChange={(e) => loadPage(e, link)}
        classNamePrefix="select"
      />
    );
  }
}
