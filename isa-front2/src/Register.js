import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';


class Registration extends Component{

    state = {
        username: "",
        password: "",
        passwordCheck: "",
        firstname: "",
        lastname: "",
        city: "",
        telefone: ""
    }

    handleChange=(e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit=(e) => {
        e.preventDefault();
        if(this.state.username!=="" && this.state.password!=="" && this.state.firstname!=="" && this.state.lastname!=="" && this.state.passwordCheck!==""
            && this.state.city!=="" && this.state.telefone!=="" && this.state.password===this.state.passwordCheck){
            axios.post("http://localhost:8080/korisnik/", {email: this.state.username, lozinka: this.state.password, ime: this.state.firstname, prezime: this.state.lastname, grad: this.state.city, telefon: this.state.telefone})
            .then(res =>{
                console.log(res.data)
                this.props.history.push("/");
            }).catch(error=>{
                alert("Nalog sa unetim email-om vec postoji.");
            })
        }else{
            alert("Morate ispravno popuniti sva polja da bi se uspesno registrovali.");
        }
    }

    render(){
        return(
            <div className="center container">
                <h4 className="center">Registruj se:</h4>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="username">Email:</label>
                        <input type="email" id="username" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="password">Lozinka:</label>
                        <input type="password" id="password" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="passwordCheck">Ponovo unesite lozinku:</label>
                        <input type="password" id="passwordCheck" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="firstname">Ime:</label>
                        <input type="text" id="firstname" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="lastname">Prezime:</label>
                        <input type="text" id="lastname" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="city">Grad:</label>
                        <input type="text" id="city" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="telefone">Telefon:</label>
                        <input type="tel" pattern="[0-9]{5,20}" id="telefone" onChange={this.handleChange} />
                        <button className="btn waves-effect waves-light green">Registracija</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(Registration)