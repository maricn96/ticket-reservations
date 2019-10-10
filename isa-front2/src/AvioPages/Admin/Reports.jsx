import React, { Component } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import {
    VictoryBar,
    VictoryChart
} from "victory";

class Reports extends Component {

    state = {
        prosecnaOcenaKompanije: [],
        letoviKompanije: [],
        user: "",

        
        prodateKartePoDanima: [
            
        ],

        prodateKartePoNedeljama: [
            
        ],

        prodateKartePoMesecima: [
            
        ],

        date1: "",
        date2: "",
        prihodZaDatum: ""
    }

    componentDidMount() {
        var token = localStorage.getItem('jwtToken');
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {
                this.setState({
                    user: res.data
                })

                axios.get('http://localhost:8080/aviocompany/getavgrating/' + res.data.zaduzenZaId).then(res => {
                    this.setState({
                        prosecnaOcenaKompanije: res.data
                    })
                })

                //vrati sve letove za odredjenu kompaniju
                axios.get('http://localhost:8080/flight/getall/' + res.data.zaduzenZaId, { headers: { Authorization: `Bearer ${token}` } }).then(res => {
                    this.setState({
                        letoviKompanije: res.data
                    })
                })

                //ZA GRAFIKE
                axios.get('http://localhost:8080/aviocompany/getsoldcardsbyday/' + res.data.zaduzenZaId).then(res => {
                    this.setState({
                        prodateKartePoDanima: res.data
                    })
                })

                axios.get('http://localhost:8080/aviocompany/getsoldcardsbyweek/' + res.data.zaduzenZaId).then(res => {
                    this.setState({
                        prodateKartePoNedeljama: res.data
                    })
                })

                axios.get('http://localhost:8080/aviocompany/getsoldcardsbymonth/' + res.data.zaduzenZaId).then(res => {
                    this.setState({
                        prodateKartePoMesecima: res.data
                    })
                })

            })
    }

    changeDatum = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleDateSubmit = (e) => {
        e.preventDefault();

        let datumOd = this.state.date1
        let datumDo = this.state.date2
        let id = this.state.user.zaduzenZaId

        axios.get('http://localhost:8080/aviocompany/getincomebydate/' + id + '/' + datumOd + '/' + datumDo).then(res => {
            this.setState({
                prihodZaDatum: res.data
            })
            console.log(res.data)
        })

    }

    render() {

        const letovi = this.state.letoviKompanije.length ? (this.state.letoviKompanije.map(flight => {
            return (
                <div key={flight.idLeta}>
                    <div className="center container">
                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Broj leta: {flight.brojLeta}</b></span>
                                        <span className="card-title"><b>Prosecna ocena: {flight.prosecnaOcena}</b></span>
                                        <div className="divider white"></div>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })) : (
                <h4>Nema letova za datu aviokompaniju</h4>
            )

        const prihodZaDatum = this.state.prihodZaDatum ? (<div>{this.state.prihodZaDatum} €</div>) : (<div>Nema prihoda</div>)

        const prodateKartePoDanima = this.state.prodateKartePoDanima.length ? (<VictoryChart>
            <VictoryBar
                data={[
                    { nivo: this.state.prodateKartePoDanima[0].datum, broj_karata: this.state.prodateKartePoDanima[0].brojKarata },
                    { nivo: this.state.prodateKartePoDanima[1].datum, broj_karata: this.state.prodateKartePoDanima[1].brojKarata },
                    { nivo: this.state.prodateKartePoDanima[2].datum, broj_karata: this.state.prodateKartePoDanima[2].brojKarata },
                    { nivo: this.state.prodateKartePoDanima[3].datum, broj_karata: this.state.prodateKartePoDanima[3].brojKarata },
                    { nivo: this.state.prodateKartePoDanima[4].datum, broj_karata: this.state.prodateKartePoDanima[4].brojKarata },
                ]}
                x="nivo"
                y="broj_karata"
            />
        </VictoryChart>) : (<div>Nema podataka</div>)

        const prodateKartePoNedeljama = this.state.prodateKartePoNedeljama.length ? (<VictoryChart>
            <VictoryBar
                data={[
                    { nivo: this.state.prodateKartePoNedeljama[0].datum, broj_karata: this.state.prodateKartePoNedeljama[0].brojKarata },
                    { nivo: this.state.prodateKartePoNedeljama[1].datum, broj_karata: this.state.prodateKartePoNedeljama[1].brojKarata },
                    { nivo: this.state.prodateKartePoNedeljama[2].datum, broj_karata: this.state.prodateKartePoNedeljama[2].brojKarata },
                    { nivo: this.state.prodateKartePoNedeljama[3].datum, broj_karata: this.state.prodateKartePoNedeljama[3].brojKarata },
                    { nivo: this.state.prodateKartePoNedeljama[4].datum, broj_karata: this.state.prodateKartePoNedeljama[4].brojKarata },
                ]}
                x="nivo"
                y="broj_karata"
            />
        </VictoryChart>) : (<div>Nema podataka</div>)

        const prodateKartePoMesecima = this.state.prodateKartePoMesecima.length ? (<VictoryChart>
            <VictoryBar
                data={[
                    { nivo: this.state.prodateKartePoMesecima[0].datum, broj_karata: this.state.prodateKartePoMesecima[0].brojKarata },
                    { nivo: this.state.prodateKartePoMesecima[1].datum, broj_karata: this.state.prodateKartePoMesecima[1].brojKarata },
                    { nivo: this.state.prodateKartePoMesecima[2].datum, broj_karata: this.state.prodateKartePoMesecima[2].brojKarata },
                    { nivo: this.state.prodateKartePoMesecima[3].datum, broj_karata: this.state.prodateKartePoMesecima[3].brojKarata },
                    { nivo: this.state.prodateKartePoMesecima[4].datum, broj_karata: this.state.prodateKartePoMesecima[4].brojKarata },
                ]}
                x="nivo"
                y="broj_karata"
            />
        </VictoryChart>) : (<div>Nema podataka</div>)

        return (
            <div>
                <h3>Prosecna ocena aviokompanije: {this.state.prosecnaOcenaKompanije}</h3>
                <h3>Prosecne ocene pojedinacnih letova:</h3>
                {letovi}

                <h3>Prodate karte na dnevnom nivou</h3>
                {prodateKartePoDanima}

                <h3>Prodate karte na nedeljnom nivou</h3>
                {prodateKartePoNedeljama}

                <h3>Prodate karte na mesecnom nivou</h3>
                {prodateKartePoMesecima}

                <h3>Prihod aviokompanije za odredjeni period</h3><h2>{prihodZaDatum} </h2>
                <form className="white" onSubmit={(e) => { this.handleDateSubmit(e) }}>
                    <label htmlFor="date1">Datum OD:</label>
                    <div className="input-field">
                        <input type="date" className="datepicker" id="date1" onChange={(e) => { this.changeDatum(e) }} />
                    </div>
                    <label htmlFor="date2">Datum DO:</label>
                    <div className="input-field">
                        <input type="date" className="datepicker" id="date2" onChange={(e) => { this.changeDatum(e) }} />
                    </div>
                    <div className="input-field">
                        <input type="submit" value="Unesi" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                    </div>
                </form>

            </div>
        );
    }
}

export default withRouter(Reports);
