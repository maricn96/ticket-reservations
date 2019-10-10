import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';


class FlightInfo extends Component {

    state = {
        kompanija: "",
        avgrating: "",
        destinacijeNaKojimaPosluje: []
    }

    componentDidMount() {
        console.log(this.props.match.params.flightid);
        axios.get('http://localhost:8080/aviocompany/getone/' + this.props.match.params.flightid).then(
            res => {
                this.setState({
                    kompanija: res.data
                })

                let kompanija = this.state.kompanija.idAvioKompanije;
                axios.get('http://localhost:8080/destination/getalldestsbycompany/' + kompanija).then(
                    res => {
                        console.log("DESTINACIJE: ")
                        console.log(res.data)
                        this.setState({
                            destinacijeNaKojimaPosluje: res.data
                        })
                    }


                )
            }


        )
        //za prosecnu ocenu
        axios.get('http://localhost:8080/aviocompany/getavgrating/' + this.props.match.params.flightid).then(
            res => {
                console.log(res.data);
                this.setState({
                    avgrating: res.data
                })
            }
        )
    }

    render() {
        const destinacijeNaKojimaPosluje = this.state.destinacijeNaKojimaPosluje.length ? (this.state.destinacijeNaKojimaPosluje.map(dest => {
            return (
                <div key={dest.idDestinacije}>
                    <div><i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{dest.naziv}</i></div>
                </div>
            );
        })) : (
                <h3>Nema destinacija</h3>
            )

        return (
            <div>
                <div className="center container">
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                    <span className="card-title"><b>Naziv aviokompanije: {this.state.kompanija.naziv}</b></span>
                                    <div className="divider white"></div>
                                    <br />
                                    <p>Adresa: &nbsp;&nbsp;{this.state.kompanija.adresa}</p>
                                    <p>Opis: &nbsp;&nbsp;{this.state.kompanija.opis}</p>
                                    <p>Prosecna ocena: &nbsp;&nbsp;{this.state.avgrating}</p>
                                    <p>Destinacije na kojima posluje: &nbsp;&nbsp;</p> {destinacijeNaKojimaPosluje}
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(FlightInfo);




