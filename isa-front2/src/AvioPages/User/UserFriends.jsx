import React, { Component } from 'react';
import axios from 'axios'

class UserFriends extends Component {
    state = {  
        userFriends: [],
        user: ""
    }

    componentDidMount() {
        axios.get("http://localhost:8080/korisnik/all/" + localStorage.getItem('email'))
        .then(res => {
            this.setState({
                user: res.data
            })
            let userid = res.data.id
        console.log("USERID : " + userid)
                axios.get('http://localhost:8080/korisnik/getallfriends/' + userid).then(res =>
                {
                    this.setState({
                        userFriends: res.data
                    })
                    console.log(res);
                })
        })
        
    }

    deleteFriend = (userid) =>
    {
        
        let userSender = this.state.user.id;
        console.log(userSender, userid)
        axios.post('http://localhost:8080/korisnik/deletefriend/' + userid + '/' + userSender).then(res =>
                {
                    console.log(res.data);
                    if(res.data === "SUCCESS")
                    {
                        alert("Prijatelj uspesno obrisan")
                         this.componentDidMount();
                    }
                    else
                    {
                        alert("Prijatelj nije obrisan")   
                    }
                    
                }).catch(err => {
                    console.log(err)
                    alert("Prijatelj uspesno obrisan")  
                })
    }

    

    

    render() {
         const allRequests = this.state.userFriends.length ? (this.state.userFriends.map(req => {
            return (
                <div key={req.id}>
                    <div className="center container">
                        <div className="row">
                            <div className="col s12 m12">
                                <div className="card grey darken-2 card-panel hoverable">
                                    <div className="card-content white-text left-align">
                                        <span className="card-title"><b>Email: {req.email}</b></span>
                                        <div className="divider white"></div>
                                        <br />
                                        <p>Ime: {req.ime}</p>
                                        <p>Prezime: {req.prezime}</p>
                                    </div>
                                    <div className="divider white"></div>
                                    <div className="card-action">
                                        <button className="btn waves-effect waves-light red" id="refusebtn" onClick={() => { this.deleteFriend(req.id) }}>Obrisi prijatelja</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
                <h4>Lista prijatelja je prazna</h4>
            )

            return(
                <div>
                    {allRequests}
                </div>
            )

    }
}

export default UserFriends;
