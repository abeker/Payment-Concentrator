import axios from 'axios';
import React from 'react';
import Aux from '../../hoc/Auxiliary';
import Image from '../images/Image';
import Subscipe from '../paypal/Subscipe';
import classes from './dashboard.module.css';

const Dashboard = (props) => {
    const imageClickHandler = (type) => {
        if(type === 'UNICREDIT') {
            sendRequestBody('https://localhost:8443/api/bank', 'PUT', {
                merchantId: "LMo0aUBivXliLs9rjBijU096ufdv56",
                merchantPassword: "p62om0FvEhG70wzCjwrW6rsZCYSY9SikETjbpHNIrJ37Ul6odV4GgV025kFLP7Vwa79XJ8WTsAsDB2D3r9jW26G7a3zp78HbW9z",
                amount: 4200
            });
        } else if(type === 'RAIFFEISEN') {
            sendRequestBody('https://localhost:8443/api/raiffeisen', 'PUT', {
                merchantId: "3ypomvybMdZlWTZNVH1X9Tm35b4ETD",
                merchantPassword: "lBMIS5zIk1I2WsDyST3tVmgupt5utGHpFhj84l4ytosQqrv9wiK4XgpWDmTzpoA51FQBh2XmJm8RDhEIyc1F2slCBKFrm0QXdVww",
                amount: 4200
            });
        } else if(type === 'VISA') {
            sendRequestBody('https://localhost:8443/api/unicredit', 'PUT', {
                merchantId: "LMo0aUBivXliLs9rjBijU096ufdv56",
                merchantPassword: "p62om0FvEhG70wzCjwrW6rsZCYSY9SikETjbpHNIrJ37Ul6odV4GgV025kFLP7Vwa79XJ8WTsAsDB2D3r9jW26G7a3zp78HbW9z",
                amount: 4200
            });
        } else if(type === 'PAYPAL') {
            axios.get("https://localhost:8443/api/paypal/paypal").then(response => alert(response.data))
            props.history.push('/paypal');
        } else if(type === 'BITCOIN') {
            // sendRequest('https://localhost:8443/api/bitcoin/pay');
            props.history.push('/bitcoin')
        } else if(type === 'NEW_LU') {
            // sendRequest('https://localhost:8443/api/bitcoin/pay');
            props.history.push('/create-lu')
        }
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
            {/* <Image type="RAIFFEISEN" clicked={ imageClickHandler.bind(this, "RAIFFEISEN") } /> */}
            {/* <Image type="UNICREDIT" clicked={ imageClickHandler.bind(this, "UNICREDIT") } /> */}
            <div className={ classes.Images }>
                <Image type="VISA" clicked={ imageClickHandler.bind(this, "VISA") } />
                <Image type="PAYPAL" clicked={ imageClickHandler.bind(this, "PAYPAL") } />
                <Image type="BITCOIN" clicked={ imageClickHandler.bind(this, "BITCOIN") } />
            </div>
            <br/>
            <br/>
            <br/>
            <hr/>
            <span className={ classes.inlineSpan }>
                <Image type="NEW_LU" clicked={ imageClickHandler.bind(this, "NEW_LU") } />
            </span>
            <span className={ classes.inlineSpan }>
                <div style={{display:'flex', justifyContent: 'center',alignItems: 'center', width: '800'}}>
                    <h3> Subscribe fast to PayPal</h3>
                    <br/>
                    <Subscipe />
                </div>
            </span>
        </Aux>
    );
}

export default Dashboard;