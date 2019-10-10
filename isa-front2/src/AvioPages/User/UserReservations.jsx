import React, { Component } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';

class UserReservations extends Component {
    state = {
        user: "",
        userTickets: [],
        showRateFormFlag: false,
        ocena: ""
    }

    componentDidMount() {
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {

                this.setState({
                    user: res.data
                })

                axios.get('http://localhost:8080/korisnik/getallreservations/' + res.data.id).then(res => {
                    this.setState({
                        userTickets: res.data
                    })
                    console.log(res);
                })
            })
    }

    deleteReservation = (idKarte) => {
        let userid = this.state.user.id;
        axios.post('http://localhost:8080/ticket/deletereservation/' + userid + '/' + idKarte).then(res => {
            // alert(res.data)
            if (res.data) {
                alert("Rezervacija uspesno otkazana")
                this.componentDidMount();
            }
            else {
                alert("Vreme za otkazivanje rezervacije je isteklo")
            }

        }).catch(err => {
            alert("Vreme za otkazivanje rezervacije je isteklo")
        })
    }

    rateFlight = (e, idKarte) => {
        e.preventDefault();
        let userid = this.state.user.id;
        let ocena = this.state.ocena;

        console.log(ocena)
        axios.put('http://localhost:8080/korisnik/rateflight/' + userid + '/' + idKarte + '/' + ocena).then(res => {
            // alert(res.data)
            if (res.data === "SUCCESS") {
                alert("Ocena zabelezena")
            }
            else {
                alert("Let jos traje ili nije zapoceo")
            }

        }).catch(err => {
            alert("Let jos traje ili nije zapoceo")
        })
    }

    changeInputField = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    showRateForm = () => {
        if(this.state.showRateFormFlag) {
            this.setState({
                showRateFormFlag: false
            })
        }
        else {
            this.setState({
                showRateFormFlag: true
            })
        }
    }

    redirectToHotel = (karta) =>
    {
        sessionStorage.setItem("vremePoletanja", karta.let.vremePoletanja.substring(0, 10));
        sessionStorage.setItem("vremeSletanja", karta.let.vremeSletanja.substring(0, 10));
        sessionStorage.setItem("flag", "1");
        sessionStorage.setItem("ticketid", karta.idKarte);
        this.props.history.push('/hotels');
    } 

    render() {
        const reservationList = this.state.userTickets.length ? (this.state.userTickets.map(ticket => {
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
                                        <p>Vreme rezervisanja: {ticket.vremeRezervisanja}</p>
                                        <p>Cena rezervacije: {(ticket.cena - ticket.cena * ticket.popust*0.01).toFixed(2)} €</p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                        <button className="btn waves-effect waves-light red" id="deletebtn" onClick={() => { this.deleteReservation(ticket.idKarte) }}>Ponisti</button>
                                        <button className="btn waves-effect waves-light blue" id="ratebtn" onClick={() => { this.showRateForm() }}>Oceni</button>
                                        <button className="btn waves-effect waves-light green" id="redirectbtn" onClick={() => { this.redirectToHotel(ticket) }}>Rezervisi smestaj</button>
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
                <h4>Nema rezervacija</h4>
            )

        return (
            <div>
                {reservationList}
            </div>
        )

    }
}

export default withRouter(UserReservations);
