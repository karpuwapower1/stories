import React from "react";
import FooterItem from "./FooterItem.js";

export default class FooterTagComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let { tags, changeState, path } = this.props;
    if (tags._embedded) {
      return tags._embedded.tags.map((tag) => {
        return (
          <FooterItem
            pathname={`${path}${tag.name}`}
            link={tag._links.tag.href}
            onClick={(e) => changeState(tag._links.tag.href)}
            title={tag.name}
          />
        );
      });
    }
    return " ";
  }
}
