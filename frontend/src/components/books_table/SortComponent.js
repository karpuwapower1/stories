import React from "react";
import Select from "react-select";
import {Row } from "react-bootstrap";

export default class SortComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Select
        className="justify-content-center"
        placeholder="Sort by"
        name="sort"
        options={this.props.sorts}
        onChange={(e) => this.props.loadSortPage(e, this.props.link)}
        classNamePrefix="select"
      />
    );
  }
}
