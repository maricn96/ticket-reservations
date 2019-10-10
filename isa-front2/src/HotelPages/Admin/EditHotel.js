import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
class EditHotel extends Component{

    state={
        korisnik:"",
        hotel:"",
        naziv:"",
        adresa:"",
        opis:""
    }

    componentDidMount() {
        var a = "";
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
        .then(res=>{
            this.setState({
                korisnik: res.data
            })
            console.log(res.data);
            a = res.data.zaduzenZaId;
            axios.get("http://localhost:8080/hotel/" + a)
            .then(res=>{
                this.setState({
                    hotel: res.data,
                    naziv: res.data.naziv,
                    adresa: res.data.adresa,
                    opis: res.data.opis
                })
                console.log(res.data);
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
        if(this.state.naziv!=="" && this.state.adresa!=="" && this.state.opis!==""){
            axios.put("http://localhost:8080/hotel/" + this.state.hotel.id, {naziv: this.state.naziv, adresa: this.state.adresa, opis: this.state.opis}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Informacije hotela su uspesno izmenjene.")
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
                <h3>Izmena hotela:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="naziv">Naziv:</label>
                        <input type="text" id="naziv" value={this.state.naziv} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="adresa">Adresa:</label>
                        <input type="text" id="adresa" value={this.state.adresa} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="opis">Opis:</label>
                        <input type="text" id="opis" value={this.state.opis} onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Izmeni</button>
                    </form>
                </div>
            </div>
        )
    }

}
export default withRouter(EditHotel)