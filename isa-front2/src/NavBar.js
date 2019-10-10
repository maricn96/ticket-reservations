import React, { Component } from 'react'
import {Link, withRouter} from "react-router-dom"

class NavBar extends Component{

    odjava = () =>{
        localStorage.setItem('jwtToken', undefined);
        localStorage.setItem('email', undefined);
        localStorage.setItem('rola', undefined);
        localStorage.setItem('prviPutLogovan', undefined);
        localStorage.setItem("isLogged", false);
        sessionStorage.setItem("vremePoletanja", undefined);
        sessionStorage.setItem("vremeSletanja", undefined);
        sessionStorage.setItem("flag", "0");
        sessionStorage.setItem("ticketid", undefined);
        this.props.logOut();  
        this.props.history.push("/");    
    }

    render(){
        //var isLogged = this.props.loggedIn;
        //var isLogged = localStorage.getItem("isLogged")
        var rola = localStorage.getItem('rola');
        var ispis="";
        if(rola==="KORISNIK"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/userinvitations">Pozivnice za putovanje</Link></li>
                        <li><Link to="/account">Nalog</Link></li>
                        <li><Link to="/my_reservations">Moje rezervacije</Link></li>
                        <li><Link to="/" onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }else if(rola==="ADMIN_AVIO_KOMPANIJE"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/adcompanies/edit">Avio kompanije</Link></li>
                        <li><Link to="/adflights">Letovi</Link></li>
                        <li><Link to="/adother">Ostalo</Link></li>
                        <li><Link to="/adreports">Izvestaji</Link></li>
                        <li><Link to="/adinfo">Profil</Link></li>
                        <li><Link to="/" onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }else if(rola==="ADMIN_HOTELA"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/edit/hotel">Izmeni hotel</Link></li>
                        <li><Link to="/admin/rooms">Sobe</Link></li>
                        <li><Link to="/admin/types">Tipovi sobe</Link></li>
                        <li><Link to="/cenovnik">Cenovnik</Link></li>
                        <li><Link to="/izvestaji">Izvestaji</Link></li>
                        <li><Link to="/admin/services">Usluge</Link></li>
                        <li><Link to="/profile">Profil</Link></li>
                        <li><Link to="/" onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }else if(rola==="ADMIN_RENT_A_CAR"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/login">Rent</Link></li>
                        <li><Link to="/" onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }else if(rola==="MASTER_ADMIN"){
            ispis = <ul id="nav-mobile" className="right hide-on-med-and-down">
                        <li><Link to="/master/add_admin">Dodaj admina</Link></li>
                        <li><Link to="/master/add_avio">Kreiraj aviokompaniju</Link></li>
                        <li><Link to="/master/add_hotel">Kreiraj hotel</Link></li>
                        <li><Link to="/master/add_rent">Kreiraj rent-a-car</Link></li>
                        <li><Link to="/master/edit_bonus">Bonus poeni</Link></li>
                        <li><Link to="/" onClick={this.odjava}>Odjava</Link></li>
                    </ul>
        }
        return(          
            <nav className="nav-wrapper red darken-3">
                <div className="container">
                    <Link to='/' className="brand-logo">Home</Link>
                    { (localStorage.getItem("isLogged") && ispis!=="") ? (
                        <div>
                            {ispis}
                        </div>
                    ):(
                        <ul id="nav-mobile" className="right hide-on-med-and-down">
                            <li><Link to="/login">Prijava</Link></li>
                            <li><Link to="/register">Registracija</Link></li>
                        </ul>
                    )}
                </div>
            </nav>       
        )
    }

}
export default withRouter(NavBar)