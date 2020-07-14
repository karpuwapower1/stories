import React from "react";
import  {
  NavItem,
  NavIcon,
  NavText,
} from "@trendmicro/react-sidenav";
import "@trendmicro/react-sidenav/dist/react-sidenav.css";

export default class LeftSideMenuItemComponent extends React.Component {
  render() {
      const chapter = this.props.chapter;
    return (
      <div>
        <NavItem eventKey={this.props.chapter.id}>
          <NavIcon>
            <i className="fa fa-fw fa-home" />
          </NavIcon>
          <NavText>
            <a href={"#" + chapter.number} style= {{color: "#000"}}>
              <p>{chapter.number} {chapter.name}</p>
            </a>
          </NavText>
        </NavItem>
      </div>
    );
  }
}