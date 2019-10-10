import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
class AdminProfile extends Component{

    state={
        korisnik:"",
        email:"",
        lozinka:"",
        ime:"",
        prezime:"",
        grad:"",
        telefon:""
    }

    componentDidMount() {
        var a = "";
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
        .then(res=>{
            this.setState({
                korisnik: res.data,
                email: res.data.email,
                lozinka: res.data.lozinka,
                ime: res.data.ime,
                prezime: res.data.prezime,
                telefon: res.data.telefon,
                grad: res.data.grad
            })
        })
    }

    handleChange = (e) => { //za inpute
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit = (e) => {
        var token = localStorage.getItem('jwtToken');
        e.preventDefault();
        if(this.state.email!=="" && this.state.lozinka!=="" && this.state.ime!=="" && this.state.prezime!=="" && this.state.telefon!=="" && this.state.grad!==""){
            axios.put("http://localhost:8080/korisnik/" + this.state.korisnik.id, {email: this.state.email, lozinka: this.state.lozinka, ime: this.state.ime, prezime: this.state.prezime, telefon: this.state.telefon, grad: this.state.grad}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Vase informacije su uspesno izmenjene.")
                this.props.history.push("/");
            }).catch(error=>{
                alert("Niste ovlasceni da napravite ovu izmenu.");
            })
        }else{
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    render(){
        return(
            <div className="center container">
                <br/>
                <h3>Izmena profila:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="email">Email:</label>
                        <input type="text" id="email" value={this.state.email} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="lozinka">Lozinka:</label>
                        <input type="password" id="lozinka" value={this.state.lozinka} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="ime">Ime:</label>
                        <input type="text" id="ime" value={this.state.ime} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="prezime">Prezime:</label>
                        <input type="text" id="prezime" value={this.state.prezime} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="telefon">Telefon:</label>
                        <input type="text" id="telefon" value={this.state.telefon} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="grad">Grad:</label>
                        <input type="text" id="grad" value={this.state.grad} onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Izmeni</button>
                    </form>
                </div>
            </div>
        )
    }

}
export default withRouter(AdminProfile)