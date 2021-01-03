import Aux from "../../../hoc/Auxiliary";
import {useHistory} from 'react-router-dom'
import BankForm from '../BankForm';
import unicreditLogo from '../../../assets/images/unicredit1.png';
import classes from './Unicredit.module.css';

const Unicredit = (props) => {
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
            history.push('/success')
        }).catch(error => {
            alert("Invalid Card Holder Data!");
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