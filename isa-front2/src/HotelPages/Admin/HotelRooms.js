import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class HotelRooms extends Component{

    state={
        sobe:[],
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
            axios.get("http://localhost:8080/sobe/admin/all/" + a, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                this.setState({
                    sobe: res.data
                })
                console.log(res.data);
            })
        })
    }

    dodajClick=()=>{
        this.props.history.push('/admin/add_room/' + this.state.korisnik.zaduzenZaId);
    }

    izmeniClick=(sobaId, hotelId)=>{
        this.props.history.push('/admin/edit_room/' + hotelId + "/" + sobaId);
    }

    obrisiClick=(sobaId)=>{
        var token = localStorage.getItem('jwtToken');
        axios.delete("http://localhost:8080/sobe/" + sobaId, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                console.log(res.data);
                this.componentDidMount();
            })
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
                                <span className="card-title"><b>{soba.brojSobe}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Sprat: {soba.sprat}</p>
                                <p>Broj kreveta: {soba.brojKreveta}</p>
                                <p>Tip sobe: {soba.tipSobe.naziv}</p>
                                <p>Originalna cena: {soba.originalnaCena}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light green" id="izmeniSobuBtn" onClick={()=>{this.izmeniClick(soba.id, soba.hotel.id)}}>Izmeni</button>
                                    <button className="btn waves-effect waves-light red" id="obrisiSobuBtn" onClick={()=>{this.obrisiClick(soba.id)}}>Obrisi</button>
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
                <div>
                    <h3 className="left-align container" id="dodajSobuBtn">Sobe:</h3>
                    <button className="btn waves-effect waves-light green" id="dodajSobuBtn" onClick={()=>{this.dodajClick()}}>Dodaj</button>
                </div>
                <br/>
                {sobeList}
            </div>
        )
    }

}
export default withRouter(HotelRooms)