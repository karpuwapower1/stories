import React from "react";
import {
  Button,
  Table,
  Container,
  Card,
  DropdownButton,
  Dropdown,
  Row,
  Col,
} from "react-bootstrap";

export default class DropdownItemComponent extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <Dropdown.Item onClick={this.props.onClick}>
        {this.props.title}
      </Dropdown.Item>
    );
  }
}
