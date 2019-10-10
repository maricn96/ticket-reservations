import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import Select from 'react-select';

class Rooms extends Component{

    state={
        sobe:[],
        datumOd:"",
        datumDo:"",
        tipSobe:"",
        tipoviSobe:[],
        selectedTip: "",
        cenaOd:"",
        cenaDo:""
    }

    componentDidMount() {
        var id = this.props.match.params.hotelId;
        axios.get("http://localhost:8080/sobe/all/" + id)
        .then(res=>{
            this.setState({
                sobe: res.data
            })
        })

        axios.get("http://localhost:8080/tip_sobe/all/" + id)
        .then(res=>{
            console.log(res.data);
             this.setState({
                tipoviSobe: res.data
             })
       })
    }

    handleChangeTip = (selectedTip) => { //za tip selcet
        console.log(selectedTip);
        this.setState({ selectedTip });
    };

    handleChange = (e) => { //za inpute
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit = (e) => {
        var tipZaSlanje = "";
        var {tipoviSobe} = this.state;
        var i;
        for(i=0; i<tipoviSobe.length; i++){
            if(this.state.selectedTip.value===tipoviSobe[i].id){
                tipZaSlanje = tipoviSobe[i];
            }
        }
        e.preventDefault();
            axios.post("http://localhost:8080/sobe/slobodne/" + this.props.match.params.hotelId, {datumOd: this.state.datumOd, datumDo: this.state.datumDo, cenaOd: this.state.cenaOd, cenaDo: this.state.cenaDo, tipSobe: tipZaSlanje})
            .then(res => {
                this.setState({
                    sobe: res.data
                })
            })
    }

    rezervisiClick=(sobaId, originalCena, fakeCena)=>{
        this.props.history.push("/reserve/"+ this.props.match.params.hotelId +"/" + sobaId);
        
        if(originalCena===-1){
            localStorage.setItem("cena", fakeCena);
        }else{
            localStorage.setItem("cena", originalCena);
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
        var isLogged = localStorage.getItem("isLogged");
        var rola = localStorage.getItem('rola');
        var {sobe}=this.state;
        const sobeList = sobe.length ? (sobe.map(soba => {
            return(
                <div className="center container" key={soba.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>{soba.brojSobe}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Sprat: {soba.sprat}</p>
                                <p>Broj kreveta: {soba.brojKreveta}</p>
                                <p>Tip sobe: {soba.tipSobe.naziv}</p>
                                <p>Ocena: {soba.ocena}</p>
                                {soba.cenaNocenja==-1 ? (<p>Cena nocenja: {soba.originalnaCena}</p>) : (<p>Cena nocenja: {soba.cenaNocenja}</p>)}
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                {(isLogged && rola==="KORISNIK") ? (
                                        <button className="btn waves-effect waves-light green" onClick={()=>{this.rezervisiClick(soba.id, soba.originalnaCena, soba.cenaNocenja)}}>Rezrvisi</button>
                                    ) : (
                                        <p/>
                                )}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjena nijedna soba.</div>
        )

        return(
            <div className="center container">
            <br/>
            <h5 className="left-align container">Pronadjite slobodne sobe u odredjenom periodu:</h5>
            <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="datumOd">Datum od:</label>
                        <input type="date" id="datumOd" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumDo">Datum do:</label>
                        <input type="date" id="datumDo" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="cenaOd">Cena od:</label>
                        <input type="number" id="cenaOd" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="cenaDo">Cena do:</label>
                        <input type="number" id="cenaDo" onChange={this.handleChange}/>
                        <Select 
                                    value={selectedTip}
                                    onChange={this.handleChangeTip}
                                    options={ listaTipova } 
                                    id="selectTip"/>
                        <button className="btn waves-effect waves-light green">Pretrazi</button>
                    </form>
                </div>
                <br/>
                <h3 className="left-align container">Sobe:</h3>
                <br/>
                {sobeList}
            </div>
        )
    }

}
export default withRouter(Rooms)