import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class Services extends Component{

    state={
        usluge:[]
    }

    componentDidMount() {
        var id = this.props.match.params.serviceId;
        axios.get("http://localhost:8080/usluga/all/" + id)
        .then(res=>{
            this.setState({
                usluge: res.data
            })
        })
    }

    render(){
        var {usluge}=this.state;
        const uslugeList = usluge.length ? (usluge.map(usluga => {
            return(
                <div className="center container" key={usluga.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>{usluga.naziv}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Cena: {usluga.cena} dinara</p>
                                <p>Popust: {usluga.popust}%</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        })):(
            <div className="center">Izabrani hotel ne nudi dodatne usluge.</div>
        )

        return(
            <div className="center container">
                <br/>
                <h3 className="left-align container">Dodatne usluge:</h3>
                <br/>
                {uslugeList}
            </div>
        )
    }

}
export default withRouter(Services)