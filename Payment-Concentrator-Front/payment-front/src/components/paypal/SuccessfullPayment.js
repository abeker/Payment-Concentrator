import React from 'react';
import { Link, useHistory } from 'react-router-dom';
import classes from './paypal.module.css';

function SuccessPayment() {
    return(
        <div className={ classes.Background }>
            <Link to="/" className={ classes.Link }>Go to the main page</Link>
        </div>
    )
}

export default SuccessPayment;