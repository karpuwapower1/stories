import axios from "axios";


export default function addBook(event, link, state) {
  event.preventDefault();
    try {
      axios.post(link, createReturnData(state));
      this.setState(this.initialState);
    } catch (error) {
      console.log(error);
    }
  };

function createReturnData(state) {
    const data = new FormData();
    const book = { name: state.name, 
        description: state.description };
    data.append("book", JSON.stringify(book));
    data.append("chapters", JSON.stringify(state.chapters));
    data.append("genres", JSON.stringify(state.genres));
    data.append("tags", JSON.stringify(prepareTags(state.tags)));
    return data;
  };

  function prepareTags(state) {
    let tags = [];
    if (state.tags) {
      state.tags.map(tag => {
        tags.push({name: tag.text})
      })
    }
    return tags;
  }