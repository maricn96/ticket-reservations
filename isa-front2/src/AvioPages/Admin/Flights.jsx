import React, { Component } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';
import Select from 'react-select';

class Flights extends Component {
    state = {
        aviokompanijaPovucena: "",
        user: "",
        letovi: [],
        destinacije: [],
        klase: [],
        dodatneUsluge: [],
        prtljag: [],
        clickAdd: false,

        brojLeta: "",
        vremePoletanja: "",
        vremeSletanja: "",
        destinacijaPoletanja: "",
        destinacijaSletanja: "",
        duzinaPutovanja: "",
        brojPresedanja: "",
        destinacijePresedanja: [],

        tipPuta: "",
        brojMesta: "",
        cenaKarte: "",

        tipoviPrtljagaPoLetu: [], //au brt kako ovo.
        klaseKojeLetSadrzi: [],
        dodatneUslugeKojeLetSadrzi: [],


        //POMOCNE
        datumPoletanja1: "",
        datumPoletanja2: "",
        vremePoletanja1: "",
        vremePoletanja2: ""

    }

    componentDidMount() {
        var token = localStorage.getItem('jwtToken');
        var idKompanije = "";
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
            .then(res => {
                console.log(res)
                this.setState({
                    user: res.data
                })
                idKompanije = res.data.zaduzenZaId;
                axios.get('http://localhost:8080/flight/getall/' + idKompanije, { headers: { Authorization: `Bearer ${token}` } }).then( //treba da vrati samo letove za datu aviokompaniju
                    res => {
                        this.setState({
                            letovi: res.data
                        })
                    }


                ).then(axios.get('http://localhost:8080/aviocompany/getone/' + idKompanije, { headers: { Authorization: `Bearer ${token}` } }).then(res => {
                    this.setState({
                        aviokompanijaPovucena: res.data
                    })
                })

                )

                let kompanija = this.state.user.zaduzenZaId;
        axios.get('http://localhost:8080/destination/getalldestsbycompany/' + kompanija).then(
            res => {
                console.log("DESTINACIJE: ")
                console.log(res.data)
                this.setState({
                    destinacije: res.data
                })
                
            }
        )
    }
            
        )

        axios.get('http://localhost:8080/class/getall').then(
            res => {
                this.setState({
                    klase: res.data
                })
            }
        )

        axios.get('http://localhost:8080/luggage/getall').then(
            res => {
                console.log("PRTLJAG: ")
                console.log(res.data);
                this.setState({
                    prtljag: res.data
                })
            })

        axios.get('http://localhost:8080/service/getall').then(
            res => {
                this.setState({
                    dodatneUsluge: res.data
                })
            }
        )
    }

    changeFlight = (idLeta) => {
        this.props.history.push('/adflightsedit/' + idLeta);
    }

    toggleAddBtn = () => {
        if (this.state.clickAdd) {
            this.setState({
                clickAdd: false
            })
        }
        else {
            this.setState({
                clickAdd: true
            })
        }
    }


    /*
    *   OPERACIJE ZA DODAVANJE NOVOG LETA
    */

    changeInputField = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    changeDatum1 = (e) => {
        this.setState({
            datumPoletanja1: e.target.value
        })
    }

