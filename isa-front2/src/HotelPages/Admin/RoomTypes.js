import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class RoomTypes extends Component{

    state={
        tipovi:[],
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
            axios.get("http://localhost:8080/tip_sobe/all/" + a)
            .then(res=>{
                this.setState({
                    tipovi: res.data
                })
                console.log(res.data);
            })
        })
    }

    dodajClick=()=>{
        this.props.history.push('/admin/add_room_type/' + this.state.korisnik.zaduzenZaId);
    }

    obrisiClick=(tipId)=>{
        var token = localStorage.getItem('jwtToken');
        axios.delete("http://localhost:8080/tip_sobe/" + tipId, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                console.log(res.data);
                this.componentDidMount();
            })
    }

    render(){
        var {tipovi}=this.state;
        const tipoviList = tipovi.length ? (tipovi.map(tip => {
            return(
                <div className="center container" key={tip.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>{tip.naziv}</b></span>
                                <div className="divider white"></div>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light red" id="obrisiTipBtn" onClick={()=>{this.obrisiClick(tip.id)}}>Obrisi</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjen nijedan tip.</div>
        )

        return(
            <div className="center container">
                <br/>
                <div>
                    <h3 className="left-align container" id="dodajSobuBtn">Tipovi:</h3>
                    <button className="btn waves-effect waves-light green" id="dodajSobuBtn" onClick={()=>{this.dodajClick()}}>Dodaj</button>
                </div>
                <br/>
                {tipoviList}
            </div>
        )
    }

}
export default withRouter(RoomTypes)