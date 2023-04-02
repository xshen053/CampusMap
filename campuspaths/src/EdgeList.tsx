/*
 * Copyright (C) 2023 Xiaxi Shen. All rights reserved.
 */

import React, {Component} from 'react';

export interface edge{
    x1: number;
    x2: number;
    y1: number;
    y2: number;
    color: string;
    text: string;
}

interface EdgeListProps {
    onChange(edges: edge[]): void;
    inputString: string;
}

interface EdgeListValue{
    value: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListValue> {
    constructor(props: EdgeListProps) {
        super(props);
        this.state = {value: "Enter Edges here..."};
    }

    ParseInput(s:string) {
        console.log(s)
        if (s === "") {
            alert("Input cannot be empty!");
            return;
        }
        let strs: string[] = s.split("\n");
        let listOfEdges: edge[] = [];
        for (let i = 0; i < strs.length; i++){
            let temp: string[] = strs[i].split(" ");
            if(temp.length !== 5){
                alert("Input does not match format! Should be 4 coordinates and color of path.");
                return;
            }
            if(typeof temp[temp.length - 1] !== 'string'){
                alert("Invalid type; color of path must be of type string!");
                return;
            }
            for (let j=0; j < temp.length-1; j++){
                let num: number = parseInt(temp[j]);
                if(isNaN(num)){
                    alert("Coordinate must be a number!");
                    return;
                }
                if(num>4000 || num<0){
                    alert("Coordinate is not within the inclusive bound(0-4000)!");
                    return;
                }
            }
            listOfEdges.push({x1:parseInt(temp[0]),y1:parseInt(temp[1]),
                x2:parseInt(temp[2]),y2:parseInt(temp[3]),color:temp[4],text:i.toString()});
        }
        this.props.onChange(listOfEdges);
    }

    render() {
        return (
            <div id="edge-list">
                <button onClick={() => {this.ParseInput(this.props.inputString)}}>Draw</button>
                <button onClick={() => {this.setState({value: "Enter Edges here...",});this.props.onChange([]);}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
