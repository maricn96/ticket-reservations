    import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';


class EditBonus extends Component{

    state = {
        bonus:"",
        popust:""
    }

    componentDidMount() {
        axios.get("http://localhost:8080/bonus/")
            .then(res=>{
                this.setState({
                    bonus: res.data
                })
            })
    }

    handleChange = (e) => {
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    

    handleSubmit = (e) => {
        e.preventDefault();
        var token = localStorage.getItem('jwtToken');
        if(this.state.popust!=""){
            axios.put("http://localhost:8080/bonus/", {popust: this.state.popust}, { headers: { Authorization: `Bearer ${token}` } })
            .then(
                res=>{
                    alert("Uspesno izmenjen popust.");
                    this.props.history.push("/");
                }
            )
        }else{
            alert("Morate ispravno popuniti sva polja da bi uspesno izmenili popust.");
        }
        
    }

    render(){
        return(
            <div className = "center container">
                <h4 className="center">Popust po jednom bonus poenu:</h4>
                <div className = "center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="popust">Popust(u procentima):</label>
                        <input type="number" id="popust" step="0.01" onChange={this.handleChange}/>                    
                        <button className="btn waves-effect waves-light green">Izmeni</button>
                    </form>
                </div>
            </div>
        )

    }
}

export default withRouter(EditBonus)