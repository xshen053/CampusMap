/*
 * Copyright (C) 2023 Xiaxi Shen. All rights reserved.
 */

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import {edge} from "./EdgeList";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";
// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    // so you can render them here
    edgeList: edge[]  // edges to be drawn
}

interface MapState {}

class Map extends Component<MapProps, MapState> {
    render() {
        return (
            <div id="map">
                <MapContainer
                    center={position}
                    zoom={15}
                    scrollWheelZoom={false}
                >
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    {
                        this.props.edgeList.map((edge)=>
                            <MapLine
                                color = {edge.color}
                                x1 = {edge.x1}
                                x2 = {edge.x2}
                                y1 = {edge.y1}
                                y2 = {edge.y2}
                                key = {edge.text}/>)
                    }
                </MapContainer>
            </div>
        );
    }
}

export default Map;
