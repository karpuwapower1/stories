import React from 'react';
import SideNav, { Toggle, Nav, NavItem, NavIcon, NavText } from '@trendmicro/react-sidenav';
import '@trendmicro/react-sidenav/dist/react-sidenav.css';
import LeftSideMenuItemComponent from "./LeftSideMenuItemComponent.js"


export default class LeftSideMenu extends React.Component {

    render() {
        return (
            <SideNav 
            onSelect={(selected) => {
            }}
            style = {{backgroundColor: "#C0C0C0"}}
        >
            <SideNav.Toggle />
            <SideNav.Nav defaultSelected="home">
                {this.props.chapters.map( chapter => {             
                 return (
                        <LeftSideMenuItemComponent key={chapter.id} chapter={chapter}/>
                    );
                })}
            </SideNav.Nav>
        </SideNav>
        )
    }

}