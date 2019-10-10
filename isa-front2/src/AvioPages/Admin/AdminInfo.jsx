import React, { Component } from 'react';
import axios from 'axios'

class AdminInfo extends Component {
    state = {
        user: "",
        email: "",
        lozinka: "",
        ime: "",
        prezime: "",
        grad: "",
        telefon: ""
    }

    componentDidMount() {
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {
                this.setState({
                    user: res.data,
                    email: res.data.email,
                    ime: res.data.ime,
                    prezime: res.data.prezime,
                    grad: res.data.grad,
                    telefon: res.data.telefon,
                    prijateljiKorisnika: res.data.prijateljiKorisnika,
                    zahteviKorisnika: res.data.zahteviKorisnika
                })
            })
    }

    changeInputField = (e) =>
    {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleUserInformationsSubmit = (e) => {
        e.preventDefault();

        let ime = this.state.ime;
        let prezime = this.state.prezime;
        let email = this.state.email;
        let lozinka = this.state.lozinka;
        let grad = this.state.grad;
        let telefon = this.state.telefon;

        axios.put("http://localhost:8080/korisnik/" + this.state.user.id, {ime, prezime, email, lozinka, grad, telefon, aktiviran: true, rola: this.state.user.rola, 
            zaduzenZaId: this.state.user.zaduzenZaId, prviPutLogovan: false, brojPasosa: this.state.user.brojPasosa,       
            prijateljiKorisnika: this.state.user.prijateljiKorisnika, zahteviKorisnika: this.state.user.zahteviKorisnika  
    }, {headers: {
            'Content-Type': 'application/json'
          },})
            .then(res => {
               alert("Podaci uspesno azurirani")
                }).catch(error =>
                    {
                        alert("Greska u azuriranju podataka!")
                    })
    }



    render() {
        return (
            <div>
                <form className="white" onSubmit={(e) => { this.handleUserInformationsSubmit(e) }}>
                    <h2 className="red-text lighten-1 center">Informacije o korisniku</h2>
                    <div className="container">

                        <label htmlFor="email">Email</label>
                        <div className="input-field">
                            <input type="text" id="email" defaultValue={this.state.email} className="browser-default" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="lozinka">Lozinka</label>
                        <div className="input-field">
                            <input type="password" id="lozinka" className="browser-default" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="ime">Ime</label>
                        <div className="input-field">
                            <input type="text" id="ime" defaultValue={this.state.ime} className="browser-default" name="ime" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="prezime">Prezime</label>
                        <div className="input-field">
                            <input type="text" id="prezime" defaultValue={this.state.prezime} className="browser-default" name="prezime" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="grad">Grad</label>
                        <div className="input-field">
                            <input type="text" id="grad" defaultValue={this.state.grad} className="browser-default" name="grad" onChange={(e) => { this.changeInputField(e) }} />
                        </div>

                        <label htmlFor="telefon">Telefon</label>
                        <div className="input-field">
                            <input type="text" id="telefon" defaultValue={this.state.telefon} className="browser-default" name="telefon" onChange={(e) => { this.changeInputField(e) }} />
                        </div>
                        <div className="input-field">
                            <input type="submit" value="Sacuvaj" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}

export default AdminInfo;
