import React from "react";
import CreateUpdateComponent from "../components/create_update_book_page/CreateUpdateComponent.js";
import axios from "axios";
import Constants from "../constants.js";

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

  addBook = (event) => {
    event.preventDefault();
    axios
      .post(this.props.location.state.link, this.createReturnData())
      .then((response) => this.setState(this.initialState))
      .catch((error) => this.setHistory(error))
  };

  setHistory = (error) => {
    this.props.history.push({
      pathname: Constants.ERROR_PAGE_ROUTE,
      status: { code: error.response.status },
    });
  };

  createReturnData = () => {
    const data = new FormData();
    const book = { name: this.state.name, description: this.state.description };
    data.append(Constants.RESPONSE_BOOK_NAME, JSON.stringify(book));
    data.append(Constants.RESPONSE_CHAPTERS_NAME, JSON.stringify(this.state.chapters));
    data.append(Constants.RESPONSE_GENRES_NAME, JSON.stringify(this.state.genres));
    data.append(Constants.RESPONSE_TAGS_NAME, JSON.stringify(this.prepareTags()));
    return data;
  };

  prepareTags = () => {
    let tags = [];
    if (this.state.tags) {
      this.state.tags.map((tag) => {
        tags.push({ name: tag.text });
      });
    }
    return tags;
  };

  setParameter = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  addGenre = (event) => {
    this.setState({ genres: event ? event.map((x) => x.value) : [] });
  };

  setChapterParameter = (event, i) => {
    let update = [...this.state.chapters];
    update[i][event.target.name] = event.target.value;
    this.setState({ chapters: update });
  };

  addChapter = (event) => {
    event.preventDefault();
    this.setState({
      chapters: [
        ...this.state.chapters,
        { number: this.state.chapters.length + 1, name: "", text: "" },
      ],
    });
  };

  removeChapter = (event, index) => {
    event.preventDefault();
    const chapters = [...this.state.chapters];
    chapters.splice(index, 1);
    this.setState({ chapters:  this.reduceChapterNumber(chapters, index)});
  };

  reduceChapterNumber = (chapters, index) => {
    for (let i = index; i < chapters.length; i++) {
      chapters[i].number = chapters[i].number - 1;
    }
    return chapters;
  }

  deleteTag = (i) => {
    const { tags } = this.state;
    this.setState({
      tags: tags.filter((tag, index) => index !== i),
    });
  };

  addTag = (tag) => {
    this.setState((state) => ({ tags: [...state.tags, tag] }));
  };

  render() {
    return (
      <CreateUpdateComponent
        name={this.state.name}
        description={this.state.description}
        chapters={this.state.chapters}
        stateGenres={this.state.genres}
        setBook={this.addBook}
        setParameter={this.setParameter}
        addGenre={this.addGenre}
        addTag={this.addTag}
        deleteTag={this.deleteTag}
        choosedTags={this.state.tags}
        removeChapter={this.removeChapter}
        setChapterParameter={this.setChapterParameter}
        addChapter={this.addChapter}
        buttonTitle={"Add book"}
        choosedGenres={this.state.genres}
      />
    );
  }
}
