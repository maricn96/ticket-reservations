import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class RentACars extends Component{

    state={
        servisi:[]
    }

    componentDidMount() {

    }

    render(){
        return(
            <div className="center container">
                <br/>
                <div>
                    <h3 className="left-align container">Rent:</h3>
                </div>
                <br/>
                <div className="center container">
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>Servis</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Adresa: adresa</p>
                                <p>Opis: opis</p>
                                <p>Ocena: 3.9</p>
                                <p/>
                                <div className="divider white"></div>
                                <div className="card-action">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        )
    }

}
export default withRouter(RentACars)