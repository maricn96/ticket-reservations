import React, { Component } from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";
import { withRouter} from 'react-router-dom';
import './user.css';

class AllFlights extends Component {

    state = {
        letovi: [],
        showDestPolInfo: false,
        showDestSleInfo: false
    }

    componentDidMount() {

        axios('http://localhost:8080/flight/getall').then(
            res => {
                this.setState({
                    letovi: res.data
                })
            }
        )
    }

    showFlightInfo = (idLeta) =>
    {
        this.props.history.push('/flinfo/' + idLeta);
    }

    showDestinacijaPoletanjaInfo = () =>
    {
        if(this.state.showDestPolInfo)
        {
            this.setState({
                showDestPolInfo: false
            })
        }
        else
        {
            this.setState({
                showDestPolInfo: true
            })
        }
    }

    showDestinacijaSletanjaInfo = () =>
    {
        if(this.state.showDestSleInfo)
        {
            this.setState({
                showDestSleInfo: false
            })
        }
        else
        {
            this.setState({
                showDestSleInfo: true
            })
        }
    }

    render() {
        const flightsList = this.state.letovi.length ? (this.state.letovi.map(flight => {
            return (
                <div key={flight.idLeta}>

                    <div className="center container" >

                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Broj leta: {flight.brojLeta}</b></span>
                                        <div className="divider white"></div>
                                        <br />
                                        <p>Destinacija poletanja: {flight.destinacijaPoletanja.naziv}</p> <button className="btn-floating btn-small waves-effect waves-light blue" id="destsinfobtn" onClick={() => { this.showDestinacijaPoletanjaInfo() }}>INFO</button>
                                        {(this.state.showDestPolInfo) ? (<div>{flight.destinacijaPoletanja.informacije}</div>) : (<div></div>)}<br />
                                        
                                        <p>Destinacija sletanja: {flight.destinacijaSletanja.naziv}</p> <button className="btn-floating btn-small waves-effect waves-light blue" id="destsinfobtn" onClick={() => { this.showDestinacijaSletanjaInfo() }}>INFO</button> 
                                        {(this.state.showDestSleInfo) ? (<div>{flight.destinacijaSletanja.informacije}</div>) : (<div></div>)}<br />
                                       
                                        
                                        <p>Vreme poletanja: {flight.vremePoletanja}</p><br />
                                        <p>Vreme sletanja: {flight.vremeSletanja}</p><br />
                                        <p>Broj slobodnih mesta: {flight.brojMesta - flight.brojOsoba}</p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                        <button className="btn waves-effect waves-light red" id="letinfo-btn" onClick={() => { this.showFlightInfo(flight.idLeta) }}>Informacije o letu</button>
                                        </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
                <h3>Nema letova</h3>
            )

        return (
            <div>
                <br />
                <Link to="/flsearch"><button className="btn green center lighten-1 z-depth-0">Pretrazi letove</button></Link><br /><br />
                <Link to="/expticket"><button className="btn orange center lighten-1 z-depth-0">Brza rezervacija</button></Link><br /><br />
                <Link to="/allcompanieslist"><button className="btn blue center lighten-1 z-depth-0">Spisak svih aviokompanija</button></Link>
                <div className="center container">
                    <h3 className="left-align container">Spisak letova:</h3>
                </div>
                {flightsList}
            </div>
        )

    }
}

export default withRouter(AllFlights);
