import React, { Component } from 'react'
import axios from 'axios'
import {withRouter} from 'react-router-dom';

class Login extends Component{

    state = {
        username: "",
        password: "",
    }

    handleChange=(e)=>{
        this.setState({
            [e.target.id]: e.target.value
        })
    }

    handleSubmit = (e) => {
        e.preventDefault();
        var a=this.state.username; 
        var b=this.state.password;
        if(a!=="" && b!==""){
            //axios.post('http://localhost:8221/user/login', {email: a, lozinka: b})
            axios.post('http://localhost:8080/korisnik/login', {email: a, lozinka: b})
            .then(res => {
                localStorage.setItem('jwtToken', res.data);
                localStorage.setItem('email', a)
                this.props.setToken(localStorage.getItem('jwtToken'));
                this.props.setEmail(localStorage.getItem('email'));
                this.props.logIn();
                //axios.get("http://localhost:8221/user/all/" + localStorage.getItem('email'))
                axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
                .then(res=>{
                  localStorage.setItem('rola', res.data.rola);
                  localStorage.setItem('prviPutLogovan', res.data.prviPutLogovan);
                  var rola = localStorage.getItem('rola');
                  var prviPutLogovan = localStorage.getItem('prviPutLogovan')
                  console.log(rola + "  " + prviPutLogovan)
                  if(prviPutLogovan=="false" && (rola==="ADMIN_HOTELA" || rola==="ADMIN_RENT_A_CAR" || rola==="ADMIN_AVIO_KOMPANIJE" || rola==="MASTER_ADMIN")){
                    this.props.history.push("/promeni_sifru");
                  }else{
                    this.props.history.push("/");
                  }
                })
                
            }).catch(error=>{
                alert("Pogresno unet email ili lozinka.");
            })
        }else{
            alert("Unesite email i sifru kako biste se ulogovali.")
        }
    }

    render(){
        return(
            <div className="center container">
                <h4 className="center">Prijavi se:</h4>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="username">Email:</label>
                        <input type="text" id="username" onChange={this.handleChange} />
                        <label className="left black-text" htmlFor="password">Lozinka:</label>
                        <input type="password" id="password" onChange={this.handleChange} />
                        <button className="btn waves-effect waves-light green">Prijava</button>
                    </form>
                </div>
            </div>
        )

    }

}
export default withRouter(Login)