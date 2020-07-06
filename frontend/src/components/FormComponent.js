import React from "react";
import { Form, Button, Container, Col, Row } from "react-bootstrap";
import NavbarComponent from "../components/navbar/NavbarComponent.js";

export default class FormComponent extends React.Component {
 
  render() {
      const formName = this.props.formName;
      const formValue = this.props.formValue;
      const formType = this.props.formType;
      const formPlaceholder = this.props.formPlaceholder;
      const formOnchangeMethod = this.props.formOnchangeMethod;
    return (
                <Form.Control
                  type={formType}
                  placeholder={formPlaceholder}
                  name = {formName}
                  value={formValue}
                  onChange={this.formOnchangeMethod}
                  required
                />
    );
  }
}
