import React from "react";
import { WithContext as ReactTags } from "react-tag-input";
import "./InputTagComponent.css";
import Constants from "../../constants.js";

export default class InputTagComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  suggestions = [];

  componentDidMount = () => {
    const tags = JSON.parse(
      localStorage.getItem(Constants.MAIN_DATA_STORAGE_NAME)
    );
    this.initiateSuggestions(tags);
  };

  initiateSuggestions = (data) => {
    if (data.tags._embedded) {
      data.tags._embedded.tupleBackedMaps.map((tag) =>
        this.suggestions.push({ id: tag.name, text: tag.name })
      );
    }
  };

  render() {
    const { choosedTags, deleteTag, addTag } = this.props;
    return (
      <div>
        <ReactTags
          tags={choosedTags}
          placeholder={"Tags"}
          suggestions={this.suggestions}
          handleDelete={deleteTag}
          handleAddition={addTag}
          allowDragDrop={false}
          allowUnique={true}
          delimiters={this.delimiters}
          autocomplete={true}
        />
      </div>
    );
  }
}
