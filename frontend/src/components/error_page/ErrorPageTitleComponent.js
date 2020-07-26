import React from "react";

export default class ErrorPageTitleComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "50vh",
          textAlign: "center",
        }}
      >
        <div>
          <h1>Error</h1>
          <h3>{this.props.code}</h3>
          <h3>{this.props.title}</h3>
        </div>
      </div>
    );
  }
}
