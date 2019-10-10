import React, { Component } from 'react'
import {Link, withRouter} from "react-router-dom"

class ReservationsChooseBar extends Component{
    
    componentDidMount(){

    }

    clickAvio=()=>{
        this.props.history.push("/avio_reservations");
    }

    clickHotel=()=>{
        this.props.history.push("/hotel_reservations");
    }

    clickCar=()=>{
        this.props.history.push("/rent-a-cars-r");
    }

    render(){
        return(          
            <div>
                <button className="btn waves-effect waves-light red darken-3" id="btnHotels1" onClick={this.clickAvio}>Aviokompanije</button>
                <button className="btn waves-effect waves-light red darken-3" id="btnHotels2" onClick={this.clickHotel}>Hoteli</button>
                <button className="btn waves-effect waves-light red darken-3" id="btnHotels3" onClick={this.clickCar}>Rent-a-car</button>
            </div>
        )
    }

}
export default withRouter(ReservationsChooseBar)