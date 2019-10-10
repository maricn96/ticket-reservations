import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';
import Select from 'react-select';

class AddAvio extends Component{

    state = {
        naziv: "",
        adresa: "",
        opis: "",
        admin:"",
        admini:[],
        lat:"",
        lng:"",
        selectedAdmin: "",
    }

    componentDidMount(){
        var token = localStorage.getItem('jwtToken')
        axios.get("http://localhost:8080/korisnik/allAvioAdmins", { headers: { Authorization: `Bearer ${token}` } })
        .then(res=>{
            console.log(res.data);
             this.setState({
                admini: res.data
             })
       })
    }

    handleChange=(e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleChangeAdmin = (selectedAdmin) => { //za tip selcet
        console.log(selectedAdmin);
        this.setState({ selectedAdmin });
    };

    handleSubmit=(e) => {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken')
        var adminZaSlanje = "";
        var {admini} = this.state;
        var i;
        for(i=0; i<admini.length; i++){
            if(this.state.selectedAdmin.value===admini[i].id){
                adminZaSlanje = admini[i];
            }
        }
        if(this.state.naziv!=="" && this.state.opis!=="" && this.state.adresa!=="" && this.state.selectedAdmin!="" && this.state.lat!="" && this.state.lng!=""){
            axios.post("http://localhost:8080/aviocompany/" + this.state.selectedAdmin.value, {naziv: this.state.naziv, adresa:this.state.adresa, opis:this.state.opis, lat: this.state.lat, lng: this.state.lng}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res =>{
                console.log(res.data)
                alert("Uspesno kreirana nova avio kompanija.");
                this.props.history.push("/");
            }).catch(error=>{
                alert("Doslo je do greske prilikom kreiranja avio kompanije.");
            })
        }else{
            alert("Morate ispravno popuniti sva polja da bi se uspesno kreirala kompanija.");
        }
    }

    render(){
        var { selectedAdmin } = this.state;
        var { admini } = this.state;
        var listaAdmina = [];//lista objekata tip
        admini.map(a => {            //prolazimo kroz sve tipove i za svaki pravimo novi objekat sa
            var options = new Object();//poljima value i label posto su ta dva polja neophodna za rad react-select
            options.value = a.id;
            options.label = a.email;
            listaAdmina.push(options); //dodajemo objekat u listu
        })
        return(
            <div className="center container">
                <h4 className="center">Dodaj aviokompaniju:</h4>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="naziv">Naziv:</label>
                        <input type="text" id="naziv" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="adresa">Adresa:</label>
                        <input type="text" id="adresa" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="opis">Opis:</label>
                        <input type="text" id="opis" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="lat">Latituda:</label>
                        <input type="number" step="any" id="lat" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="lng">Longituda:</label>
                        <input type="number" step="any" id="lng" onChange={this.handleChange} />
                        <Select 
                                    value={selectedAdmin}
                                    onChange={this.handleChangeAdmin}
                                    options={ listaAdmina } 
                                    id="selectTip"/>
                        <button className="btn waves-effect waves-light green">Dodaj</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(AddAvio) 