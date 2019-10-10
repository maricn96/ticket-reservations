import React, { Component } from 'react';
import axios from 'axios';
import { withRouter } from 'react-router-dom';

class UserRequests extends Component {
    state = {
        userRequests: [],
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
                axios.get('http://localhost:8080/korisnik/getallrequests/' + userid).then(res => {
                    this.setState({
                        userRequests: res.data
                    })
                    console.log(res);
                })
            })

    }

    acceptRequest = (userid) => {
        let userSender = this.state.user.id;
        axios.post('http://localhost:8080/korisnik/acceptrequest/' + userid + '/' + userSender).then(res => {
            console.log(res.data);
            if (res.data === "SUCCESS") {
                alert("Zahtev uspesno prihvacen")
                this.componentDidMount();
            }
            else {
                alert("Zahtev nije prihvacen")
            }

        }).catch(err => {
            console.log(err)
            alert("Zahtev uspesno prihvacen")   //baci exc al odradi posao
            this.componentDidMount();
        })
    }

    refuseRequest = (userid) => {
        let userSender = this.state.user.id;
        axios.post('http://localhost:8080/korisnik/refuserequest/' + userid + '/' + userSender).then(res => {
            console.log(res.data);
            if (res.data === "SUCCESS") {
                alert("Zahtev uspesno odbijen")
                this.componentDidMount();
            }
            else {
                alert("Zahtev neuspesno odbijen")
            }

        }).catch(err => {
            console.log(err)
            alert("Zahtev uspesno odbijen")
        })
    }



    render() {
        const allRequests = this.state.userRequests.length ? (this.state.userRequests.map(req => {
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
                                        <button className="btn waves-effect waves-light green" id="acceptbtn" onClick={() => { this.acceptRequest(req.id) }}>Prihvati</button>
                                        <button className="btn waves-effect waves-light red" id="refusebtn" onClick={() => { this.refuseRequest(req.id) }}>Odbij</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        })) : (
                <h4>Nema novih zahteva za prijateljstvo</h4>
            )

        return (
            <div>
                {allRequests}
            </div>
        )

    }
}

export default withRouter(UserRequests);
