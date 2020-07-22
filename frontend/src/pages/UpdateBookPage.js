import React from "react";
import axios from "axios";
import {Redirect} from "react-router-dom";
import CreateUpdateComponent from "../components/create_update_book_page/CreateUpdateComponent.js";
import LoadingComponent from "../components/general/LoadingComponent.js";

export default class UpdateBookPage extends React.Component {
  constructor(props) {
      console.log("update book page");
    super(props);
    this.state = this.initialState;
  }

  initialState = {
    name: "",
    description: "",
    chapters: [],
    genres: [],
    tags: [],
    id:"",
    isLoaded: false
  };

  componentDidMount() {
      console.log(this.props.location.state.links);
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
          tags: data.tags,
          isLoaded: true,
          redirect: "",
        });
      });
  }

  updateBook = (e) => {
    e.preventDefault();
    try {
      axios.put(this.props.location.state.links, this.createReturnData()).then(response => this.setState({
        redirect: `/books/${this.state.id}`
      }));
    } catch (error) {
      console.log(error);
    }
  };

  createReturnData = () => {
    const data = new FormData();
    const book = { id: this.state.id, name: this.state.name, description: this.state.description };
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
    if (!this.state.isLoaded) {
        return <LoadingComponent />;
      }
      if (this.state.redirect) {
       return  <Redirect to={{pathname: this.state.redirect, state: 
          {links: this.props.location.state.links}}}/>
      }
    let book = this.state.book;
    return (
      <CreateUpdateComponent
        name={this.state.name}
        description={this.state.description}
        chapters={this.state.chapters}
        setBook={this.updateBook}
        setParameter={this.setParameter}
        addGenre={this.addGenre}
        removeChapter={this.removeChapter}
        setChapterParameter={this.setChapterParameter}
        addChapter={this.addChapter}
      />
    );
  }
}