import React from "react";

import axios from "axios";
import "../css/toggle.css";
import CreateUpdateComponent from "../components/create_update_book_page/CreateUpdateComponent.js";

export default class AddBookPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
  }

  initialState = {
    name: "",
    description: "",
    chapters: [],
    genres: [],
    tags: [],
  };

  addBook = (e) => {
    e.preventDefault();
    try {
      axios.post(this.props.location.state.link, this.createReturnData());
      this.setState(this.initialState);
    } catch (error) {
      console.log(error);
    }
  };

  createReturnData = () => {
    const data = new FormData();
    const book = { name: this.state.name, description: this.state.description };
    data.append("book", JSON.stringify(book));
    data.append("chapters", JSON.stringify(this.state.chapters));
    data.append("genres", JSON.stringify(this.state.genres));
    return data;
  };

  setParameter = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  addGenre = (event) => {
    this.setState({ genres: event ? event.map(x => x.value) : [] });
  };

  setChapterParameter = (event, i) => {
    let update = [...this.state.chapters];
    update[i][event.target.name] = event.target.value;
    this.setState({ chapters: update });
  };

  addChapter = (event) => {
    event.preventDefault();
    this.setState((state) => ({
      chapters: [...this.state.chapters, { name: "", text: "" }],
    }));
  };

  removeChapter = (event, index) => {
    event.preventDefault();
    const chapters = [...this.state.chapters];
    chapters.splice(index, 1);
    this.setState({ chapters });
  };

  render() {
    console.log(this.genres);
    return (
    <CreateUpdateComponent
      name={this.state.name}
      description={this.state.description}
      chapters={this.state.chapters}
      stateGenres={this.state.genres}
      setBook={this.addBook}
      setParameter = {this.setParameter}
      addGenre={this.addGenre}
      removeChapter= {this.removeChapter}
      setChapterParameter={this.setChapterParameter}
      addChapter = {this.addChapter}
      buttonTitle={"Add book"}
    />               
    );
  }
}
