import React, { Component } from 'react'
import axios from 'axios';
import { Link } from 'react-router-dom';
import Select from 'react-select';
import { withRouter } from 'react-router-dom';

class FlightsSearch extends Component {

    state = {
        destinacije: [],
            letovi: [],
            aviokompanija: "",
            klaseKojeLetSadrzi: [],
            tipoviPrtljagaPoLetu: [],

            takeOffDestination: "",
            landingDestination: "",
            type: "",
            number: "",
            klase: [],
            prtljag: [],

            datumPoletanja1: "",
            vremePoletanja1: "",
            datumPoletanja2: "",
            vremePoletanja2: "",

            showFlightInfo: true,
            showResBtn: false,

            flightsRes: [],

            advancedSearch: false
    }


    componentDidMount() {

        let local = localStorage.getItem("rola");
        if(local === 'KORISNIK')
        {
            this.setState({
                showResBtn: true
            })
        }
        else
        {
            this.setState({
                showResBtn: false
            })
        }

        axios.get('http://localhost:8080/destination/getall').then(
            res => {
                this.setState({
                    destinacije: res.data
                })
            }
        )

        axios.get('http://localhost:8080/flight/getall').then(
            res => {
                this.setState({
                    letovi: res.data
                })
            }
        )

        axios.get('http://localhost:8080/class/getall').then(
            res => {
                this.setState({
                    klaseKojeLetSadrzi: res.data
                })
            }
        )

        axios.get('http://localhost:8080/luggage/getall').then(
            res => {
                this.setState({
                    tipoviPrtljagaPoLetu: res.data
                })
            }
        )

    }

    changeMestoPolaska = (e) => {

        let mestoPolaska = "";

        this.state.destinacije.map(dest =>
            {
                if(e.target.value === dest.naziv)
                {
                    mestoPolaska = dest.idDestinacije
                }
            })

        this.setState({
            takeOffDestination: mestoPolaska
        })
        console.log(e.target.value);
    }

    changeMestoDolaska = (e) => {

        let mestoDolaska = "";

        this.state.destinacije.map(dest =>
            {
                if(e.target.value === dest.naziv)
                {
                    mestoDolaska = dest.idDestinacije
                }
            })

        this.setState({
             landingDestination: mestoDolaska
        })
        console.log(e.target.value);
    }

    changeDatum1 = (e) => {
        this.setState({
            datumPoletanja1: e.target.value 
       })
    }

    changeVreme1 = (e) => {
        console.log(e.target.value)
        this.setState({
            vremePoletanja1: e.target.value 
       })
    }

    changeDatum2 = (e) => {
        this.setState({
            datumPoletanja2: e.target.value 
       })
    }

    changeVreme2 = (e) => {
        this.setState({
            vremePoletanja2: e.target.value 
       })
    }

    changeTipLeta = (e) => {
        this.setState({
            type: e.target.value
        })
        console.log(e.target.value);
    }


    changeBrojOsoba = (e) => {
        this.setState({
            number: e.target.value
        })
    }

    changeKlaseKojeLetSadrzi = (klase) => {
        this.setState({ klase });
        console.log(this.state.klase)
    }

    changePrtljag = (prtljag) => {
        this.setState({ prtljag });
        console.log(this.state.prtljag)
    }

    handleBasicSearchSubmit = (e) => {
        e.preventDefault();

        let takeOffDestination = this.state.takeOffDestination;
        let landingDestination = this.state.landingDestination;
        let time1 = this.state.datumPoletanja1 + 'T' + this.state.vremePoletanja1 + ':00';
        let time2 = this.state.datumPoletanja2 + 'T' + this.state.vremePoletanja2 + ':00';
       
        let number = this.state.number;
        let object = new Object();
       

        axios.post('http://localhost:8080/flight/basicsearchflights', {
           time1, time2, takeOffDestination, landingDestination, number
        }).then(res => {
            console.log(res)
                if(res.status === 200)
                {
                    this.setState({
                        flightsRes: res.data
                    })
                }
                else
                {
                    this.setState({
                        flightsRes: []
                    }) 
                }
            }
        ).catch(error => {
            this.setState({
                flightsRes: []
            }) 
        })

    }

