/*
 * Copyright (C) 2023 Xiaxi Shen. All rights reserved.
 */

import React, {Component} from 'react';

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import StartForm from "./StartBuilding";
import EndForm from "./EndBuilding";
import Map from "./Map";
import EdgeList, {edge} from "./EdgeList";

interface AppState {
    requestResult: string;
    DefaultPath: string;
    start: string;
    end: string;
    paths: edge[]
}

class App extends Component<{}, AppState> {
    constructor(props: {}) {
        super(props);
        this.state = {
            requestResult: "NO REQUEST RESULT",
            DefaultPath: "http://localhost:4567/find_path?",
            start: "NULL",
            end: "NULL",
            paths: []
        };
    }

    setStart(value: string){
        this.setState({start: value})
    }

    setEnd(value: string){
        this.setState({end: value})
    }

    setPaths(edge: edge[]){
        this.setState({paths: edge});
    }

    makeRequestLong = async () => {  // <- Syntax for making async arrow functions.
        try {
            // Note that we need the "http://" here for browser security reasons.
            // If you remove it, we'll get an error complaining about not having it.
            // start=CSE&&end=BAG
            let path = this.state.DefaultPath + "start=" + this.state.start + "&&end=" + this.state.end
            let response = await fetch(path);
            // Now that we have a response, we should check the status code, make sure it's OK=200:
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }
            let object = await response.json()  // what is this code do?
            // // // Let's just print them all out.
            // print text
            let text = ""
            for (let value of object.path) {
                text = text + value.start.x + " "
                text = text + value.start.y + " "
                text = text + value.end.x + " "
                text = text + value.end.y + " "
                text = text + "blue"
                text = text + "\n"
            }
            // Now that we have the string, let's stick it in state so it'll be displayed
            // to the user.
            text = text.substring(0, text.length - 2);
            this.setState({
                requestResult: text
            });
            alert("You successfully fetch the data from backend! Click Draw Now!")
        } catch (e) {
            // If an error/exception happens (such as if the fetch URL is wrong or the
            // server is offline), then we'll end up here. Probably best to show a message to the
            // user.
            alert("There was an error contacting the server.");
            console.log(e);  // Logging the error can be nice for debugging.
        }
        // Note that the above is a _very_ long-winded way of doing this. The equivalent statement
        // without all the extra unnecessary variables is shown in makeRequest below: this is how
        // you'll usually see it written, without all the intermediate variables.
    };

    render() {
        // Change which callback is assigned to the onClick in the button to try
        // out the different demos.
        return (
            <div className="App">
                <div className="center">
                    <h1 id = "center">Line Mapper!</h1>
                </div>
                <div className={"center"}>
                    <Map edgeList={this.state.paths}></Map>
                </div>
                <div className="center">
                    <StartForm onChange={(start) => {
                        this.setStart(start)
                        console.log("EdgeList onChange", start);
                    }}/>
                    <EndForm onChange={(end) => {
                        this.setEnd(end)
                        console.log("EdgeList onChange", end);
                    }}/>
                    <p>Start Building: {this.state.start}</p>
                    <p>End Building: {this.state.end}</p>
                    <button onClick={this.makeRequestLong}>Calculate Path</button>
                    <EdgeList inputString={this.state.requestResult}
                              onChange={(value) => {
                                  this.setPaths(value)
                                  console.log("EdgeList onChange", value);
                              }}
                    />
                </div>
            </div>
        );
    }

}

export default App;
