import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';
import Select from 'react-select';

class Registration extends Component{

    state = {
        username: "",
        password: "",
        passwordCheck: "",
        firstname: "",
        lastname: "",
        city: "",
        telefone: "",
        selectedTip: ""
    }

    componentDidMount(){}

    handleChange=(e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    
    handleChangeTip = (selectedTip) => { //za tip selcet
        console.log(selectedTip);
        this.setState({ selectedTip });
    };

    handleSubmit=(e) => {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken');
        if(this.state.username!=="" && this.state.password!=="" && this.state.firstname!=="" && this.state.lastname!=="" && this.state.passwordCheck!==""
            && this.state.city!=="" && this.state.telefone!=="" && this.state.password===this.state.passwordCheck && this.state.selectedTip!==null){
                var tip = ""
                if(this.state.selectedTip.value==1){
                    tip="ADMIN_AVIO_KOMPANIJE"
                }else if(this.state.selectedTip.value==2){
                    tip="ADMIN_HOTELA"
                }else if(this.state.selectedTip.value==3){
                    tip="ADMIN_RENT_A_CAR"
                }else{
                    tip="MASTER_ADMIN"
                }
            axios.post("http://localhost:8080/korisnik/admin", {email: this.state.username, lozinka: this.state.password, ime: this.state.firstname, prezime: this.state.lastname, grad: this.state.city, telefon: this.state.telefone, rola: tip}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res =>{
                console.log(res.data)
                alert("Uspesno kreiran novi admin.");
                this.props.history.push("/");
            }).catch(error=>{
                alert("Nalog sa unetim email-om vec postoji.");
            })
        }else{
            alert("Morate ispravno popuniti sva polja da bi se uspesno registrovali.");
        }
    }

    render(){
        const tipoviAdmina = [
            { label: "Admin aviokompanije", value: 1 },
            { label: "Admin hotela", value: 2 },
            { label: "Admin rent-a-cara", value: 3 },
            { label: "Admin sistema", value: 4 }
          ];
        var { selectedTip } = this.state;
        return(
            <div className="center container">
                <h4 className="center">Dodaj admina:</h4>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="username">Email:</label>
                        <input type="text" id="username" onChange={this.handleChange} />
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
                        <input type="text" id="telefone" onChange={this.handleChange}/>
                        <Select 
                                    value={selectedTip}
                                    onChange={this.handleChangeTip}
                                    options={ tipoviAdmina } 
                                    id="selectTip"/>
                        <button className="btn waves-effect waves-light green" id="addAdminBtn">Dodaj</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(Registration)