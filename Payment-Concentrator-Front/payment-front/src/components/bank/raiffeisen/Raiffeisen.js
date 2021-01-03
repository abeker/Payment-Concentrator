import Aux from "../../../hoc/Auxiliary";
import BankForm from '../BankForm';
import raiffeisenLogo from '../../../assets/images/raiffeisen-logo.jpg';
import classes from './Raiffeisen.module.css';

const Raiffeisen = (props) => {
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
            console.log(responseData);
        }).catch(error => {
            alert("Invalid Card Holder Data!");
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