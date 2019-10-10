import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class FastRooms extends Component{

    state={
        sobe:[],
        datumOd:"",
        datumDo:""
    }

    componentDidMount() {
        var id = this.props.match.params.hotelId;
        console.log(sessionStorage.getItem('vremePoletanja'));
        console.log(sessionStorage.getItem('vremeSletanja'))
        axios.post("http://localhost:8080/sobe/brza/" + id, {datumOd: sessionStorage.getItem('vremePoletanja'), datumDo: sessionStorage.getItem('vremeSletanja')})
        .then(res=>{
            this.setState({
                sobe: res.data
            })
        })
    }

    handleChange = (e) => { //za inpute
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit = (e) => {
        e.preventDefault();
            axios.post("http://localhost:8080/sobe/slobodne/" + this.props.match.params.hotelId, {datumOd: this.state.datumOd, datumDo: this.state.datumDo})
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
export default withRouter(FastRooms)