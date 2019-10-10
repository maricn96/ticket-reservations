import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';

class SinglePrices extends Component{

    state={
        prices:[],
    }

    componentDidMount() {
        var token = localStorage.getItem('jwtToken');
        axios.get("http://localhost:8080/cena/all/" + this.props.match.params.sobaId)
        .then(res=>{
            this.setState({
                prices: res.data
            })
            console.log(res.data);
        })
        
    }

    dodajClick=()=>{
        this.props.history.push('/admin/add_price/' + this.props.match.params.sobaId);
    }

    obrisiClick=(priceId)=>{
        var token = localStorage.getItem('jwtToken');
        axios.delete("http://localhost:8080/cena/" + priceId, { headers: { Authorization: `Bearer ${token}` } })
            .then(res=>{
                console.log(res.data);
                this.componentDidMount();
            })
    }

    render(){
        var {prices}=this.state;
        const pricesList = prices.length ? (prices.map(price => {
            return(
                <div className="center container" key={price.id}>
                    <div className="row">
                        <div className="col s12 m12">
                            <div className="card grey darken-2 card-panel hoverable">
                                <div className="card-content white-text left-align">
                                <span className="card-title"><b>{price.cenaNocenja}</b></span>
                                <div className="divider white"></div>
                                <br/>
                                <p>Datum od: {price.datumOd}</p>
                                <p>Datum do: {price.datumDo}</p>
                                </div>
                                <div className="divider white"></div>
                                <div className="card-action">
                                    <button className="btn waves-effect waves-light red" id="obrisiSobuBtn" onClick={()=>{this.obrisiClick(price.id)}}>Obrisi</button>
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
                    <button className="btn waves-effect waves-light green" id="dodajSobuBtn" onClick={()=>{this.dodajClick()}}>Dodaj</button>
                </div>
                <br/>
                {pricesList}
            </div>
        )
    }

}
export default withRouter(SinglePrices)