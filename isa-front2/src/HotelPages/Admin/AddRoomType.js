import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
class AddRoomType extends Component{

    state={
        naziv:"",
        hotel:""
    }

    componentDidMount() {
       axios.get("http://localhost:8080/hotel/" + this.props.match.params.hotelId)
        .then(res=>{
            console.log(res.data);
             this.setState({
                hotel: res.data
             })
       })
    }

    handleChange = (e) => { //za inpute
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit = (e) => {
        var token = localStorage.getItem('jwtToken');
        e.preventDefault();
        if(this.state.naziv!==""){
            axios.post("http://localhost:8080/tip_sobe/", {naziv: this.state.naziv, hotel: this.state.hotel}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Uspesno dodat novi tip.")
                this.props.history.push("/admin/types");
            })
        }else{
            alert("Sva polja moraju biti ispravno popunjena.")
        }
    }

    render(){
        return(
            <div className="center container">
                <br/>
                <h3>Dodavanje tipa sobe:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="naziv">Naziv tipa:</label>
                        <input type="text" id="naziv" onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green" id="dodajTipClick">Dodaj</button>
                    </form>
                </div>
            </div>
        )
    }

}
export default withRouter(AddRoomType)