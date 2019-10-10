import React, { Component } from 'react'
import axios from 'axios'
import { withRouter} from 'react-router-dom';
import Select from 'react-select';
class EditRoom extends Component{

    state={
        originalnaCena:"",
        soba:""
    }

    componentDidMount() {
       axios.get("http://localhost:8080/sobe/" + this.props.match.params.sobaId)
        .then(res=>{
            console.log(res.data);
             this.setState({
                soba: res.data,
                originalnaCena: res.data.originalnaCena
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
        if(this.state.originalnaCena!==""){
            axios.put("http://localhost:8080/sobe/cena/" + this.props.match.params.sobaId, {originalnaCena: this.state.originalnaCena}, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                alert("Uspesno izmenjena cena.")
                this.props.history.push("/cenovnik");
            }).catch(error=>{
                alert("Nemate ovlascene potrbno za izmenu cene.");
            })
        }else{
            alert("Polje mora biti ispravno popunjeno.")
        }
    }

    render(){
        return(
            <div className="center container">
                <br/>
                <h3>Izmeni cenu:</h3>
                <br/>
                <div className="center container">
                    <form onSubmit={this.handleSubmit}>
                        <label className="left black-text" htmlFor="originalnaCena">Originalna cena:</label>
                        <input type="number" id="originalnaCena" value={this.state.originalnaCena} onChange={this.handleChange}/>
                        <button className="btn waves-effect waves-light green" id="dodajSobaClick">Izmeni</button>
                    </form>
                </div>
            </div>
        )
    }

}
export default withRouter(EditRoom)