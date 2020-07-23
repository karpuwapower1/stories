import React from "react";
import CreateUpdateComponent from "../components/create_update_book_page/CreateUpdateComponent.js";
import creationBookService from "../Services/book/AddBookService.js";

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
    creationBookService.addBook(e, this.props.location.state.link, this.state);
  }

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
      chapters: [...this.state.chapters, { number: this.state.chapters.length + 1, name: "", text: "" }],
    }));
  };

  removeChapter = (event, index) => {
    event.preventDefault();
    const chapters = [...this.state.chapters];
    chapters.splice(index, 1);
    for (let i = index; i < chapters.length; i++) {
        chapters[i].number = chapters[i].number - 1;
    }
    this.setState({ chapters });
  };

  deleteTag = (i) => {
    const { tags } = this.state;
    this.setState({
     tags: tags.filter((tag, index) => index !== i),
    });
}

addTag = (tag) => {
    this.setState(state => ({ tags: [...state.tags, tag] }));
}

  render() {
    return (
    <CreateUpdateComponent
      name={this.state.name}
      description={this.state.description}
      chapters={this.state.chapters}
      stateGenres={this.state.genres}
      setBook={this.addBook}
      setParameter = {this.setParameter}
      addGenre={this.addGenre}
      addTag={this.addTag}
      deleteTag={this.deleteTag}
      choosedTags={this.state.tags}
      removeChapter= {this.removeChapter}
      setChapterParameter={this.setChapterParameter}
      addChapter = {this.addChapter}
      buttonTitle={"Add book"}
      choosedGenres={this.state.genres}
    />               
    );
  }
}