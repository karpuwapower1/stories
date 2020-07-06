import React from "react";
import { Figure } from "react-bootstrap";
import BookCarouselLink from "./BookCarouselLink.js";

export default class BookCarouselItem extends React.Component {
  render() {
    const book = this.props.book;
    const link = this.props.book.links
      ? this.props.book.links.find((link) => (link.rel = "self"))
      : {};
    return (
     
      <div style={{ textAlign: "center" }}>
         <BookCarouselLink link={link.href} text = {book.name}/>
        <Figure>
          <Figure.Image
            width={300}
            height={300}
            alt="book.picture"
            src="https://picsum.photos/500/500"
            rounded
          />
        </Figure>
        <BookCarouselLink link={link.href} text = {book.description} />
      </div>
    );
  }
}