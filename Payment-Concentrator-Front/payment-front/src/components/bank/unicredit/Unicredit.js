import BankForm from '../BankForm';

const Unicredit = (props) => {
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
        }).catch(error => {
            alert("Invalid Card Holder Data!");
        });
    }

    return (
        <BankForm 
            onClick={ onSendData } />
    );
}

export default Unicredit;