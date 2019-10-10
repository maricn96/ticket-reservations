import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import Select from 'react-select';
class AddRoom extends Component{

    state={
        sprat:"",
        brojSobe:"",
        brojKreveta:"",
        originalnaCena:"",
        tipSobe:"",
        tipoviSobe:[],
        selectedTip: "",
        hotel:""
    }

    componentDidMount() {
        axios.get("http://localhost:8080/tip_sobe/all/" + this.props.match.params.hotelId)
        .then(res=>{
            console.log(res.data);
             this.setState({
                tipoviSobe: res.data
             })
       })
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

    handleChangeTip = (selectedTip) => { //za tip selcet
        console.log(selectedTip);
        this.setState({ selectedTip });
    };

    handleSubmit = (e) => {
        var token = localStorage.getItem('jwtToken');
        var tipZaSlanje = "";
        var {tipoviSobe} = this.state;
        var i;
        for(i=0; i<tipoviSobe.length; i++){
            if(this.state.selectedTip.value===tipoviSobe[i].id){
                tipZaSlanje = tipoviSobe[i];
            }
        }
        e.preventDefault();
        if(this.state.sprat!=="" && this.state.brojSobe!=="" && this.state.brojKreveta!=="" && this.state.selectedTip!=="" && this.state.originalnaCena!==""){
            axios.post("http://localhost:8080/sobe/", {sprat: this.state.sprat, brojSobe: this.state.brojSobe, brojKreveta: this.state.brojKreveta, tipSobe: tipZaSlanje, hotel: this.state.hotel, originalnaCena: this.state.originalnaCena}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Uspesno dodata nova soba.")
                this.props.history.push("/admin/rooms");
            }).catch(error=>{
                alert("Niste ovlasceni da dodate sobu.");
            })
        }else{
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    render(){
        var { selectedTip } = this.state;
        var { tipoviSobe } = this.state;
        var listaTipova = [];//lista objekata tip
        tipoviSobe.map(tip => {            //prolazimo kroz sve tipove i za svaki pravimo novi objekat sa
            var options = new Object();//poljima value i label posto su ta dva polja neophodna za rad react-select
            options.value = tip.id;
            options.label = tip.naziv;
            listaTipova.push(options); //dodajemo objekat u listu
        })
        return(
            <div className="center container">
                <br/>
                <h3>Dodavanje sobe:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="brojSobe">Broj sobe:</label>
                        <input type="number" id="brojSobe" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="sprat">Sprat:</label>
                        <input type="number" id="sprat" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="brojKreveta">Broj kreveta:</label>
                        <input type="number" id="brojKreveta" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="originalnaCena">Originalna cena:</label>
                        <input type="number" id="originalnaCena" onChange={this.handleChange}/>
                        <Select 
                                    value={selectedTip}
                                    onChange={this.handleChangeTip}
                                    options={ listaTipova } 
                                    id="selectTip"/>
                        <button className="btn waves-effect waves-light green" id="dodajSobaClick">Dodaj</button>
                    </form>
                </div>
            </div>
        )
    }

}
export default withRouter(AddRoom)