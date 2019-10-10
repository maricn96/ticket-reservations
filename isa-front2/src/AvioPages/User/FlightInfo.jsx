import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import GoogleMap from './GoogleMap';


class FlightInfo extends Component {

    state = {
        let: "",
        aviokompanija: "",
        user: "",
        showResBtn: "",

        popust: ""
    }

    componentDidMount() {

        let local = localStorage.getItem("rola");
        if(local === 'KORISNIK')
        {
            this.setState({
                showResBtn: true
            })
        }
        else
        {
            this.setState({
                showResBtn: false
            })
        }

        axios.get('http://localhost:8080/flight/getone/' + this.props.match.params.flightid).then(
            res => {
                console.log(res);
                let popust = 0;
                if(res.data.duzinaPutovanja > 0 && res.data.duzinaPutovanja <= 200)
                    popust = 5;
                else if(res.data.duzinaPutovanja > 200 && res.data.duzinaPutovanja <=500)
                    popust = 15;
                else if(res.data.duzinaPutovanja > 500 && res.data.duzinaPutovanja <= 1000)
                    popust = 25;
                else if(res.data.duzinaPutovanja > 1000 && res.data.duzinaPutovanja < 2000)
                    popust = 40;
                else
                    popust = 50;
                    
                this.setState({
                    popust: popust,
                    let: res.data
                })
            }
        )

        axios.get('http://localhost:8080/flight/getcompanyid/' + this.props.match.params.flightid).then(
            res => {
                console.log(res);
                this.setState({
                    aviokompanija: res.data
                })
            }
        )

        
       

    }

    showCompanyInfo = () => {
        this.props.history.push('/companyinfo/' + this.state.aviokompanija);
    }

    reserveTicket = (idLeta) => {
        this.props.history.push('/reservation/' + idLeta);
    }

    render() {
        let show = this.state.showResBtn;
        const rezbtn = (show) ? (<button className="btn waves-effect waves-light red" id="rezervisi-btn" onClick={() => { this.reserveTicket(this.state.let.idLeta) }}>Rezervacija</button>) : (<div></div>)

        return (
            <div>
                <div className="center container">
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                    <span className="card-title"><b>Broj leta: {this.state.let.brojLeta}</b></span>
                                    <div className="divider white"></div>
                                    <br />
                                    <p>Vreme poletanja: {this.state.let.vremePoletanja}</p>
                                    <p>Vreme sletanja: {this.state.let.vremeSletanja}</p>
                                    <p>Broj slobodnih mesta: {this.state.let.brojMesta - this.state.let.brojOsoba}</p>
                                    <p>Broj presedanja: {this.state.let.brojPresedanja}</p>
                                    <p>Duzina putovanja: {this.state.let.duzinaPutovanja} km</p>
                                    <p>Tip puta: {this.state.let.tipPuta}</p>
                                    <p>Cena karte: {this.state.let.cenaKarte} €</p>
                                    <p>Popust na duzinu puta: {this.state.popust}%</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light green" id="avioinfo-btn" onClick={() => { this.showCompanyInfo() }}>Informacije o aviokompaniji</button>
                                    {rezbtn}
                                </div>
                            </div>
                        </div>
                    </div>
                    <GoogleMap />
                </div>
            </div>
        );
    }
}

export default withRouter(FlightInfo);




