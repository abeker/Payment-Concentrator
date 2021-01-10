import React, { useState } from 'react';
import Aux from '../../hoc/Auxiliary';
import Image from '../images/Image';
import axios from 'axios'
import Bitcoin from '../bitcoin/Bitcoin';
import Subscipe from '../paypal/Subscipe';

const Dashboard = (props) => {
    const [error, setError] = useState(null);
    const imageClickHandler = (type) => {
        if(type === 'UNICREDIT') {
            sendRequestBody('https://localhost:8443/api/bank', 'PUT', {
                merchantId: "LMo0aUBivXliLs9rjBijU096ufdv56",
                merchantPassword: "p62om0FvEhG70wzCjwrW6rsZCYSY9SikETjbpHNIrJ37Ul6odV4GgV025kFLP7Vwa79XJ8WTsAsDB2D3r9jW26G7a3zp78HbW9z",
                amount: 4200
            });
        } else if(type === 'RAIFFEISEN') {
            sendRequestBody('https://localhost:8443/api/bank', 'PUT', {
                merchantId: "3ypomvybMdZlWTZNVH1X9Tm35b4ETD",
                merchantPassword: "lBMIS5zIk1I2WsDyST3tVmgupt5utGHpFhj84l4ytosQqrv9wiK4XgpWDmTzpoA51FQBh2XmJm8RDhEIyc1F2slCBKFrm0QXdVww",
                amount: 4200
            });
        } else if(type === 'PAYPAL') {
            axios.get("https://localhost:8443/api/paypal/paypal").then(response => alert(response.data))
            props.history.push('/paypal');
        } else if(type === 'BITCOIN') {
            // sendRequest('https://localhost:8443/api/bitcoin/pay');
            props.history.push('/bitcoin')
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
        <Aux classes="Background">
            { error && alert(error) }
            <Image type="RAIFFEISEN" clicked={ imageClickHandler.bind(this, "RAIFFEISEN") } />
            <Image type="UNICREDIT" clicked={ imageClickHandler.bind(this, "UNICREDIT") } />
            {/* <Image type="VISA" clicked={ imageClickHandler.bind(this, "VISA") } /> */}
            <Image type="PAYPAL" clicked={ imageClickHandler.bind(this, "PAYPAL") } />
            <Image type="BITCOIN" clicked={ imageClickHandler.bind(this, "BITCOIN") } />
            
            <div style={{display:'flex', justifyContent: 'center',alignItems: 'center', width: '800'}}>
                <h3> Subscripe fast to PayPal</h3>
                <br/>
                <Subscipe />
            </div>
        </Aux>
    );
}

export default Dashboard;