import React from "react";
import axios from "axios";
import { Redirect } from "react-router-dom";
import CreateUpdateComponent from "../components/create_update_book_page/CreateUpdateComponent.js";
import LoadingComponent from "../components/general/LoadingComponent.js";

export default class UpdateBookPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    this.loadBook();
  }

  initialState = {
    name: "",
    description: "",
    chapters: [],
    genres: [],
    tags: [],
    id: "",
    isLoaded: false,
  };

  loadBook = () => {
    axios
      .get(this.props.location.state.links)
      .then((response) => response.data)
      .then((data) => {
        this.setState({
          id: data.id,
          name: data.name,
          description: data.description,
          chapters: data.chapters,
          genres: data.genres,
          tags: this.makeAppriciteTags(data.tags),
          isLoaded: true,
          redirect: "",
        });
      });
  };

  updateBook = (e) => {
    e.preventDefault();
    try {
      axios
        .put(this.props.location.state.links, this.createReturnData(this.state))
        .then((response) => {
          this.setState({
            redirect: `/books/${this.state.id}`,
          });
        });
    } catch (error) {
      console.log(error);
    }
  };

  createReturnData = () => {
    let data = new FormData();
    let book = {
      id: this.state.id,
      name: this.state.name,
      description: this.state.description,
    };
    data.append("book", JSON.stringify(book));
    data.append(
      "chapters",
      JSON.stringify(this.state.chapters ? this.state.chapters : [])
    );
    data.append(
      "genres",
      JSON.stringify(this.state.genres ? this.state.genres : [])
    );
    data.append("tags", JSON.stringify(this.prepareTags()));
    return data;
  };

  prepareTags = () => {
    let loadedTags = [];
    if (this.state.tags) {
      this.state.tags.map((tag) => {
        loadedTags.push({ name: tag.text });
      });
    }
    return loadedTags;
  };

  makeAppriciteTags(tags) {
    let tagsForInput = [];
    if (tags) {
      tags.map((tag) => tagsForInput.push({ id: tag.name, text: tag.name }));
    }
    return tagsForInput;
  }

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
    this.setState((state) => ({
      chapters: [
        ...this.state.chapters,
        { number: this.state.chapters.length + 1, name: "", text: "" },
      ],
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
    console.log(this.state.chapters);
  };

  deleteTag = (i) => {
    const { tags } = this.state;
    this.setState({
      tags: tags.filter((tag, index) => index !== i),
    });
  };

  addTag = (tag) => {
    console.log(tag);
    this.setState((state) => ({ tags: [...state.tags, tag] }));
  };

  render() {
    if (!this.state.isLoaded) {
      return <LoadingComponent />;
    }
    if (this.state.redirect) {
      console.log(this.state.redirect);
      return (
        <Redirect
          to={{
            pathname: this.state.redirect,
            state: { links: this.props.location.state.links },
          }}
        />
      );
    }
    return (
      <CreateUpdateComponent
        name={this.state.name}
        description={this.state.description}
        chapters={this.state.chapters}
        stateGenres={this.state.genres}
        setBook={this.updateBook}
        setParameter={this.setParameter}
        addGenre={this.addGenre}
        addTag={this.addTag}
        deleteTag={this.deleteTag}
        choosedTags={this.state.tags}
        removeChapter={this.removeChapter}
        setChapterParameter={this.setChapterParameter}
        addChapter={this.addChapter}
        buttonTitle={"Update book"}
        choosedGenres={this.state.genres}
      />
    );
  }
}