    handleSubmit = (e) => {
        e.preventDefault();

        let takeOffDestination = this.state.takeOffDestination;
        let landingDestination = this.state.landingDestination;
        let time1 = this.state.datumPoletanja1 + 'T' + this.state.vremePoletanja1 + ':00';
        let time2 = this.state.datumPoletanja2 + 'T' + this.state.vremePoletanja2 + ':00';
        let type = this.state.type;
        let number = this.state.number;
        // let klase = this.state.klase; 
        let klase = [];
        let object = new Object();
        for(let i = 0; i<this.state.klase.length; i++)
        {
            object.idKlase = this.state.klase[i].value;
            object.naziv = this.state.klase[i].label;
            klase.push(object)
            object = {}
        }

        let prtljag = [];
        for(let i = 0; i<this.state.prtljag.length; i++)
        {
            object.idPrtljaga = this.state.prtljag[i].value;
            object.opis = this.state.prtljag[i].label;
            prtljag.push(object)
            object = {}
        }

        axios.post('http://localhost:8080/flight/searchflights', {
           time1, time2, takeOffDestination, landingDestination, type, number, klase, prtljag
        }).then(res => {
            console.log(res)
                if(res.status === 200)
                {
                    this.setState({
                        flightsRes: res.data
                    })
                }
                else
                {
                    this.setState({
                        flightsRes: []
                    }) 
                }
            }
        ).catch(error => {
            this.setState({
                flightsRes: []
            }) 
        })


    }

    showAdvancedSearch = () =>
    {
        if(this.state.advancedSearch)
        {
            this.setState({
                advancedSearch: false
            })
        }
        else
        {
            this.setState({
                advancedSearch: true
            })
        }
    }

  

    showCompanyInfo = (idLeta) => {
        axios.get('http://localhost:8080/flight/getcompanyid/' + idLeta).then(
            res => {
                console.log(res);
                this.setState({
                    aviokompanija: res.data
                })
                this.props.history.push('/companyinfo/' + res.data);
            }
        )
        
    }

    reserveTicket = (idLeta) =>
    {
        this.props.history.push('/reservation/' + idLeta);
    }


