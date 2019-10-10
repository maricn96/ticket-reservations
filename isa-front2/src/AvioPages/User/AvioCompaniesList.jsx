import React, { Component } from 'react';
import axios from 'axios';
import { Link, withRouter } from 'react-router-dom';
import {Map, InfoWindow, Marker, GoogleApiWrapper} from 'google-maps-react';


class AvioCompaniesList extends Component {
    state = {
        kompanija: "",
        avgrating: "",
        sveAviokompanije: [],
        destinacijeNaKojimaPosluje: []
    }

    componentDidMount() {

        axios.get('http://localhost:8080/aviocompany/getall').then(
            res => {
                this.setState({
                    sveAviokompanije: res.data
                })
            })
    }

    sortCompanies = (e) =>
    {
        axios.get('http://localhost:8080/aviocompany/sort/' + e.target.value).then(
            res => {
                this.setState({
                    sveAviokompanije: res.data
                })
                this.props.history.push('/allcompanieslist');
            })
    }

    showCompanyInfo = (id) => {
        this.props.history.push('/companyinfo/' + id);
    }

    render() {
        const style = {
            width: '85%',
            height: '40%',
            border: 'solid 3px whitesmoke'
        }
            const letovi = (this.state.sveAviokompanije.length) ? (this.state.sveAviokompanije.map(kompanija => {
                return(
                <div key={kompanija.idAvioKompanije}>
                <div className="center container">
                    <div className="row" >
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                    <span className="card-title"><b>Naziv aviokompanije: {kompanija.naziv}</b></span>
                                    <div className="divider white"></div>
                                    <br />
                                    <p>Naziv: &nbsp;&nbsp; {kompanija.naziv}</p>
                                    <p>Adresa: &nbsp;&nbsp;{kompanija.adresa}</p>
                                </div>
                                <div id="mapa">
                                    <Map google={this.props.google}  style={style} zoom={1}>
                                        <Marker onClick={this.onMarkerClick}
                                                name={kompanija.naziv}
                                                position={{lat: kompanija.lat, lng: kompanija.lng}}
                                                />
                                        <InfoWindow onClose={this.onInfoWindowClose}>
                                            <div>
                                            </div>
                                        </InfoWindow>
                                    </Map>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                <button className="btn waves-effect waves-light green" id="avioinfo-btn" onClick={() => { this.showCompanyInfo(kompanija.idAvioKompanije) }}>Informacije o aviokompaniji</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            )})) : (<div>Nema aviokompanija za prikaz</div>)

        return (
            <div>
                <br />
            <Link to="/companies"><button className="btn red center lighten-1 z-depth-0">Nazad</button></Link><br /><br />
            Sortiraj po:
            <select id="sort" className="browser-default" name="sort" onChange = {(e) => {this.sortCompanies(e)}}>
                                <option value="1">Naziv</option>
                                <option value="2">Grad</option>
                            </select>



                {letovi}
            </div>
        );
    }
}

export default GoogleApiWrapper({
    apiKey: ("AIzaSyC4EEa685SDSWi2LamKQUpU30rUF4GkHtA")
  })(withRouter(AvioCompaniesList))