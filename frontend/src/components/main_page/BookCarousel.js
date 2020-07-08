import React from "react";
import { Container } from "react-bootstrap";
import BookCaruselItem from "./BookCarouselItem.js";
import Slider from "react-slick";


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
      responsive: [
        {
          breakpoint: 1400,
          settings: {
            slidesToShow: 5,
            slidesToScroll: 1,
          }
        },
        {
          breakpoint: 1000,
          settings: {
            slidesToShow: 4,
            slidesToScroll: 1,
          }
        },
        {
          breakpoint: 800,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 1,
          }
        },
        {
          breakpoint: 600,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1,
          }
        },
        {
          breakpoint: 480,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
      ]
    };
    const books = this.props.books ? this.props.books : [];
    return (
          <Slider {...settings}>
            {books.map((book, index) => {
              return (
                <div style={{ align: "center" }} key={index}>
                  <BookCaruselItem book={book} />
                </div>
              );
            })}
          </Slider>
    );
  }
}