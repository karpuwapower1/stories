import React from "react";
import { Figure } from "react-bootstrap";
import BookCarouselLink from "./BookCarouselLink.js";

export default class BookCarouselItem extends React.Component {
  render() {
    const book = this.props.book;
    const link = this.props.book.links
      ? this.props.book.links.find((link) => (link.rel = "self"))
      : {}
    return (
     
      <div style={{ textAlign: "center" }}>
        <a href={link.href} style={{color: "#000"}}>
        <Figure>
        <Figure.Caption>
         <BookCarouselLink link={link.href} text = {book.name}/>
         </Figure.Caption>
          <Figure.Image
            width={170}
            height={50}
            alt="book.picture"
            src="https://picsum.photos/500/600"
            rounded
          />
        </Figure>
        </a>
      </div>
    );
  }
}