import React, { Component } from 'react';
import axios from 'axios';

class Other extends Component {

    state = {
        user: "",
        letovi: [],
        karte: [],
        prtljag: [],
        opis: "",
        tezina: "",

        popust: "",
        karta: "",

        toggleTickets: false
    }

    componentDidMount() {
        var token = localStorage.getItem('jwtToken');
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {
                this.setState({
                    user: res.data
                })

                axios.get('http://localhost:8080/flight/getall/' + res.data.zaduzenZaId, { headers: { Authorization: `Bearer ${token}` } }).then(res => {
                    this.setState({
                        letovi: res.data
                    })
                })

                axios.get('http://localhost:8080/luggage/getall').then(res => {
                    this.setState({
                        prtljag: res.data
                    })
                })
                
            })
    }

    toggleTickets = (idLeta) =>
    {
        if(this.state.toggleTickets)
        {
            this.setState({
                toggleTickets: false
            })
        }
        else
        {
            this.setState({
                toggleTickets: true
            })
            axios.get('http://localhost:8080/ticket/getfree/' + idLeta).then(res =>
        {
            this.setState({
                karte: res.data
            })
        })
        }

        
    }

    changeField = (e) =>
    {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    setToExpressTicket = (karta) =>
    {
        this.setState({
            karta: karta
        })
    }

    handleDiscountSubmit = (e) =>
    {
        e.preventDefault();
        let popust = this.state.popust;
        let karta = this.state.karta;

        axios.put('http://localhost:8080/ticket/settoexpress/' + karta.idKarte + '/' + popust).then(res => {
                    if(res.data)
                        alert("Karta uspesno postavljena na brzu rezervaciju");
                })
    }

    deleteLuggage = (idPrtljaga) =>
    {
        axios.delete('http://localhost:8080/luggage/delete/' + idPrtljaga).then(res => {
                    alert("Prtljag obrisan")
                    this.componentDidMount();
                })
    }

    handleLuggageSubmit = (e) =>
    {
        e.preventDefault();
        let opis = this.state.opis;
        let tezina = this.state.tezina;

        axios.post('http://localhost:8080/luggage/add/', {opis, tezina}).then(res => {
                    alert("Novi prtljag uspesno definisan")
                    this.componentDidMount();
                })
    }

    render() {

        const letovi = this.state.letovi.length ? (this.state.letovi.map(flight => {
            return (
                <div key={flight.idLeta}>
                    <div className="center container">
                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Broj leta: {flight.brojLeta}</b></span>
                                        <div className="divider white"></div>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                    <button  className="btn waves-effect waves-light red" id="ticketbtn" onClick={() => { this.toggleTickets(flight.idLeta) }} >Prikazi karte</button>
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

            let i=0;
            const raspored = this.state.toggleTickets ? (this.state.karte.map(karta => {
            
                return (
                    <div id="planelayout" key={karta.idKarte}>
                            {(!karta.brzaRezervacija) ? (<div><button  className="btn waves-effect waves-light blue" id="ticketbtn" onClick={() => { this.setToExpressTicket(karta) }}>RBR: {i++} - {karta.idKarte}</button>
                            </div>) : (
                                <button  className="btn waves-effect waves-light red" id="ticketbtn" >RBR: {i++} - {karta.idKarte}</button>
                            )}
                                
                    </div>
                );
            })) : (
                    <div></div>
                )
    
        
                const prtljag = this.state.prtljag.length ? (this.state.prtljag.map(prt => {
                    return (
                        <div key={prt.idPrtljaga}>
                            <div className="center container">
                                <div className="row">
                                    <div className="col s12 m12">
                                        <div className="card grey darken-2 card-panel hoverable">
                                            <div className="card-content white-text left-align">
                                                <span className="card-title"><b>Kratak opis: {prt.opis}</b></span>
                                                <span className="card-title"><b>Masa: {prt.tezina} kg</b></span>
                                                <div className="divider white"></div>
                                            </div>
                                            <div className="divider white"></div>
                                            <div className="card-action">
                                            <button  className="btn waves-effect waves-light red" id="ticketbtn" onClick={() => { this.deleteLuggage(prt.idPrtljaga) }} >Obrisi</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    )
                })) : (
                        <h4>Nema definisanog prtljaga za aviokompaniju</h4>
                    )

        return (
            <div>
                <h3>Uredjivanje brzih rezervacija, izaberi let za definisanje karata sa popustom</h3>
                {letovi}
                {raspored}

                <form onSubmit={(e) => { this.handleDiscountSubmit(e) }}> 
                                <div className="container">
                                    <div className="input-field">
                                        <input type="text" id="popust" placeholder="Unesi popust" className="browser-default" name="popust" onChange={(e) => { this.changeField(e) }} /><br />
                                        <input className="btn waves-effect waves-light blue" type="submit" value="Postavi"/>
                                    </div>
                                </div>
                            </form>

                <h3>Definisanje tipova prtljaga za let</h3>
                <h4>Dodaj novi:</h4>
                <form onSubmit={(e) => { this.handleLuggageSubmit(e) }}> 
                                <div className="container">
                                    <div className="input-field">
                                        <input type="text" id="opis" placeholder="Opis" className="browser-default" name="opis" onChange={(e) => { this.changeField(e) }} /><br />
                                        <input type="text" id="tezina" placeholder="Masa [kg]" className="browser-default" name="tezina" onChange={(e) => { this.changeField(e) }} /><br />
                                        <input className="btn waves-effect waves-light green" type="submit" value="Dodaj"/>
                                    </div>
                                </div>
                            </form>
                {prtljag}
            </div>
        );
    }
}

export default Other;
