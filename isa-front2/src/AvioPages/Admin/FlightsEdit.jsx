import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import Select from 'react-select';

class FlightsEdit extends Component {
    state = {
        aviokompanijaPovucena: "",
        user: "",
        let: "",

        datumPoletanja1fetch: "",
        vremePoletanja1fetch: "",

        datumSletanja2fetch: "",
        vremeSletanja2fetch: "",

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
                axios.get('http://localhost:8080/flight/getone/' + this.props.match.params.flightid).then(
                    res => {
                        this.setState({
                            let: res.data,
                            vremePoletanja: res.data.vremePoletanja,
                            vremeSletanja: res.data.vremeSletanja,
                            destinacijaPoletanja: res.data.destinacijaPoletanja,
                            destinacijaSletanja: res.data.destinacijaSletanja,
                            brojLeta: res.data.brojLeta,
                            duzinaPutovanja: res.data.duzinaPutovanja,
                            brojPresedanja: res.data.brojPresedanja,
                            tipPuta: res.data.tipPuta,
                            brojMesta: res.data.brojMesta,
                            cenaKarte: res.data.cenaKarte
                        })
                    }


                ).then(axios.get('http://localhost:8080/aviocompany/getone/' + idKompanije, { headers: { Authorization: `Bearer ${token}` } }).then(res => {
                    this.setState({
                        aviokompanijaPovucena: res.data
                    })
                })

                )
            })

        axios.get('http://localhost:8080/destination/getall').then(
            res => {
                this.setState({
                    destinacije: res.data
                })
            }
        )

        axios.get('http://localhost:8080/class/getall').then(
            res => {
                this.setState({
                    klase: res.data
                })
            }
        )

        axios.get('http://localhost:8080/service/getall').then(
            res => {
                this.setState({
                    dodatneUsluge: res.data
                })
            }
        )

        axios.get('http://localhost:8080/luggage/getall').then(
            res => {
                this.setState({
                    prtljag: res.data
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
    *   OPERACIJE ZA IZMENU LETA
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

        var LET_ID = this.state.let.idLeta;

        let idLeta = LET_ID;

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
            opis: this.state.aviokompanijaPovucena.opis
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

        let tipoviPrtljagaPoLetu = [];
        for(let i = 0; i<this.state.tipoviPrtljagaPoLetu.length; i++)
        {
            object.idPrtljaga = this.state.tipoviPrtljagaPoLetu[i].value;
            object.opis = this.state.tipoviPrtljagaPoLetu[i].label;
            tipoviPrtljagaPoLetu.push(object)
            object = {}
        }


        axios.put("http://localhost:8080/flight/update/" + LET_ID, {
            idLeta, brojLeta, vremePoletanja, vremeSletanja, duzinaPutovanja, brojPresedanja, tipPuta, brojMesta, cenaKarte,
            aviokompanija, destinacijaPoletanja, destinacijaSletanja, destinacijePresedanja, klaseKojeLetSadrzi,
            dodatneUslugeKojeLetSadrzi, tipoviPrtljagaPoLetu, prosecnaOcena: null, brojOsoba: 0, ukupanPrihod: 0
        }, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Uspesno ste izmenili let")
                this.props.history.push("/adflights");
            }).catch(error => {
                alert("Uspesno ste izmenili let"); //baca null exc zbog prtljaga, zato i ovde nek je uspesno
            })
    }

    render() {
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

        //TIPOVI PRTLJAGA
        var { tipoviPrtljagaPoLetu } = this.state;
        var { prtljag } = this.state;

        var listaPrtljaga = [];

        prtljag.map(usl => {
            let options = new Object();
            options.value = usl.idPrtljaga;
            options.label = usl.opis;
            listaPrtljaga.push(options);
        })


        return (
            <div>
                <br />
                <div className="container">
                    <form className="white" onSubmit={(e) => { this.handleSubmit(e) }}>
                        <h2 className="red-text lighten-1 center">Izmeni let</h2>
                        <div className="container">

                            <label htmlFor="brojLeta">Broj leta</label>
                            <div className="input-field">
                                <input type="number" id="brojLeta" defaultValue={this.state.let.brojLeta} className="browser-default" name="brojLeta" onChange={(e) => { this.changeInputField(e) }} />
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
                            <input type="number" defaultValue={this.state.let.duzinaPutovanja} id="duzinaPutovanja" onChange={(e) => { this.changeInputField(e) }} />

                            <label htmlFor="brojPresedanja">Broj presedanja</label>
                            <input type="number" defaultValue={this.state.let.brojPresedanja} id="brojPresedanja" onChange={(e) => { this.changeInputField(e) }} />

                            <label htmlFor="destinacijePresedanja">Mesta u kojima se preseda</label>
                            <Select
                                value={destinacijePresedanja}
                                onChange={(destinacijePresedanja) => { this.changeDestinacijePresedanja(destinacijePresedanja) }}
                                options={listaDestinacija}
                                id="destinacijePresedanja" isMulti={true} />

                            <label htmlFor="tipPuta">Tip leta</label>
                            <input type="text" defaultValue={this.state.let.tipPuta} id="tipPuta" onChange={(e) => { this.changeInputField(e) }} />


                            <label htmlFor="brojMesta">Broj mesta za rezervaciju</label>
                            <input type="number" defaultValue={this.state.let.brojMesta} id="brojMesta" onChange={(e) => { this.changeInputField(e) }} />

                            <label htmlFor="cenaKarte">Cena karte</label>
                            <input type="number" defaultValue={this.state.let.cenaKarte} id="cenaKarte" onChange={(e) => { this.changeInputField(e) }} />

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

                                <label htmlFor="tipoviPrtljagaPoLetu">Tipovi prtljaga dozvoljeni na letu</label>
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
            </div>
        );
    }
}

export default withRouter(FlightsEdit);
