import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';


class Registration extends Component{

    state = {
        password: "",
        passwordCheck: "",
        korisnik:""
    }

    componentDidMount(){
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
        .then(res=>{
            this.setState({
                korisnik: res.data
            })
        })
    }

    handleChange=(e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit=(e) => {
        e.preventDefault();
        if(this.state.password!=="" && this.state.passwordCheck!=="" && this.state.password===this.state.passwordCheck){
            axios.put("http://localhost:8080/korisnik/firstLogin/" + this.state.korisnik.id, {lozinka: this.state.password})
            .then(res =>{
                console.log(res.data)
                this.props.history.push("/");
            })
        }else{
            alert("Morate ispravno popuniti sva polja da bi se uspesno registrovali.");
        }
    }

    render(){
        return(
            <div className="center container">
                <h4 className="center">Izmena sifre:</h4>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="password">Lozinka:</label>
                        <input type="password" id="password" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="passwordCheck">Ponovo unesite lozinku:</label>
                        <input type="password" id="passwordCheck" onChange={this.handleChange} />
                        <button className="btn waves-effect waves-light green">Potvrdi</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(Registration)