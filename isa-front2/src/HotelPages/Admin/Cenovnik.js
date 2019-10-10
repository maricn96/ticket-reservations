import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class Cenovnik extends Component{

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

                    axios.get("http://localhost:8080/sobe/admin/all/" + a, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                this.setState({
                    sobe: res.data
                })
                console.log(res.data);
            })
    }

    izmeniCenuClick=(sobaId)=>{
        this.props.history.push('/admin/edit_price/' + sobaId);
    }

    vanrednaClick=(sobaId)=>{
        this.props.history.push('/admin/single_prices/' + sobaId);
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
                                <p>Originalna cena: {soba.originalnaCena}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light green" id="izmeniSobuBtn" onClick={()=>{this.izmeniCenuClick(soba.id)}}>Izmeni</button>
                                    <button className="btn waves-effect waves-light green" id="obrisiSobuBtn" onClick={()=>{this.vanrednaClick(soba.id)}}>Vanredne cene nocenja</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Nije pronadjena nijedna cena.</div>
        )

        return(
            <div className="center container">
                <br/>
                <div>
                    <h3 className="left-align container" id="dodajSobuBtn">Cene:</h3>
                </div>
                <br/>
                {sobeList}
            </div>
        )
    }

}
export default withRouter(Cenovnik)