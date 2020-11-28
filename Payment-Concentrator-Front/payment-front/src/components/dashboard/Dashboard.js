import React, { useState } from 'react';
import Aux from '../../hoc/Auxiliary';
import Image from '../images/Image';
import Spinner from '../UI/Spinner/Spinner';

const Dashboard = (props) => {
    const [error, setError] = useState(null);

    const imageClickHandler = (type) => {
        let url = '';
        if(type === 'VISA') {
            url = 'http://localhost:8080/bank/visa';
        } else if(type === 'PAYPAL') {
            url = 'http://localhost:8080/paypal/paypal';
        } else if(type === 'BITCOIN') {
            url = 'http://localhost:8080/bitcocin/bitcoin';
        }

        fetch(url).then(response => {
            return response.text();     
        }).then(responseData => {
            alert(responseData);
        }).catch(error => {
            setError('Something get wrong!');
        });
    }

    return (
        <Aux>
            { error && alert(error) }
            <Image type="VISA" clicked={ imageClickHandler.bind(this, "VISA") } />
            <Image type="PAYPAL" clicked={ imageClickHandler.bind(this, "PAYPAL") } />
            <Image type="BITCOIN" clicked={ imageClickHandler.bind(this, "BITCOIN") } />
        </Aux>
    );
}

export default Dashboard;