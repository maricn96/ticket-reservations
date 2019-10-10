import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

class ExpressTicket extends Component {
    state = {
        user: "",
        tickets: "",

        showResBtn: false
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

        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
        .then(res => {

            this.setState({
                user: res.data
            })

            axios.get('http://localhost:8080/ticket/getexpress').then(res => {
                this.setState({
                    tickets: res.data
                })
            })
        })
      }

      reserveTicket = (idKarte) =>{

        let userid = this.state.user.id;
        axios.post('http://localhost:8080/ticket/expressreservation/' + userid + '/' + idKarte).then(res => {
                if(res.data)
                {
                    alert("Karta uspesno rezervisana")
                    this.componentDidMount();
                }
                    
            }).catch(error => {
                alert("Izabrana karta je upravo rezervisana od strane drugog korisnika")
            })
      }


    render() {
        let show = this.state.showResBtn;
        const reservationList = this.state.tickets.length ? (this.state.tickets.map(ticket => {
            return (
                <div key={ticket.idKarte}>
                    <div className="center container">
                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Broj leta: {ticket.let.brojLeta}</b></span>
                                        <div className="divider white"></div>
                                        <br />
                                        <p>Destinacija poletanja: {ticket.let.destinacijaPoletanja.naziv}</p>
                                        <p>Destinacija sletanja: {ticket.let.destinacijaSletanja.naziv}</p>
                                        <p>Vreme poletanja: {ticket.let.vremePoletanja}</p>
                                        <p>Vreme sletanja: {ticket.let.vremeSletanja}</p>
                                        <p>Cena: {ticket.cena} €</p>
                                        <p>Popust: {ticket.popust} %</p>
                                        <p><strong>Konacna cena: {ticket.cena - ticket.cena*ticket.popust/100} €</strong></p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                    {(show) ? (<button className="btn waves-effect waves-light blue" id="reservationbtn" onClick={() => { this.reserveTicket(ticket.idKarte) }}>REZERVISI</button>) : (<div></div>)}
                                        {
                                            (this.state.showRateFormFlag) ? (
                                                <div>
                                                    <form onSubmit={(e) => { this.rateFlight(e, ticket.idKarte) }}>
                                                        <div >
                                                            <label htmlFor="ocena">Ocena</label>
                                                            <div className="input-field">
                                                                <input type="text" id="ocena" defaultValue={ticket.ocena} className="browser-default" onChange={(e) => { this.changeInputField(e) }} />
                                                            </div>
                                                            <div className="input-field">
                                                                <input type="submit" value="Sacuvaj" className="btn green lighten-1 z-depth-0" /> <br /> <br />
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>) : (<div></div>)
                                        }

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
                <h4>Nema karata za brzu rezervaciju</h4>
            )

        return (
            <div><br /> <Link to="/companies"><button className="btn red center lighten-1 z-depth-0">Nazad</button></Link>
            <h3>Brza rezervacija</h3>
                {reservationList}
            </div>
        );
    }
}

export default ExpressTicket;
