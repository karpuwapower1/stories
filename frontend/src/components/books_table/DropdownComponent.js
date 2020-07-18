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
import DropdownItemComponent from "./DropdownItemComponent.js";


export default class DropdownComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <DropdownButton
        variant="link"
        style={{ textAlign: "right", alignItem: "right"}}
      >
        <div style={{fontSize: "12px"}}>
          <DropdownItemComponent
            onClick={this.props.onClickRead}
           title="Read"
          />
           <DropdownItemComponent
            onClick={this.props.onClickDelete}
           title="Delete"
          />
          </div>
      </DropdownButton>
    );
  }
}
