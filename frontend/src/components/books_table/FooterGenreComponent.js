import React from "react";
import FooterItem from "./FooterItem.js";

export default class FooterGenreComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    let { genres, changeState, path } = this.props;
    if (genres._embedded) {
      return genres._embedded.genres.map((genre) => {
        return (
          <FooterItem
            pathname={`${path}${genre.name}`}
            link={genre._links.genre.href}
            onClick={(e) => changeState(genre._links.genre.href)}
            title={genre.name}
          />
        );
      });
    }
    return " ";
  }
}
