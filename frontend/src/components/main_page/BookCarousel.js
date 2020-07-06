import React from "react";
import { Container } from "react-bootstrap";
import BookCaruselItem from "./BookCarouselItem.js";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export default class BookCarousel extends React.Component {

  render() {
    const settings = {
      dots: true,
      arrows: true,
      infinite: true,
      autoplay: true,
      autoplaySpeed: 3000,
      pauseOnHover: true,
      adaptiveHeight: true,
      fade: true,
      slidesToShow: 1,
      slidesToScroll: 1,
    };
    const books = this.props.books ? this.props.books : [];
    return (
      <div>
        <Container>
          <Slider {...settings}>
            {books.map((book, index) => {
              return (
                <div style={{ align: "center" }} key={index}>
                  <BookCaruselItem book={book} />
                </div>
              );
            })}
          </Slider>
        </Container>
      </div>
    );
  }
}