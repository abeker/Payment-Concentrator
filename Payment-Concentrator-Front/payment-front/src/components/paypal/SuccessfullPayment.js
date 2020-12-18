import React from 'react';
import { Link, useHistory } from 'react-router-dom';

function SuccessPayment() {
    const history = useHistory()
   
    return(
        <div>
            <h2>Payment was successfull</h2>
            <Link to="/">Go to the main page</Link>
        </div>
    )
}

export default SuccessPayment;