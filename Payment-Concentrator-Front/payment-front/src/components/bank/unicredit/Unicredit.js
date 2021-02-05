import { useBeforeunload } from 'react-beforeunload';
import { useHistory } from 'react-router-dom';
import unicreditLogo from '../../../assets/images/unicredit1.png';
import Aux from "../../../hoc/Auxiliary";
import BankForm from '../BankForm';
import classes from './Unicredit.module.css';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { message } from 'antd';

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
        // console.log('readerid: ' + props.location.state.readerId);
        // console.log('books: ' + props.location.state.bookIds);
        const readerId = props.location.state.readerId;
        const bookIds = props.location.state.bookIds;

        event.preventDefault();
        const cardholderName = event.target[0].value;
        const pan = event.target[1].value;
        const validThru = event.target[2].value;
        const security_code = event.target[3].value;
        const bankRequestBody = {
            cardHolderName: cardholderName,
            accountNumber: pan,
            securityCode: security_code,
            validThru: validThru,
            paymentId: paymentRequestId
        };
        console.log(bankRequestBody);
        sendToLiteraryAssociation('http://localhost:8084/la/reader-pay-request', {
            "paymentCounter": paymentRequestId,
            "bankCode": pan.substring(1,6),
            "readerId": readerId,
            "bookIds": bookIds
        }, bankRequestBody);
    }

    const sendToLiteraryAssociation = (url, body, bankRequestBody) => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.post(url, body, {headers: {'Auth-Token': token}})
            .then(response => {
                console.log(response.data);
                sendRequestBody('https://localhost:8443/api/unicredit/pay', 'POST', bankRequestBody, response.data.id);
            })
            .catch(error => {
                console.log(error.response);
                if(error.response.status === 409) {
                    message.info('Book is not purchased. You have not paid your membership yet.');
                    history.push('/error');
                }
            });
    }
    
    const sendRequestBody = (url, method, body, readerPaymentId) => {
        console.log(url);
        console.log(body);
        fetch(url, {
            method: method,
            body: JSON.stringify(body),
            headers: {'Content-Type': 'application/json'}
        }).then(response => {
            return response.json();
        }).then(responseData => {
            if(responseData.status === 500) {
                sendTransactionBackRequest(
                    `http://localhost:8084/la/reader-pay/${readerPaymentId}/status/FAIL`);
                history.push('/error');
            } else {
                history.push('/success');
            }
        })
    }

    const sendTransactionBackRequest = (url) => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.put(url, {}, {headers: {'Auth-Token': token}})
            .then(response => {
                console.log('Reverted');
            })
            .catch(error => {
                console.log(error);
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