    
import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';


class Ocena extends Component{

    state = {
        ocenaSoba:"",
        ocenaHotel:"",
        korisnik:""
    }

    componentDidMount() {
        var token = localStorage.getItem('jwtToken');
        var id = ""
        var sobaId = ""
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res=>{
                this.setState({
                    korisnik: res.data
                })
            })
    }

    handleChange = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    

    handleSubmit = (e) => {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken');
        if(this.state.ocenaSoba!="" && this.state.ocenaSoba>0 && this.state.ocenaSoba<6 && this.state.ocenaHotel!="" && this.state.ocenaHotel>0 && this.state.ocenaHotel<6){
            axios.post("http://localhost:8080/ocena/hotel/", {ocena: this.state.ocenaHotel, rezervacijaId: this.props.match.params.rezervacijaId, 
                korisnikId: this.state.korisnik.id, hotelId: this.props.match.params.hotelId}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                axios.post("http://localhost:8080/ocena/soba/", {ocena: this.state.ocenaSoba, rezervacijaId: this.props.match.params.rezervacijaId, 
                korisnikId: this.state.korisnik.id, hotelskaSobaId: this.props.match.params.sobaId}, { headers: { Authorization: `Bearer ${token}` } })
                    .then(res => {
                        alert("Ocene uspesno prosledjene.");
                        this.props.history.push('/my_reservations');
                }).catch(error=>{
                    alert("Vec ste ocenili ovu rezervaciju.");
                })
            }).catch(error=>{
                alert("Vec ste ocenili ovu rezervaciju.");
            })
        }
        
    }

    render(){
        return(
            <div className = "center container">
                <h4 className="center">Ocenite hotel i sobu (1-5):</h4>
                <div className = "center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="ocenaSoba">Soba:</label>
                        <input type="number" id="ocenaSoba" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="ocenaHotel">Hotel:</label>
                        <input type="number" id="ocenaHotel" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Oceni</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(Ocena)