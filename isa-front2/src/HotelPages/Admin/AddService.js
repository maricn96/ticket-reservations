import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
class AddService extends Component{

    state={
        naziv:"",
        cena:"",
        popust:"",
        hotel:""
    }

    componentDidMount() {
       axios.get("http://localhost:8080/hotel/" + this.props.match.params.hotelId)
        .then(res=>{
            console.log(res.data);
             this.setState({
                hotel: res.data
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
        if(this.state.naziv!=="" && this.state.cena!=="" && this.state.popust!==""){
            axios.post("http://localhost:8080/usluga/", {naziv: this.state.naziv, cena: this.state.cena, popust: this.state.popust, hotel:this.state.hotel}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Uspesno dodata nova usluga.")
                this.props.history.push("/admin/services");
            }).catch(error=>{
                alert("Niste ovlasceni da dodate uslugu.");
            })
        }else{
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    render(){
        return(
            <div className="center container">
                <br/>
                <h3>Dodavanje usluge:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="naziv">Naziv:</label>
                        <input type="text" id="naziv" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="cena">Cena:</label>
                        <input type="number" id="cena" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="popust">Popust( u procentima ):</label>
                        <input type="number" id="popust" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Dodaj</button>
                    </form>
                </div>
            </div>
        )
    }

}
export default withRouter(AddService)