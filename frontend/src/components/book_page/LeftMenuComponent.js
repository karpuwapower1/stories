import React from "react";
import SideNav, { Toggle } from "@trendmicro/react-sidenav";
import "@trendmicro/react-sidenav/dist/react-sidenav.css";
import LeftSideMenuItemComponent from "./LeftSideMenuItemComponent.js";

export default class LeftSideMenu extends React.Component {
  styles = {
    top: "52px",
    position: "absolute",
    position: "fixed",
    whiteSpace: "nowrap",
    overflow: "hidden",
    backgroundColor: "#f8f9fa",
  };

  render() {
    return (
      <SideNav
        onSelect={(selected) => {
          this.styles = {};
        }}
        style={this.styles}
      >
        <SideNav.Toggle />
        <SideNav.Nav defaultSelected="home">
          {this.props.chapters.map((chapter) => {
            return (
              <LeftSideMenuItemComponent key={chapter.id} chapter={chapter} />
            );
          })}
        </SideNav.Nav>
      </SideNav>
    );
  }
}
