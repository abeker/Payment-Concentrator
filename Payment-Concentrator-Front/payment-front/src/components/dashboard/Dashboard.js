import React, { useState } from 'react';
import Aux from '../../hoc/Auxiliary';
import Image from '../images/Image';

const Dashboard = (props) => {
    const [error, setError] = useState(null);

    const imageClickHandler = (type) => {
        let url = '';
        if(type === 'VISA') {
            url = 'http://localhost:8080/api/bank/pay';
        } else if(type === 'PAYPAL') {
            url = 'http://localhost:8080/api/paypal/pay';
        } else if(type === 'BITCOIN') {
            url = 'http://localhost:8080/api/bitcoin/pay';
        }

        fetch(url).then(response => {
            return response.text();     
        }).then(responseData => {
            console.log(responseData);
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