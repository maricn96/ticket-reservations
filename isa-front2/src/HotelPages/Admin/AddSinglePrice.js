import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import Select from 'react-select';
class AddSinglePrice extends Component{

    state={
        soba:"",
        cena:"",
        datumOd:"",
        datumDo:""
    }

    componentDidMount() {
       axios.get("http://localhost:8080/sobe/" + this.props.match.params.sobaId)
        .then(res=>{
            console.log(res.data);
             this.setState({
                soba: res.data
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
        if(this.state.cena!=="" && this.state.datumOd!=="" && this.state.datumDo!==""){
            axios.post("http://localhost:8080/cena/", {cenaNocenja: this.state.cena, datumOd: this.state.datumOd, datumDo: this.state.datumOd, hotelskaSoba: this.state.soba}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Uspesno dodata nova cena nocenja.")
                this.props.history.push("/cenovnik");
            }).catch(error=>{
                alert("Niste ovlasceni da dodate cenu.");
            })
        }else{
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    render(){
        return(
            <div className="center container">
                <br/>
                <h3>Dodavanje cene nocenja:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="cena">Cena:</label>
                        <input type="number" id="cena" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumOd">Datum od:</label>
                        <input type="date" id="datumOd" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumDo">Datum do:</label>
                        <input type="date" id="datumDo" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green" id="dodajSobaClick">Dodaj</button>
                    </form>
                </div>
            </div>
        )
    }

}
export default withRouter(AddSinglePrice)