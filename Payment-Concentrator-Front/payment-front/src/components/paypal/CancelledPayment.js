import React from 'react';
import { Link, useHistory } from 'react-router-dom';

function CancelledPayment() {
    return(
        <div>
            <h2>Payment was not successfful</h2>
            <Link to="/">Go to the main page</Link>
        </div>
    )
}

export default SuccessPayment;