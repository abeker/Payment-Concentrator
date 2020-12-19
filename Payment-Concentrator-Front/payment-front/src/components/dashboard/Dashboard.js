import React, { useState } from 'react';
import Aux from '../../hoc/Auxiliary';
import Image from '../images/Image';

const Dashboard = (props) => {
    const [error, setError] = useState(null);

    const imageClickHandler = (type) => {
        if(type === 'BANK') {
            sendRequestBody('http://localhost:8080/api/unicredit', 'PUT', {
                merchantId: "LMo0aUBivXliLs9rjBijU096ufdv56",
                merchantPassword: "p62om0FvEhG70wzCjwrW6rsZCYSY9SikETjbpHNIrJ37Ul6odV4GgV025kFLP7Vwa79XJ8WTsAsDB2D3r9jW26G7a3zp78HbW9z",
                amount: 4200
            });
        } else if(type === 'PAYPAL') {
            props.history.push('/paypal');
        } else if(type === 'BITCOIN') {
            sendRequest('http://localhost:8080/api/bitcoin/pay');
        }
    }

    const sendRequest = (url) => {
        fetch(url).then(response => {
            return response.text();     
        }).then(responseData => {
            console.log(responseData);
            alert(responseData);
        }).catch(error => {
            setError('Something get wrong!');
        });
    }

    const sendRequestBody = (url, method, body) => {
        fetch(url, {
            method: method,
            body: JSON.stringify(body),
            headers: {'Content-Type': 'application/json'}
        }).then(response => {
            return response.json();
        }).then(responseData => {
            console.log(responseData);
            props.history.push(responseData.paymentUrl+"/"+responseData.paymentId);
        }).catch(error => {
            console.log(error);
        });
    }
    

    return (
        <Aux>
            { error && alert(error) }
            <Image type="BANK" clicked={ imageClickHandler.bind(this, "BANK") } />
            <Image type="PAYPAL" clicked={ imageClickHandler.bind(this, "PAYPAL") } />
            <Image type="BITCOIN" clicked={ imageClickHandler.bind(this, "BITCOIN") } />
        </Aux>
    );
}

export default Dashboard;