import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import ReservationsChooseBar from './ReservationsChooseBar';

class MyReservations extends Component{

    state={
        rezervacije:[],
        korisnik:""
    }

    componentDidMount() {
        var token = localStorage.getItem('jwtToken');
        var id = ""
        var sobaId = ""
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res=>{
                this.setState({
                    korisnik: res.data
                })
                id=res.data.id;
            axios.get("http://localhost:8080/rezervacija/user/" + id)
            .then(res=>{
                this.setState({
                    rezervacije: res.data
                })
                console.log(res.data);
            })
        })
    }

    oceniClick=(rezId, hotelId, sobaId)=>{//dovrsi
        console.log(rezId+" "+hotelId+" "+sobaId);
        this.props.history.push("/rating/"+ rezId + "/" + hotelId + "/" + sobaId);
    }
    
    otkaziClick=(rezId)=>{
        var token = localStorage.getItem('jwtToken')
            axios.delete("http://localhost:8080/rezervacija/" + rezId, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                this.componentDidMount();
            })
    }

    render(){
        var {rezervacije}=this.state;
        const rezervacijeList = rezervacije.length ? (rezervacije.map(rezervacija => {
            return(
                <div className="center container" key={rezervacija.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>Od: {rezervacija.datumOd} Do: {rezervacija.datumDo}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Cena: {rezervacija.ukupnaCena}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light green" id="sobeBtn" onClick={()=>{this.oceniClick(rezervacija.id, rezervacija.hotel.id, rezervacija.hotelskaSoba.id)}}>Oceni</button>
                                    <button className="btn waves-effect waves-light red" id="uslugeBtn" onClick={()=>{this.otkaziClick(rezervacija.id)}}>Otkazi</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjena nijedana rezervacija.</div>
        )

        return(
            <div className="center container">
                <br/>
                <ReservationsChooseBar/>
                <br/>
                <h3 className="left-align container">Rezervacije:</h3>
                <br/>
                {rezervacijeList}
            </div>
        )
        
    }

}
export default withRouter(MyReservations)