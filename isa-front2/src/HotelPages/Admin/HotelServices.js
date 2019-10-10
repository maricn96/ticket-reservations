import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class HotelServices extends Component{

    state={
        services:[],
        korisnik:""
    }

    componentDidMount() {
        var token = localStorage.getItem('jwtToken');
        var a = "";
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
        .then(res=>{
            this.setState({
                korisnik: res.data
            })
            console.log(res.data);
            a = res.data.zaduzenZaId;
            axios.get("http://localhost:8080/usluga/all/" + a, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                this.setState({
                    services: res.data
                })
                console.log(res.data);
            })
        })
    }

    dodajClick=()=>{
        this.props.history.push('/admin/add_services/' + this.state.korisnik.zaduzenZaId);
    }

    izmeniClick=(serviceId)=>{
        this.props.history.push('/admin/edit_services/' + serviceId);
    }

    obrisiClick=(serviceId)=>{
        var token = localStorage.getItem('jwtToken');
        axios.delete("http://localhost:8080/usluga/" + serviceId, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                console.log(res.data);
                this.componentDidMount();
            })
    }

    render(){
        var {services}=this.state;
        const servicesList = services.length ? (services.map(service => {
            return(
                <div className="center container" key={service.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>{service.naziv}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Cena: {service.cena}</p>
                                <p>Popust: {service.popust}%</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light green" id="izmeniSobuBtn" onClick={()=>{this.izmeniClick(service.id)}}>Izmeni</button>
                                    <button className="btn waves-effect waves-light red" id="obrisiSobuBtn" onClick={()=>{this.obrisiClick(service.id)}}>Obrisi</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjena nijedna usluga.</div>
        )

        return(
            <div className="center container">
                <br/>
                <div>
                    <h3 className="left-align container" id="dodajSobuBtn">Usluge:</h3>
                    <button className="btn waves-effect waves-light green" id="dodajSobuBtn" onClick={()=>{this.dodajClick()}}>Dodaj</button>
                </div>
                <br/>
                {servicesList}
            </div>
        )
    }

}
export default withRouter(HotelServices)