import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
class EditService extends Component{

    state={
        usluga:"",
        naziv:"",
        cena:"",
        popust:""
    }

    componentDidMount() {
        axios.get("http://localhost:8080/usluga/" + this.props.match.params.serviceId)
        .then(res=>{
            console.log(res.data);
             this.setState({
                usluga: res.data,
                naziv: res.data.naziv,
                cena: res.data.cena,
                popust: res.data.popust
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
            axios.put("http://localhost:8080/usluga/" + this.props.match.params.serviceId, {naziv: this.state.naziv, cena: this.state.cena, popust: this.state.popust, hotel:this.state.usluga.hotel}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Uspesno izmenjena usluga.")
                this.props.history.push("/admin/services");
            }).catch(error=>{
                alert("Niste ovlasceni da izmenite uslugu.");
            })
        }else{
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    render(){
        return(
            <div className="center container">
                <br/>
                <h3>Izmena usluge:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="naziv">Naziv:</label>
                        <input type="text" id="naziv" value={this.state.naziv} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="cena">Cena:</label>
                        <input type="number" id="cena" value={this.state.cena} onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="popust">Popust( u procentima ):</label>
                        <input type="number" id="popust" value={this.state.popust} onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Izmeni</button>
                    </form>
                </div>
            </div>
        )
    }

}
export default withRouter(EditService)