import { useBeforeunload } from 'react-beforeunload';
import { useHistory } from 'react-router-dom';
import unicreditLogo from '../../../assets/images/unicredit1.png';
import Aux from "../../../hoc/Auxiliary";
import BankForm from '../BankForm';
import classes from './Unicredit.module.css';
import axios from 'axios';
import { useEffect, useState } from 'react';

const Unicredit = (props) => {
    const [paymentRequestId, setPaymentRequestId] = useState(null);
    useEffect(() => {
        setPaymentRequestId(props.match.params.paymentId);
    }, [props.match.params.paymentId]);

    useBeforeunload((event) => {
        cancelRequest(paymentRequestId);
        event.preventDefault();
    });

    const cancelRequest = (paymentRequestId) => {
        axios.put(`https://localhost:8443/api/unicredit/${paymentRequestId}/cancel`)
            .then(response => {
                // do nothing
            });
    }
    
    let history = useHistory();
    const onSendData = (event) => {
        event.preventDefault();
        const cardholderName = event.target[0].value;
        const pan = event.target[1].value;
        const validThru = event.target[2].value;
        const security_code = event.target[3].value;
        sendRequestBody('https://localhost:8443/api/unicredit/pay', 'POST', {
            "cardHolderName": cardholderName,
            "accountNumber": pan,
            "securityCode": security_code,
            "validThru": validThru,
            "paymentId": paymentRequestId
        });
        sendToLiteraryAssociation('http://localhost:8084/la/reader-pay-request', {
            "paymentCounter": paymentRequestId,
            "bankCode": pan.substring(1,6),
            "readerId": "readerId",
            "bookId": "bookId"
        });
    }

    const sendToLiteraryAssociation = (url, body) => {
        axios.post(url, body)
            .then(response => {
                //do nothing
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
            if(responseData.status === 500) {
                history.push('/error');
            } else {
                history.push('/success');
            }
        });
    }

    return (
        <Aux classes="Unicredit">
            <div style={{ marginLeft: "20%" }}>
                <h2 className={ classes.H2 }>Unicredit Bank</h2>
                <BankForm 
                    onClick={ onSendData }
                    cssClass="Unicredit"  />
                <img src={ unicreditLogo } alt="Unicredit" className={ classes.Img }/>
            </div>
        </Aux>
    );
}

export default Unicredit;