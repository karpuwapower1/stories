import React from "react";
import ErrorPageTitleComponent from "../components/error_page/ErrorPageTitleComponent.js";

export default class ErrorPage extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    switch (this.props.location.status.code) {
      case 403:
        return (
          <ErrorPageTitleComponent
            code={this.props.location.status.code}
            title={"Forbidden"}
          />
        );
      case 404:
        return (
          <ErrorPageTitleComponent
            code={this.props.location.status.code}
            title={"Bad Request"}
          />
        );
      case 500:
        return (
          <ErrorPageTitleComponent
            code={this.props.location.status.code}
            title={"Server Error"}
          />
        );
      default:
        return "";
    }
  }
}