    changeVreme1 = (e) => {
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

    changeDestinacijaPoletanja = (destinacijaPoletanja) => {
        this.setState({ destinacijaPoletanja });
        console.log(this.state.destinacijaPoletanja)
    }

    changeDestinacijaSletanja = (destinacijaSletanja) => {
        this.setState({ destinacijaSletanja });
        console.log(this.state.destinacijaSletanja)
    }

    changeDestinacijePresedanja = (destinacijePresedanja) => {
        this.setState({ destinacijePresedanja });
        console.log(this.state.destinacijePresedanja)
    }

    changeKlaseKojeLetSadrzi = (klaseKojeLetSadrzi) => {
        this.setState({ klaseKojeLetSadrzi });
        console.log(this.state.klaseKojeLetSadrzi)
    }

    changeDodatneUslugeKojeLetSadrzi = (dodatneUslugeKojeLetSadrzi) => {
        this.setState({ dodatneUslugeKojeLetSadrzi });
        console.log(this.state.dodatneUslugeKojeLetSadrzi)
    }

    changeTipoviPrtljagaPoLetu = (tipoviPrtljagaPoLetu) => {
        this.setState({ tipoviPrtljagaPoLetu });
        console.log(this.state.tipoviPrtljagaPoLetu)
    }


    handleSubmit = (e) => {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken');

        let idLeta = 9999;
        let brojLeta = this.state.brojLeta;
        let vremePoletanja = this.state.datumPoletanja1 + 'T' + this.state.vremePoletanja1 + ':00';
        let vremeSletanja = this.state.datumPoletanja2 + 'T' + this.state.vremePoletanja2 + ':00';
        let duzinaPutovanja = this.state.duzinaPutovanja;
        let brojPresedanja = this.state.brojPresedanja;
        let tipPuta = this.state.tipPuta;
        let brojMesta = this.state.brojMesta;
        let cenaKarte = this.state.cenaKarte;
        let aviokompanija = {
            idAvioKompanije: this.state.user.zaduzenZaId,
            naziv: this.state.aviokompanijaPovucena.naziv,
            adresa: this.state.aviokompanijaPovucena.adresa,
            opis: this.state.aviokompanijaPovucena.opis,
            destinacijeNaKojimaPosluje: this.state.aviokompanijaPovucena.destinacijeNaKojimaPosluje
        }

        let destinacijaPoletanja = {
            idDestinacije: this.state.destinacijaPoletanja.value,
            naziv: this.state.destinacijaPoletanja.label,
            informacije: ""
        }

        let destinacijaSletanja = {
            idDestinacije: this.state.destinacijaSletanja.value,
            naziv: this.state.destinacijaSletanja.label,
            informacije: ""
        }

        let destinacijePresedanja = [];
        let object = new Object();
        for(let i = 0; i<this.state.destinacijePresedanja.length; i++)
        {
            object.idDestinacije = this.state.destinacijePresedanja[i].value;
            object.naziv = this.state.destinacijePresedanja[i].label;
            destinacijePresedanja.push(object)
            object = {}
        }


        let klaseKojeLetSadrzi = []

        for(let i = 0; i<this.state.klaseKojeLetSadrzi.length; i++)
        {
            object.idKlase = this.state.klaseKojeLetSadrzi[i].value;
            object.naziv = this.state.klaseKojeLetSadrzi[i].label;
            klaseKojeLetSadrzi.push(object)
            object = {}
        }

        let dodatneUslugeKojeLetSadrzi = []
        for(let i = 0; i<this.state.dodatneUslugeKojeLetSadrzi.length; i++)
        {
            object.idDodatneUsluge = this.state.dodatneUslugeKojeLetSadrzi[i].value;
            object.naziv = this.state.dodatneUslugeKojeLetSadrzi[i].label;
            dodatneUslugeKojeLetSadrzi.push(object)
            object = {}
        }

        let tipoviPrtljagaPoLetu = []
        for(let i = 0; i<this.state.tipoviPrtljagaPoLetu.length; i++)
        {
            object.idPrtljaga = this.state.tipoviPrtljagaPoLetu[i].value;
            object.opis = this.state.tipoviPrtljagaPoLetu[i].label;
            tipoviPrtljagaPoLetu.push(object)
            object = {}
        }

        


        if (idLeta !== "" && brojLeta !== "" && vremePoletanja !== "" && vremeSletanja !== "" && duzinaPutovanja !== "" && brojPresedanja !== "" && tipPuta !== "" &&
            brojMesta !== "" && aviokompanija !== "" && destinacijaPoletanja !== "" && destinacijaSletanja !== "" && destinacijePresedanja !== "" &&
            klaseKojeLetSadrzi !== "" && dodatneUslugeKojeLetSadrzi !== "") {
            axios.post("http://localhost:8080/flight/add/", {
                idLeta, brojLeta, vremePoletanja, vremeSletanja, duzinaPutovanja, brojPresedanja, tipPuta, brojMesta, cenaKarte,
                aviokompanija, destinacijaPoletanja, destinacijaSletanja, destinacijePresedanja, klaseKojeLetSadrzi, tipoviPrtljagaPoLetu,
                dodatneUslugeKojeLetSadrzi, prosecnaOcena: 0, brojOsoba: 0, ukupanPrihod: 0
            }, { headers: { Authorization: `Bearer ${token}` } })
                .then(res => {
                    if(res.data)
                        alert("Uspesno ste dodali novi let")
                    else
                    alert("Broj leta vec postoji")
                    this.props.history.push("/adflights");
                }).catch(error => {
                    alert("Broj leta vec postoji ili postoje prazna polja!?.");
                })
        } else {
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    render() {
        const flightsList = this.state.letovi.length ? (this.state.letovi.map(flight => {
            return (
                <div key={flight.idLeta}>
                    <div className="center container">
                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Broj leta: {flight.brojLeta}</b></span>
                                        <div className="divider white"></div>
                                        <br />
                                        <p>Destinacija poletanja: {flight.destinacijaPoletanja.naziv}</p>
                                        <p>Destinacija sletanja: {flight.destinacijaSletanja.naziv}</p>
                                        <p>Vreme poletanja: {flight.vremePoletanja}</p><br />
                                        <p>Vreme sletanja: {flight.vremeSletanja}</p><br />
                                        <p>Broj slobodnih mesta: {flight.brojMesta - flight.brojOsoba}</p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                        <button className="btn waves-effect waves-light red" id="changebtn" onClick={() => { this.changeFlight(flight.idLeta) }}>Izmeni</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
                <h3>Nema letova</h3>
            )

        //DESTINACIJE
        var { destinacijaPoletanja } = this.state;
        var { destinacijaSletanja } = this.state;
        var { destinacijePresedanja } = this.state;

        var { destinacije } = this.state;
        var listaDestinacija = [];

        destinacije.map(dest => {
            let options = new Object();
            options.value = dest.idDestinacije;
            options.label = dest.naziv;
            listaDestinacija.push(options);
        })

        //KLASE
        var { klaseKojeLetSadrzi } = this.state;
        var { klase } = this.state;

        var listaKlasa = [];

        klase.map(klasa => {
            let options = new Object();
            options.value = klasa.idKlase;
            options.label = klasa.naziv;
            listaKlasa.push(options);
        })

        //DODATNE USLUGE
        var { dodatneUslugeKojeLetSadrzi } = this.state;
        var { dodatneUsluge } = this.state;

        var listaDodatnihUsluga = [];

        dodatneUsluge.map(usl => {
            let options = new Object();
            options.value = usl.idDodatneUsluge;
            options.label = usl.naziv;
            listaDodatnihUsluga.push(options);
        })

        //PRTLJAG
        var { tipoviPrtljagaPoLetu } = this.state.tipoviPrtljagaPoLetu;

        var listaPrtljaga = [];

        this.state.prtljag.map(usl => {
            let options = new Object();
            options.value = usl.idPrtljaga;
            options.label = usl.opis;
            listaPrtljaga.push(options);
        })


        return (
            <div>
                <br />
                <button className="btn waves-effect waves-light red" id="changebtn" onClick={() => { this.toggleAddBtn() }}>Dodaj novi let</button>
                {(this.state.clickAdd) ? (<div className="container">
                    <form className="white" onSubmit={(e) => { this.handleSubmit(e) }}>
                        <h2 className="red-text lighten-1 center">Dodaj novi let</h2>
                        <div className="container">

                            <label htmlFor="brojLeta">Broj leta</label>
                            <div className="input-field">
                                <input type="number" id="brojLeta" className="browser-default" name="brojLeta" onChange={(e) => { this.changeInputField(e) }} />
                            </div>

                            <label htmlFor="takeoff">Datum i vreme poletanja</label>
                            <div className="input-field">
                                <input type="date" className="datepicker" id="takeoff" onChange={(e) => { this.changeDatum1(e) }} />
                                <input type="time" className="timepicker" id="takeofftime" onChange={(e) => { this.changeVreme1(e) }} />
                            </div>
                            <label htmlFor="landing">Datum i vreme sletanja</label>
                            <div className="input-field">
                                <input type="date" className="datepicker" id="landing" onChange={(e) => { this.changeDatum2(e) }} />
                                <input type="time" className="timepicker" id="landingtime" onChange={(e) => { this.changeVreme2(e) }} />
                            </div>
                            <label htmlFor="destinacijaPoletanja">Mesto poletanja</label>
                            <Select
                                value={destinacijaPoletanja}
                                onChange={(destinacijaPoletanja) => { this.changeDestinacijaPoletanja(destinacijaPoletanja) }}
                                options={listaDestinacija}
                                id="destinacijaPoletanja" />


                            <label htmlFor="destinacijaSletanja">Mesto sletanja</label>
                            <Select
                                value={destinacijaSletanja}
                                onChange={(destinacijaSletanja) => { this.changeDestinacijaSletanja(destinacijaSletanja) }}
                                options={listaDestinacija}
                                id="destinacijaSletanja" />

                            <label htmlFor="duzinaPutovanja">Duzina leta [km]</label>
                            <input type="number" id="duzinaPutovanja" onChange={(e) => { this.changeInputField(e) }} />

                            <label htmlFor="brojPresedanja">Broj presedanja</label>
                            <input type="number" id="brojPresedanja" onChange={(e) => { this.changeInputField(e) }} />

                            <label htmlFor="destinacijePresedanja">Mesta u kojima se preseda</label>
                            <Select
                                value={destinacijePresedanja}
                                onChange={(destinacijePresedanja) => { this.changeDestinacijePresedanja(destinacijePresedanja) }}
                                options={listaDestinacija}
                                id="destinacijePresedanja" isMulti />

                            <label htmlFor="tipPuta">Tip leta</label>
                            <input type="text" id="tipPuta" onChange={(e) => { this.changeInputField(e) }} />


                            <label htmlFor="brojMesta">Broj mesta za rezervaciju</label>
                            <input type="number" id="brojMesta" onChange={(e) => { this.changeInputField(e) }} />

                            <label htmlFor="cenaKarte">Cena karte (€)</label>
                            <input type="number" id="cenaKarte" onChange={(e) => { this.changeInputField(e) }} />


                            <label htmlFor="klaseKojeLetSadrzi">Klase u avionu</label>
                            <Select
                                value={klaseKojeLetSadrzi}
                                onChange={(klaseKojeLetSadrzi) => { this.changeKlaseKojeLetSadrzi(klaseKojeLetSadrzi) }}
                                options={listaKlasa}
                                id="klaseKojeLetSadrzi" isMulti={true} />

                            <label htmlFor="dodatneUslugeKojeLetSadrzi">Dodatne usluge u avionu</label>
                            <Select
                                value={dodatneUslugeKojeLetSadrzi}
                                onChange={(dodatneUslugeKojeLetSadrzi) => { this.changeDodatneUslugeKojeLetSadrzi(dodatneUslugeKojeLetSadrzi) }}
                                options={listaDodatnihUsluga}
                                id="dodatneUslugeKojeLetSadrzi" isMulti={true} />

                                <label htmlFor="tipoviPrtljagaPoLetu">Tipovi prtljaga koji su dozvoljeni na letu</label>
                            <Select
                                value={tipoviPrtljagaPoLetu}
                                onChange={(tipoviPrtljagaPoLetu) => { this.changeTipoviPrtljagaPoLetu(tipoviPrtljagaPoLetu) }}
                                options={listaPrtljaga}
                                id="tipoviPrtljagaPoLetu" isMulti={true} />

                            <div className="input-field">
                                <input type="submit" value="Sacuvaj" className="btn blue lighten-1 z-depth-0" /> <br /> <br />
                            </div>
                        </div>
                    </form>
                </div>
                ) : (<div></div>)}
                <div className="center container">
                    <h3 className="left-align container">Spisak letova:</h3>
                </div>
                {flightsList}
            </div>
        )

    }
}

export default withRouter(Flights);
