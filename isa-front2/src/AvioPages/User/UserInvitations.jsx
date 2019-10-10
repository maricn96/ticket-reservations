import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';


class UserInvitations extends Component {
    state = {
        userInvitations: [], //spisak svih karata
        user: ""
    }

    componentDidMount() {
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {
                this.setState({
                    user: res.data
                })
                let userid = res.data.id
                console.log("USERID : " + userid)
                axios.get('http://localhost:8080/korisnik/getallinvitations/' + userid).then(res => {
                    this.setState({
                        userInvitations: res.data
                    })
                    console.log(res.data);
                })
            })

    }

    acceptRequest = (ticketid) => {
        axios.post('http://localhost:8080/korisnik/acceptinvrequest/' + ticketid).then(res => {
            console.log(res.data);
            if (res.data === "SUCCESS") {
                alert("Pozivnica prihvacena")
                this.componentDidMount();
            }
            else {
                alert("Let je vec poceo")
            }

        }).catch(err => {
            console.log(err)
            alert("GRESKA")   //baci exc al odradi posao
            this.componentDidMount();
        })
    }

    refuseRequest = (ticketid) => {
        axios.post('http://localhost:8080/korisnik/refuseinvrequest/' + ticketid).then(res => {
            console.log(res.data);
            if (res.data === "SUCCESS") {
                alert("Pozivnica uspesno odbijena")
                this.componentDidMount();
            }
            else {
                alert("Pozivnica nije odbijena iz nepoznatih razloga")
            }

        }).catch(err => {
            console.log(err)
            alert("GRESKA")
        })
    }



    render() {
        const allInvitations = this.state.userInvitations.length ? (this.state.userInvitations.map(inv => {
            return (
                <div key={inv.idKarte}>
                    <div className="center container">
                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Korisnik koji salje pozivnicu: {inv.korisnikKojiSaljePozivnicu.ime} {inv.korisnikKojiSaljePozivnicu.prezime}</b></span>
                                        <div className="divider white"></div>
                                        <br />
                                        <p>Broj leta: {inv.let.brojLeta}</p>
                                        <p>Mesto poletanja: {inv.let.destinacijaPoletanja.naziv}</p>
                                        <p>Mesto sletanja: {inv.let.destinacijaSletanja.naziv}</p>
                                        <p>Vreme poletanja: {inv.let.vremePoletanja}</p>
                                        <p>Vreme sletanja: {inv.let.vremeSletanja}</p>
                                        <p>Duzina putovanja: {inv.let.duzinaPutovanja} km</p>
                                        <p>Mesta presedanja:</p> {inv.let.destinacijePresedanja.map(dest =>
                                            {
                                                <p>{dest.naziv}</p>
                                            })}
                                        <p>Tip puta: {inv.let.tipPuta}</p>
                                        <p>Cena karte: {inv.let.cenaKarte}</p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                        <button className="btn waves-effect waves-light green" id="acceptbtn" onClick={() => { this.acceptRequest(inv.idKarte) }}>Prihvati</button>
                                        <button className="btn waves-effect waves-light red" id="refusebtn" onClick={() => { this.refuseRequest(inv.idKarte) }}>Odbij</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
                <h4>Nema novih pozivnica za putovanje</h4>
            )

        return (
            <div>
                {allInvitations}
            </div>
        )

    }
}

export default withRouter(UserInvitations);