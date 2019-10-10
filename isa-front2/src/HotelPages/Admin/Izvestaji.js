import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import {
    VictoryBar,
    VictoryChart,
    VictoryLine,
    VictoryPie,
  } from "victory";

class Izvestaji extends Component{

    state={
        oceneBtn: true,
        prihodiBtn: false,
        posecenostBtn: false,
        sobe:[],
        prosekHotela:"",
        datum:"",
        posecenostDnevna:"",
        posecenostNedeljna:"",
        posecenostMesecna:"",
        datum1:"",
        prihodiNedeljni:"",
        prihodiMesecni:"",
        prihodiGodisnji:""
    }

    componentDidMount(){
        var token = localStorage.getItem('jwtToken');
        var a = "";
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
        .then(res=>{
            this.setState({
                korisnik: res.data
            })
            console.log(res.data);
            a = res.data.zaduzenZaId;
            axios.get("http://localhost:8080/sobe/all/" + a)
            .then(res=>{
                this.setState({
                    sobe: res.data
                })
                console.log(res.data);
                axios.get("http://localhost:8080/ocena/prosek/hotel/" + a)
                .then(res=>{
                    this.setState({
                        prosekHotela: res.data
                    })
                    console.log(res.data);
                })
            })
        })

    }

    clickOcene=()=>{
        this.setState({
            oceneBtn: true,
            prihodiBtn: false,
            posecenostBtn: false
        })
    }

    clickPrihodi=()=>{
        this.setState({
            oceneBtn: false,
            prihodiBtn: true,
            posecenostBtn: false
        })
    }

    clickPosecenost=()=>{
        var token = localStorage.getItem('jwtToken');
        this.setState({
            oceneBtn: false,
            prihodiBtn: false,
            posecenostBtn: true
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
        if(this.state.datum!==""){
            axios.post("http://localhost:8080/rezervacija/posecenost/dnevna", {id: this.state.korisnik.zaduzenZaId, date: this.state.datum}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                this.setState({
                    posecenostDnevna: res.data
                })
                console.log(res.data)
                axios.post("http://localhost:8080/rezervacija/posecenost/nedeljna", {id: this.state.korisnik.zaduzenZaId, date: this.state.datum}, { headers: { Authorization: `Bearer ${token}` } })
                .then(res=>{
                    this.setState({
                        posecenostNedeljna: res.data
                    })
                    axios.post("http://localhost:8080/rezervacija/posecenost/mesecna", {id: this.state.korisnik.zaduzenZaId, date: this.state.datum}, { headers: { Authorization: `Bearer ${token}` } })
                    .then(res=>{
                    this.setState({
                        posecenostMesecna: res.data
                    })
                    
                    })
                    
                })
                
            })
        }else{
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    handleSubmit1 = (e) => {
        var token = localStorage.getItem('jwtToken');
        e.preventDefault();
        if(this.state.datum1!==""){
            axios.post("http://localhost:8080/rezervacija/prihodi/nedeljni", {id: this.state.korisnik.zaduzenZaId, date: this.state.datum1}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                this.setState({
                    prihodiNedeljni: res.data
                })
                console.log(res.data)
                axios.post("http://localhost:8080/rezervacija/prihodi/mesecni", {id: this.state.korisnik.zaduzenZaId, date: this.state.datum1}, { headers: { Authorization: `Bearer ${token}` } })
                .then(res=>{
                    this.setState({
                        prihodiMesecni: res.data
                    })
                    axios.post("http://localhost:8080/rezervacija/prihodi/godisnji", {id: this.state.korisnik.zaduzenZaId, date: this.state.datum1}, { headers: { Authorization: `Bearer ${token}` } })
                    .then(res=>{
                    this.setState({
                        prihodiGodisnji: res.data
                    })
                    
                    })
                    
                })
                
            })
        }else{
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    render(){
        var {sobe}=this.state;
        const sobeList = sobe.length ? (sobe.map(soba => {
            return(
                <div className="center container" key={soba.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>Soba sa brojem {soba.brojSobe}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Prosecna ocena: {soba.ocena}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjena nijedna soba.</div>
        )

        var sadrzaj;
        if(this.state.oceneBtn){
           sadrzaj =
               <div>
                <br/>
                    <h5 className="left">Prosecna ocena hotela: {this.state.prosekHotela}</h5>
                    <br/>
                    <br/>
                    <h5 className="left" id="prosecnaOcena">Prosecne ocene soba:</h5>
                    {sobeList}
                </div>    
        }else if(this.state.prihodiBtn){
            sadrzaj =<div>
                <br/>
                <form onSubmit={this.handleSubmit1}>
                        <label className="left black-text" htmlFor="datum1">Datum:</label>
                        <input type="date" id="datum1" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Pogledaj</button>
                    </form>
                <VictoryChart>
                        <VictoryBar
                            data={[
                            { period: 'nedeljni', prihodi: this.state.prihodiNedeljni },
                            { period: 'mesecni', prihodi: this.state.prihodiMesecni },
                            { period: 'godisnji', prihodi: this.state.prihodiGodisnji },
                            ]}
                            x="period"
                            y="prihodi"
                        />
                    </VictoryChart>
                </div>
        }else if(this.state.posecenostBtn){
            sadrzaj =<div>
                <br/>
                <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="datum">Datum:</label>
                        <input type="date" id="datum" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green">Pogledaj</button>
                    </form>
                <VictoryChart>
                        <VictoryBar
                            data={[
                            { period: 'danas', posecenost: this.state.posecenostDnevna },
                            { period: 'nedeljno', posecenost: this.state.posecenostNedeljna },
                            { period: 'mesecno', posecenost: this.state.posecenostMesecna },
                            ]}
                            x="period"
                            y="posecenost"
                        />
                    </VictoryChart>
                </div>
        }

        return(          
            <div className="container center">
                <button className="btn waves-effect waves-light red darken-3" id="btnOcene1" onClick={this.clickOcene}>Ocene</button>
                <button className="btn waves-effect waves-light red darken-3" id="btnOcene2" onClick={this.clickPrihodi}>Prihodi</button>
                <button className="btn waves-effect waves-light red darken-3" id="btnOcene3" onClick={this.clickPosecenost}>Posecenost</button>
                <div className="container center">
                   {sadrzaj}
                </div>
            </div>
        )
    }

}
export default withRouter(Izvestaji)