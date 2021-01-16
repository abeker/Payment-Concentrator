import axios from 'axios';
import { useBeforeunload } from 'react-beforeunload';
import { useHistory } from 'react-router-dom';
import raiffeisenLogo from '../../../assets/images/raiffeisen-logo.jpg';
import Aux from "../../../hoc/Auxiliary";
import BankForm from '../BankForm';
import classes from './Raiffeisen.module.css';

const Raiffeisen = (props) => {
    useBeforeunload((event) => {
        cancelRequest(props.match.params.paymentId);
        event.preventDefault();
    });

    const cancelRequest = (paymentRequestId) => {
        axios.put(`https://localhost:8443/api/raiffeisen/${paymentRequestId}/cancel`)
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
        sendRequestBody('https://localhost:8443/api/raiffeisen/pay', 'POST', {
            "cardHolderName": cardholderName,
            "accountNumber": pan,
            "securityCode": security_code,
            "validThru": validThru,
            "paymentId": props.match.params.paymentId
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
        <Aux classes="Raiffeisen">
            <BankForm 
                onClick={ onSendData } 
                cssClass="Raiffeisen" />
            <img src={ raiffeisenLogo } alt="Raiffeisen Bank" className={ classes.Img }/>
        </Aux>
    );
}

export default Raiffeisen;