import React from 'react'
import { withRouter} from 'react-router-dom';
import AllFlights from './User/AllFlights';

const AirlineIndex = props => {
    return(
        <div>
            <AllFlights />
        </div>
    )
};

export default withRouter(AirlineIndex)