import React, { Component } from 'react'
import { withRouter, Link } from 'react-router-dom';
import hotelSlika from "./images/hotel.jpg";
import avioSlika from "./images/aviokompanija.jpg";
import carSlika from "./images/car.jpg";
import "./card.css"

class Home extends Component{

    componentDidMount() {
    }

    render(){
        return(  
            <table className="divHome">
                <tbody>
                <tr>
                    <td>
                        <div className="row">
                            <div className="col s12 m7">
                                <div className="card z-depth-3" id="cardHome1">
                                <div className="card-image">
                                    <img className="slike" src={avioSlika} alt="a"/>
                                    <span className="card-title">Aviokompanije</span>
                                </div>
                                <div className="card-content">
                                    <p>Pregledajte našu ponudu aviokompanija i njihovih letova.</p>
                                </div>
                                <div className="card-action">
                                    <Link to="/companies" className="red-text"><b>Pregledaj aviokompanije</b></Link>
                                </div>
                                </div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div className="row">
                            <div className="col s12 m7">
                                <div className="card z-depth-3" id="cardHome2">
                                <div className="card-image">
                                    <img src={hotelSlika} className="slike" alt="a"/>
                                    <span className="card-title">Hoteli</span>
                                </div>
                                <div className="card-content">
                                    <p>Pregledajte našu ponudu hotela i njihovih soba.</p>
                                </div>
                                <div className="card-action">
                                    <Link to="/hotels" className="red-text"><b>Pregledaj hotele</b></Link>
                                </div>
                                </div>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div className="row">
                            <div className="col s12 m7">
                                <div className="card z-depth-3" id="cardHome3">
                                <div className="card-image">
                                    <img src={carSlika} className="slike" alt="a"/>
                                    <span className="card-title">Rent-a-car</span>
                                </div>
                                <div className="card-content">
                                    <p>Pregledajte našu ponudu rent-a-car servisa i njihovih vozila.</p>
                                </div>
                                <div className="card-action">
                                    <Link to="/rent-a-cars" className="red-text"><b>Pregledaj Rent-a-car servise</b></Link>
                                </div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        )
    }

}
export default withRouter(Home)