    render() {

        //LISTE ZA SELECT
         //KLASE
         var { klase } = this.state.klase;

         var listaKlasa = [];
 
         this.state.klaseKojeLetSadrzi.map(klasa => {
             let options = new Object();
             options.value = klasa.idKlase;
             options.label = klasa.naziv;
             listaKlasa.push(options);
         })

         //TIP PRTLJAGA
         var { prtljag } = this.state.prtljag;
 
         var listaPrtljaga = [];
 
         this.state.tipoviPrtljagaPoLetu.map(prt => {
             let options = new Object();
             options.value = prt.idPrtljaga;
             options.label = prt.opis;
             listaPrtljaga.push(options);
         })


        let show = this.state.showResBtn;

        //osnovna pretraga

        const advancedS = this.state.advancedSearch ? (<div className="center container">
        <div>
            <div className="container">
                <form className="white" onSubmit={(e) => { this.handleSubmit(e) }}>
                    <h2 className="red-text lighten-1 center">Pretraga letova</h2>
                    <div className="container">
                        <label htmlFor="takeoffdest">Mesto polaska</label>
                        <div className="input-field">
                            <select id="takeoffdest" className="browser-default" name="destinationTakeOff" onChange = {(e) => {this.changeMestoPolaska(e)}}>
                                {this.state.destinacije.map(dest =>
                                    <option>{dest.naziv}</option>
                                )}
                            </select>
                        </div>
                        <label htmlFor="landingdest">Mesto dolaska</label>
                        <div className="input-field">
                            <select id="landingdest" className="browser-default" name="destinationLanding" onChange={(e) => {this.changeMestoDolaska(e)}}>
                                {this.state.destinacije.map(dest =>
                                    <option>{dest.naziv}</option>
                                )}
                            </select>
                        </div>
                        <label htmlFor="takeoff">Datum i vreme poletanja OD:</label>
                        <div className="input-field">
                            <input type="date" className="datepicker" id="takeoff" onChange={(e) => {this.changeDatum1(e)}} />
                            <input type="time" className="timepicker" id="takeofftime" onChange={(e) => {this.changeVreme1(e)}} />
                        </div>
                        <label htmlFor="landing">Datum i vreme poletanja DO:</label>
                        <div className="input-field">
                            <input type="date" className="datepicker" id="landing" onChange={(e) => {this.changeDatum2(e)}} />
                            <input type="time" className="timepicker" id="landingtime" onChange={(e) => {this.changeVreme2(e)}} />
                        </div>
                        <label htmlFor="fltype">Tip leta</label>
                        <div className="input-field">
                            <select id="fltype" className="browser-default" name="travelType" onChange={(e) => {this.changeTipLeta(e)}}>
                                {this.state.letovi.map(lett =>
                                    <option>{lett.tipPuta}</option>
                                )}
                            </select>
                        </div>

                        <label htmlFor="takeoffdest">Broj osoba</label>
                        <div className="input-field">
                            <input type="number" id="takeoffdest" className="browser-default" name="takenNumber" onChange={(e) => {this.changeBrojOsoba(e)}}/>

                        </div>

                        <label htmlFor="klase">Klase u avionu</label>
                        <Select
                            value={klase}
                            onChange={(klase) => { this.changeKlaseKojeLetSadrzi(klase) }}
                            options={listaKlasa}
                            id="klase" isMulti={true} />

                            <label htmlFor="prtljag">Tipovi prtljaga</label>
                        <Select
                            value={prtljag}
                            onChange={(prtljag) => { this.changePrtljag(prtljag) }}
                            options={listaPrtljaga}
                            id="prtljag" isMulti={true} />

                        <div className="input-field">
                            <input type="submit" value="Pretrazi" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                        </div>
                    </div>
                </form>
                <button onClick={this.showAdvancedSearch} className="btn blue center lighten-1 z-depth-0">Osnovna pretraga</button>
            </div>
        </div>
        
            <br/>
            
        </div>) : (<div>
            <div className="container">
                <form className="white" onSubmit={(e) => { this.handleBasicSearchSubmit(e) }}>
                    <h2 className="red-text lighten-1 center">Pretraga letova</h2>
                    <div className="container">
                        <label htmlFor="takeoffdest">Mesto polaska</label>
                        <div className="input-field">
                            <select id="takeoffdest" className="browser-default" name="destinationTakeOff" onChange = {(e) => {this.changeMestoPolaska(e)}}>
                                {this.state.destinacije.map(dest =>
                                    <option>{dest.naziv}</option>
                                )}
                            </select>
                        </div>
                        <label htmlFor="landingdest">Mesto dolaska</label>
                        <div className="input-field">
                            <select id="landingdest" className="browser-default" name="destinationLanding" onChange={(e) => {this.changeMestoDolaska(e)}}>
                                {this.state.destinacije.map(dest =>
                                    <option>{dest.naziv}</option>
                                )}
                            </select>
                        </div>
                        <label htmlFor="takeoff">Datum i vreme poletanja OD:</label>
                        <div className="input-field">
                            <input type="date" className="datepicker" id="takeoff" onChange={(e) => {this.changeDatum1(e)}} />
                            <input type="time" className="timepicker" id="takeofftime" onChange={(e) => {this.changeVreme1(e)}} />
                        </div>
                        <label htmlFor="landing">Datum i vreme poletanja DO:</label>
                        <div className="input-field">
                            <input type="date" className="datepicker" id="landing" onChange={(e) => {this.changeDatum2(e)}} />
                            <input type="time" className="timepicker" id="landingtime" onChange={(e) => {this.changeVreme2(e)}} />
                        </div>
                        

                        <label htmlFor="takeoffdest">Broj osoba</label>
                        <div className="input-field">
                            <input type="number" id="takeoffdest" className="browser-default" name="takenNumber" onChange={(e) => {this.changeBrojOsoba(e)}}/>
                        </div>
                        <div className="input-field">
                            <input type="submit" value="Pretrazi" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                        </div>
                    </div>
                </form>
                <button onClick={this.showAdvancedSearch} className="btn blue center lighten-1 z-depth-0">Napredna pretraga</button>
            </div>
        </div>) 

        const flightsList = this.state.flightsRes.length ? (this.state.flightsRes.map(flight => {
            return (
                <div className="center container" key={flight.idLeta}>
                <h3 className="left-align container">Rezultati pretrage:</h3>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>Broj leta: {flight.brojLeta}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Vreme poletanja: {flight.vremePoletanja}</p>
                                <p>Vreme sletanja: {flight.vremeSletanja}</p>
                                <p>Destinacija poletanja: {flight.destinacijaPoletanja.naziv}</p>
                                <p>Destinacija sletanja: {flight.destinacijaSletanja.naziv}</p>
                                <p>Broj slobodnih mesta: {flight.brojMesta - flight.brojOsoba}</p>
                                <p>Broj presedanja: {flight.brojPresedanja}</p>
                                <p>Duzina putovanja: {flight.duzinaPutovanja}</p>
                                <p>Tip puta: {flight.tipPuta}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                <button className="btn waves-effect waves-light green" id="avioinfo-btn" onClick={() => { this.showCompanyInfo(flight.idLeta) }}>Informacije o aviokompaniji</button>
                                {
                                    (show) ? (<button className="btn waves-effect waves-light red" id="rezervisi-btn" onClick={() => { this.reserveTicket(flight.idLeta) }}>Rezervacija</button>) : (<div></div>)
                                }
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
            <h3>Nema rezultata pretrage</h3>
        )

        return(
            <div><br />
            <Link to="/companies"><button className="btn red center lighten-1 z-depth-0">Nazad</button></Link>
            {advancedS}
            {flightsList}
            </div>
        )
        
    }

};

export default withRouter(FlightsSearch)