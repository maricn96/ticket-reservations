import React, { Component, Fragment } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import './user.css';
import Rectangle from 'react-rectangle';


class Reservation extends Component {
    state = {
        toggle: false,
        podaciOLetu: "",
        
        karte: [],
        idKarte: "",
        user: "",
        idLeta: "",

        listaRezervisanihMesta: [],

        sviKorisnici: [], //lista svih povucenih korisnika
        friend: "", //sadrzi samo email korisnika (ili ime ce vidimo)
        listaPrijatelja: [], //ovo saljemo - lista objekata korisnika

        passport: "",
        brojeviPasosa: [],

        showUkupnaCena: false,
        ukupnoRezervisano: ""
    }

    componentDidMount() {
        let letid = this.props.match.params.flightid;
        this.setState({
            idLeta: letid
        })
        axios.get('http://localhost:8080/flight/getone/' + letid).then(res => {
            this.setState({
                podaciOLetu: res.data
            })
        }).catch(error => {
            console.log(error);
        }).then(
            axios.get('http://localhost:8080/ticket/getfree/' + letid).then(res =>
            {
                this.setState({
                    karte: res.data
                })
            })
        )

        //uzmi korisnika
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {
                console.log(res)
                this.setState({
                    user: res.data
                })
            })

            axios.get("http://localhost:8080/korisnik/all")
            .then(res => {
                console.log(res.data)
                this.setState({
                    sviKorisnici: res.data
                })
            })  

    }

    componentDidUpdate(){
        // let letid = this.props.match.params.flightid;
        // axios.get('http://localhost:8080/ticket/getfree/' + letid).then(res =>
        //     {
        //         this.setState({
        //             karte: res.data
        //         })
        //     })
    }

    toggleFriends = () => {
        if (this.state.toggle) {
            this.setState({
                toggle: false
            })
        }
        else {
            this.setState({
                toggle: true
            })
        }
    }

    sendInvitationFieldChange = (e) => {
        this.setState({
            friend: e.target.value
        })
    }

    sendPassportNumberFieldChange = (e) =>
    {
        
        this.setState({
            passport: e.target.value
        })
        
    }

    handleInvitation = (e) => {
        e.preventDefault();

        let listaZaSlanje = this.state.listaPrijatelja;
        let friendEmail = this.state.friend;
        let sviKorisnici = this.state.sviKorisnici;
        console.log(sviKorisnici)
        sviKorisnici.map(user => {
            if(friendEmail === user.email)
            {
                listaZaSlanje.push(user);
            }
        }) 

        
        this.setState({
            listaPrijatelja: listaZaSlanje
        })

    }

    handleInvitationPassport = (e) =>
    {
        e.preventDefault();
        let listaBrojeva = this.state.brojeviPasosa;
        listaBrojeva.push(this.state.passport)

        this.setState({
            brojeviPasosa: listaBrojeva
        })
        console.log(listaBrojeva)
    }

    getTicket = (karta) => {

        let lista = this.state.listaRezervisanihMesta;
        lista.push(karta)

        this.setState({
            listaRezervisanihMesta: lista
        })

        console.log(lista);
    }

    deleteFriendFromList = (id) =>
    {
        let novaLista = this.state.listaPrijatelja.filter(pr =>
            {
                (id !== pr.id)
            })

            this.setState({
                listaPrijatelja: novaLista
            })
    }

    deleteTicketFromList = (id) =>
    {
        let novaLista = this.state.listaRezervisanihMesta.filter(pr =>
            {
                (id !== pr.idKarte)
            })

            this.setState({
                listaRezervisanihMesta: novaLista
            })
    }

    deletePassportFromList = (broj) =>
    {
        let novaLista = this.state.brojeviPasosa.filter(pr =>
            {
                (broj !== pr)
            })

            this.setState({
                brojeviPasosa: novaLista
            })
    }

    confirmMoreTicketsReservation = () =>
    {
        let userid = this.state.user.id;
        let listaKarata = this.state.listaRezervisanihMesta;
        let listaPrijatelja = this.state.listaPrijatelja;
        let brojeviPasosa = this.state.brojeviPasosa;

        console.log("ZA SLANJE: ")
        console.log(listaKarata)
        console.log(listaPrijatelja)
        console.log(brojeviPasosa)

        this.setState({
            ukupnoRezervisano: this.state.listaRezervisanihMesta.length
        })

        axios.post('http://localhost:8080/ticket/reservemore/' + userid, {listaKarata, listaPrijatelja, brojeviPasosa}).then(res => { 
            if(res.data === "REZERVISANE")
            {
                this.setState({
                    showUkupnaCena: true
                })
                alert("Karte uspesno rezervisane");
                this.setState({
                    listaRezervisanihMesta: []
                })
                this.componentDidMount();
            }
            else if(res.data === "NOT_FRIEND_ERR")
                alert("Osoba koju pozivate nije u prijateljima")
            else
                alert("Rezervacija nije uspela")
            this.componentDidUpdate();
        }).catch(error => {
            alert("GRESKA: Neko je upravo rezervisao neku od karata koju vi pokusavate rezervisati")
        })
    }

    render() {
        let i=1;

        const ukupnaCena = (this.state.showUkupnaCena) ? (<div><h3>Ukupna cena rezervacije: {(this.state.ukupnoRezervisano * (this.state.podaciOLetu.cenaKarte - this.state.podaciOLetu.cenaKarte * this.state.karte[0].popust*0.01)).toFixed(2)} €</h3></div>) : (<div></div>)

        const raspored = this.state.karte.length ? (this.state.karte.map(karta => {
            
            return (
                <div id="planelayout" key={karta.idKarte}>
                        {(!karta.brzaRezervacija) ? (<button  className="btn waves-effect waves-light blue" id="ticketbtn" onClick={() => { this.getTicket(karta) }}>RBR: {i++} - {karta.idKarte}</button>) : (
                            <button  className="btn waves-effect waves-light red" id="ticketbtn" >RBR: {i++} - {karta.idKarte}</button>
                        )}
                            
                </div>
            );
        })) : (
                <div></div>
            )

            const listaPrijatelja = this.state.listaPrijatelja.length ? (this.state.listaPrijatelja.map(prijatelj =>
                {
                    return(
                        <div key={prijatelj.id}>{prijatelj.ime} {prijatelj.prezime}, Email adresa: {prijatelj.email}
                        <button className="btn-floating btn-small waves-effect waves-light red" id="destsinfobtn" onClick={() => { this.deleteFriendFromList(prijatelj.id) }}>X</button>
                        </div>
                    )
                })) : (<div>Unesite email prijatelja</div>)

                const listaRezervisanihMesta = this.state.listaRezervisanihMesta.length ? (this.state.listaRezervisanihMesta.map(mesto =>
                    {
                        return(
                            <div key={mesto.idKarte}>{mesto.idKarte}
                            <button className="btn-floating btn-small waves-effect waves-light red" id="destsinfobtn" onClick={() => { this.deleteTicketFromList(mesto.idKarte) }}>X</button>
                            </div>
                        )
                    })) : (<div>Nema odabranih karata za rezervaciju</div>)

                    const listaBrojeva = this.state.brojeviPasosa.length ? (this.state.brojeviPasosa.map(broj =>
                        {
                            return(
                                <div key={broj}>Pasos: {broj}
                                <button className="btn-floating btn-small waves-effect waves-light red" id="destsinfobtn" onClick={() => { this.deletePassportFromList(broj) }}>X</button>
                                </div>
                            )
                        })) : (<div>Nema unesenih brojeva pasosa</div>)

        return (
            <div>

                {raspored}
                {listaRezervisanihMesta}

                <button className="btn waves-effect waves-light green" id="friendsbtn" onClick={() => { this.toggleFriends() }}>Pozovi prijatelje</button><br />
                <button className="btn waves-effect waves-light red" id="reservationbtn" onClick={() => { this.confirmMoreTicketsReservation() }}>Potvrdi rezervaciju</button><br />
                {ukupnaCena}
                {
                    (this.state.toggle) ? (
                        <div>
                            <form onSubmit={(e) => { this.handleInvitation(e) }}> {listaPrijatelja}
                                <div className="container">
                                    <div className="input-field">
                                        <input type="text" id="friend" placeholder="Email prijatelja" className="browser-default" name="friend" onChange={(e) => { this.sendInvitationFieldChange(e) }} /><br />
                                        <button className="btn waves-effect waves-light blue" type="submit">Potvrdi</button><br />
                                    </div>
                                </div>
                            </form>
                            <form onSubmit={(e) => { this.handleInvitationPassport(e) }}>  {listaBrojeva}
                                <div className="container">
                                    <div className="input-field">
                                        <input type="text" id="passport" placeholder="Pasos prijatelja" className="browser-default" name="passport" onChange={(e) => { this.sendPassportNumberFieldChange(e) }} /><br />
                                        <button className="btn waves-effect waves-light blue" type="submit">Potvrdi</button><br />
                                    </div>
                                </div>
                            </form>
                        </div>) : (<p></p>)
                }
                

            </div>
        );
    }
}

export default withRouter(Reservation);
