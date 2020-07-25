import React from "react";

export default class ErrorPage extends React.Component {

    constructor(props) {
        super(props);

        console.log(props);
    }


    render() {
        return (
            <h1>Error page</h1>
        )
    }
}