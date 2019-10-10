import React, { Component } from 'react';
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';

class GoogleMaps extends Component {
    constructor(props) {
        super(props);

        this.state = {
            stores: [{ lat: 45.25167, lng: 19.83694 },
            { latitude: 45.00806, longitude: 19.82222 }]
        }
    }

    displayMarkers = () => {

        return this.state.stores.map((store, index) => {
            return <Marker key={index} id={index} position={{
                lat: store.latitude,
                lng: store.longitude
            }}
                onClick={() => console.log("You clicked me!")} />
        })
    }

    render() {
        const mapStyles = {
            width: '45%',
            height: '45%',
        };
        return (
            <Map
                google={this.props.google}
                zoom={8}
                style={mapStyles}
                initialCenter={{ lat: 45.25167, lng: 19.83694 }}
            >
                {this.displayMarkers()}
            </Map>
        );
    }
}


export default GoogleApiWrapper({
    apiKey: 'AIzaSyDKxNxcmqIKOq8MUAgFGeftyzIJEJsiDHg'
})(GoogleMaps);