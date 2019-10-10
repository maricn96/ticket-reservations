import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import NavBar from './NavBar';
import Home from './Home';
import "./card.css"
import Hotels from './HotelPages/Hotels';
import Rooms from './HotelPages/Rooms';
import RentACars from './RentACarPages/RentACars';
import Login from './Login';
import Register from './Register';
import Services from './HotelPages/Services';
import EditHotel from './HotelPages/Admin/EditHotel';
import HotelRooms from './HotelPages/Admin/HotelRooms';
import AddRoom from "./HotelPages/Admin/AddRoom";
import EditRoom from './HotelPages/Admin/EditRoom';
import HotelServices from './HotelPages/Admin/HotelServices';
import AddService from './HotelPages/Admin/AddService';
import EditService from './HotelPages/Admin/EditService';
import AdminProfile from './HotelPages/Admin/AdminProfile';
import Cenovnik from './HotelPages/Admin/Cenovnik';
import EditPrice from './HotelPages/Admin/EditPrice';
import AddSinglePrice from './HotelPages/Admin/AddSinglePrice';
import SinglePrices from './HotelPages/Admin/SinglePrices';
import Izvestaji from './HotelPages/Admin/Izvestaji';
import AirlineIndex from './AvioPages/AirlineIndex';
import FlightsSearch from './AvioPages/User/FlightsSearch';
import FlightInfo from './AvioPages/User/FlightInfo';
import AvioCompanyInfo from './AvioPages/User/AvioCompanyInfo';
import Reservation from './AvioPages/User/Reservation';
import AvioCompanies from './AvioPages/Admin/AvioCompanies';
import Flights from './AvioPages/Admin/Flights';
import Reports from './AvioPages/Admin/Reports';
import AdminInfo from './AvioPages/Admin/AdminInfo';
import AddAdmin from './MasterAdminPages/AddAdmin';
import FlightsEdit from './AvioPages/Admin/FlightsEdit';
import Account from './AvioPages/User/Account';
import AddHotel from './MasterAdminPages/AddHotel';
import Other from './AvioPages/Admin/Other';
import AddAvio from './MasterAdminPages/AddAvio';
import AddRent from './MasterAdminPages/AddRent';
import ReservationForm from './HotelPages/ReservationForm';
import UserInvitations from './AvioPages/User/UserInvitations';
import MyReservations from './MyReservations';
import ExpressTicket from './AvioPages/User/ExpressTicket';
import Ocena from './HotelPages/Ocena';
import PromeniSifru from './PromeniSifru';
import AddRoomType from './HotelPages/Admin/AddRoomType';
import RoomTypes from './HotelPages/Admin/RoomTypes';
import FastRooms from './HotelPages/FastRooms';
import SlowRooms from './HotelPages/SlowRooms';
import EditBonus from './MasterAdminPages/EditBonus';
import AvioCompaniesList from './AvioPages/User/AvioCompaniesList';

class App extends Component {

  state = {
    token: undefined,
    usersEmail: undefined,
    loggedIn: false
  }

  setToken = (jwt) =>{
    this.setState({
      token:jwt
    })
    console.log(this.state.token);
  }

  setEmail = (email) =>{
    this.setState({
      usersEmail:email
    })
  }

  logIn=()=>{
    this.setState({
      loggedIn: true
    })
    localStorage.setItem("isLogged", true);
  }

  logOut=()=>{
    this.setState({loggedIn: false}); 
    /*console.log(this.state.loggedIn);
    console.log(sessionStorage.getItem('jwtToken'));
    console.log(sessionStorage.getItem('email'));
    console.log(sessionStorage.getItem('rola'));*/
  }

  render() {
    return (
      <BrowserRouter>
        <div className="App grey lighten-1" id="divApp">
          <NavBar token={this.state.token} setToken={this.setToken} loggedIn={this.state.loggedIn} setEmail={this.setEmail} logOut={this.logOut}/>
          <Route exact path='/' render={Home}/>
          <Route path='/hotels' render={props => <Hotels loggedIn={this.state.loggedIn}/>}/>
          <Route path='/companies' render={AirlineIndex}/>
          <Route path='/rent-a-cars' render={RentACars}/>
          <Route path='/login' render={props => <Login setToken={this.setToken} setEmail={this.setEmail}  logIn={this.logIn}/>}/>
          <Route path='/register' render={Register}/>
          <Route path='/rooms/:hotelId' render={props => <Rooms loggedIn={this.state.loggedIn}/>}/>
          <Route path='/fast/rooms/:hotelId' render={props => <FastRooms loggedIn={this.state.loggedIn}/>}/>
          <Route path='/slow/rooms/:hotelId' render={props => <SlowRooms loggedIn={this.state.loggedIn}/>}/>
          <Route path='/services/:serviceId' render={Services}/>
          <Route path='/edit/hotel' render={EditHotel}/>
          <Route path='/admin/rooms' render={HotelRooms}/>
          <Route path='/admin/types' render={RoomTypes}/>
          <Route path='/admin/add_room/:hotelId' render={AddRoom}/>
          <Route path='/admin/add_room_type/:hotelId' render={AddRoomType}/>
          <Route path='/admin/edit_room/:hotelId/:sobaId' render={EditRoom}/>
          <Route path='/admin/services' render={HotelServices}/>
          <Route path='/admin/add_services/:hotelId' render={AddService}/>
          <Route path='/admin/edit_services/:serviceId' render={EditService}/>
          <Route path='/profile' render={AdminProfile}/>
          <Route path='/cenovnik' render={Cenovnik}/>
          <Route path='/admin/edit_price/:sobaId' render={EditPrice}/>
          <Route path='/admin/single_prices/:sobaId' render={SinglePrices}/>
          <Route path='/admin/add_price/:sobaId' render={AddSinglePrice}/>
          <Route path='/izvestaji' render={Izvestaji}/>
          <Route path='/master/add_admin' render={AddAdmin}/>
          <Route path='/master/add_hotel' render={AddHotel}/>
          <Route path='/master/add_avio' render={AddAvio}/>
          <Route path='/master/add_rent' render={AddRent}/>
          <Route path='/master/edit_bonus' render={EditBonus}/>
          <Route path='/reserve/:hotelId/:sobaId' render={ReservationForm}/>
          <Route path='/my_reservations' render={MyReservations}/>
          <Route path='/rating/:rezervacijaId/:hotelId/:sobaId' render={Ocena}/>
          <Route path='/promeni_sifru' render={PromeniSifru}/>


          <Route path='/flsearch' render={() => <FlightsSearch />} />
          <Route path='/flinfo/:flightid' render={() => <FlightInfo />} />
          <Route path='/companyinfo/:flightid' render={() => <AvioCompanyInfo />} />
          <Route path='/reservation/:flightid' render={() => <Reservation />} />
          <Route path='/adcompanies' render={() => <AvioCompanies />} />
          <Route path='/adflights' render={() => <Flights />} />
          <Route path='/adflightsedit/:flightid' render={() => <FlightsEdit />} />
          <Route path='/adother' render={() => <Other />} />
          <Route path='/adreports' render={() => <Reports />} />
          <Route path='/adinfo' render={() => <AdminInfo />} />
          <Route path='/account' render={() => <Account />} />
          <Route path='/userinvitations' render={() => <UserInvitations />} />
          <Route path='/expticket' render={() => <ExpressTicket />} />
          <Route path='/allcompanieslist' render={() => <AvioCompaniesList /> } />
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
