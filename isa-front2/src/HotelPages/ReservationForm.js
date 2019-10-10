import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';


class ReservationForm extends Component{

    state = {
        datumOd:"",
        datumDo:"",
        soba:"",
        korisnik:"",
        services:[],
        selectedUsluge:[],
        ukupnaCena:"",
        popust:"",
        hotel:"",

        idRezervacije: "",
        karta: ""
    }

    componentDidMount(){
        axios.get("http://localhost:8080/usluga/all/" + this.props.match.params.hotelId)
            .then(res => {
                this.setState({
                    services: res.data
                })
            })

            axios.get("http://localhost:8080/hotel/" + this.props.match.params.hotelId)
            .then(res => {
                this.setState({
                    hotel: res.data
                })
            })

            axios.get("http://localhost:8080/sobe/" + this.props.match.params.sobaId)
            .then(res => {
                console.log(res.data);
                this.setState({
                    soba: res.data
                })
            })

            axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {
                console.log(res.data);
                this.setState({
                    korisnik: res.data
                })
            })

            

            if(sessionStorage.getItem("flag")=="1"){
                axios.get("http://localhost:8080/ticket/getone/" + sessionStorage.getItem('ticketid'))
                .then(res => {
                    this.setState({
                        karta: res.data
                    })
                })
                this.setState({
                    popust: 5
                })
            }
    }

    handleChange = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
        if(e.target.id=="datumOd" && this.state.datumDo!=="")
        {
            const startDate = e.target.value;
            const endDate   = this.state.datumDo;
            const timeDiff  = ((new Date(endDate) - new Date(startDate)));
            const days      = timeDiff / (1000 * 60 * 60 * 24)
            this.setState({
                ukupnaCena: +days * +localStorage.getItem("cena")
            })
        }else if(e.target.id=="datumDo" && this.state.datumOd!==""){
            const startDate = this.state.datumOd;
            const endDate   = e.target.value;
            const timeDiff  = ((new Date(endDate) - new Date(startDate)));
            const days      = timeDiff / (1000 * 60 * 60 * 24)
            this.setState({
                ukupnaCena: +days * +localStorage.getItem("cena")
            })
        }
    }

    handleSubmit = (e) => {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken');
        if(this.state.datumOd!=="" && this.state.datumDo!==""){
            var iznos = this.state.ukupnaCena - (this.state.ukupnaCena/100) * this.state.popust;
            console.log(iznos);
            axios.post("http://localhost:8080/rezervacija/", {datumDo: this.state.datumDo, datumOd: this.state.datumOd, hotelskaSoba:this.state.soba, korisnik: this.state.korisnik, ukupnaCena: iznos, hotel: this.state.hotel, brojOsoba:1}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res =>{
                this.setState({
                    idRezervacije: res.data.id
                })
                sessionStorage.setItem("vremePoletanja", undefined);
                sessionStorage.setItem("vremeSletanja", undefined);
                
                alert("Uspsno rezervisan smestaj.");
                if(sessionStorage.getItem('flag') == '1')
                {
                    let karta = this.state.karta;
                    karta.idHotelRezervacije = res.data.id;

                    axios.put("http://localhost:8080/ticket/update/" + sessionStorage.getItem('ticketid'), karta)
                        .then(res => {
                            sessionStorage.setItem("flag", 0);
                            sessionStorage.setItem("ticketid", undefined);
                        })
                }
                
                this.props.history.push("/hotels");
            }).catch(error=>{
                alert("Doslo je do greske prilikom rezervacije.");
            })
        }else{
            alert("Morate ispravno popuniti sva polja da bi uspesno rezervisali sobu.");
        }

        

    }

    handleCBChange = (e) =>{
        var lista = this.state.selectedUsluge;
        var servisi = this.state.services;
        var dodat = 0;
        var cenaDodatne = 0;
        var popust = 0;
        var i;
        for(i=0; i<lista.length; i++){
            if(lista[i]===e.target.value){ //remove
                lista.splice(i, 1);
                dodat=1;
            }
            for(var h=0; h<servisi.length; h++){
                console.log(servisi[h].id + "==" + e.target.value)
                if(servisi[h].id==e.target.value){
                    console.log("EVO ME")
                    cenaDodatne+=servisi[h].cena
                    this.setState({ 
                        popust: +this.state.popust - servisi[h].popust
                    })
                }
            }
            this.setState({
                ukupnaCena: +this.state.ukupnaCena - cenaDodatne
            })
            cenaDodatne = 0;
            popust = 0;
        }
        if(dodat==0){ //add
            lista.push(e.target.value);
            for(var h=0; h<servisi.length; h++){
                console.log(servisi[h].id + "==" + e.target.value)
                if(servisi[h].id==e.target.value){
                    cenaDodatne+=servisi[h].cena
                    this.setState({
                        popust: +this.state.popust + servisi[h].popust
                    })
                }
            }
            this.setState({
                ukupnaCena: +this.state.ukupnaCena + cenaDodatne
            })
            console.log(lista);
            cenaDodatne = 0;
            popust = 0;
        } 
    }

    render(){
        var {services} = this.state;
        var {ukupnaCena} = this.state;
        var {popust} = this.state;
        return(
            //ne zaboravi da dodas dodtne usluge i racunanje ukupne cene
            <div className = "center container">
                <h4 className="center">Rezervacija smestaja:</h4>
                <div className = "center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="datumOd">Datum od:</label>
                        <input type="date" id="datumOd" onChange={this.handleChange}/>
                        <label className="left black-text" htmlFor="datumDo">Datum do:</label>
                        <input type="date" id="datumDo" onChange={this.handleChange}/>
                        
                        { 
                            sessionStorage.getItem("flag")=="0" ? (
                            services.map(service => {
                            return(
                                <p key = {service.id}>
                                    <label>
                                        <input className="black" type="checkbox" id={service.id} value={service.id} onChange={this.handleCBChange}/>
                                        <span className="black-text">{service.naziv}........{service.cena}din..(popust {service.popust}%)</span>
                                    </label>
                                </p>
                            )
                        })):(<p/>)
                            
                        }
                        <h5 id="ukupnaCena">Ukupna cena: {ukupnaCena} (popust {popust}%)</h5>
                        <button className="btn waves-effect waves-light green">Rezervisi</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(ReservationForm) 