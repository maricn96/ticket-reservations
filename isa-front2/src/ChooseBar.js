import React, { Component } from 'react'
import {Link, withRouter} from "react-router-dom"

class ChooseBar extends Component{
    
    componentDidMount(){

    }

    clickAvio=()=>{
        this.props.history.push("/companies");
    }

    clickHotel=()=>{
        this.props.history.push("/hotels");
    }

    clickCar=()=>{
        this.props.history.push("/rent-a-cars");
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
export default withRouter(ChooseBar)