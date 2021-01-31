import axios from 'axios';
import React from 'react';
import { Component } from 'react';
import Aux from '../../hoc/Auxiliary';
import Image from '../images/Image';
import Subscipe from '../paypal/Subscipe';
import { connect } from 'react-redux';
import classes from './dashboard.module.css';

class Dashboard extends Component {
    state = {
        isBankVisible: this.props.location.state.paymentTypes.includes('unicredit') || this.props.location.state.paymentTypes.includes('raiffeisen'),
        isPaypalVisible: this.props.location.state.paymentTypes.includes('paypal'),
        isBitcoinVisible: this.props.location.state.paymentTypes.includes('bitcoin')
    }

    imageClickHandler = (type) => {
        const body = {
            merchantId: this.props.merchantId,
            merchantPassword: this.props.merchantPassword,
            amount: +this.props.location.state.cartContentPrice
        }

        if(type === 'UNICREDIT') {
            this.sendRequestBody('https://localhost:8443/api/bank', 'PUT', {
                merchantId: "LMo0aUBivXliLs9rjBijU096ufdv56",
                merchantPassword: "p62om0FvEhG70wzCjwrW6rsZCYSY9SikETjbpHNIrJ37Ul6odV4GgV025kFLP7Vwa79XJ8WTsAsDB2D3r9jW26G7a3zp78HbW9z",
                amount: 4200
            });
        } else if(type === 'RAIFFEISEN') {
            this.sendRequestBody('https://localhost:8443/api/raiffeisen', 'PUT', {
                merchantId: "3ypomvybMdZlWTZNVH1X9Tm35b4ETD",
                merchantPassword: "lBMIS5zIk1I2WsDyST3tVmgupt5utGHpFhj84l4ytosQqrv9wiK4XgpWDmTzpoA51FQBh2XmJm8RDhEIyc1F2slCBKFrm0QXdVww",
                amount: 4200
            });
        } else if(type === 'VISA') {
            this.sendRequestBody('https://localhost:8443/api/unicredit', 'PUT', body);
        } else if(type === 'PAYPAL') {
            axios.get("https://localhost:8443/api/paypal/paypal").then(response => alert(response.data))
            this.props.history.push('/paypal');
        } else if(type === 'BITCOIN') {
            // sendRequest('https://localhost:8443/api/bitcoin/pay');
            this.props.history.push('/bitcoin')
        } else if(type === 'NEW_LU') {
            // sendRequest('https://localhost:8443/api/bitcoin/pay');
            this.props.history.push('/create-lu')
        }
    }

    sendRequestBody = (url, method, body) => {
        fetch(url, {
            method: method,
            body: JSON.stringify(body),
            headers: {'Content-Type': 'application/json'}
        }).then(response => {
            return response.json();
        }).then(responseData => {
            const readerId = JSON.parse(localStorage.getItem('user')).id;
            this.props.history.push({
                pathname: responseData.paymentUrl+"/"+responseData.paymentId,
                state: { readerId: readerId, bookIds: this.getBookIds() }
            });
        }).catch(error => {
            console.log(error);
        });
    }

    getBookIds = () => {
        const books = this.createListFromChunk(this.props.location.state.books);
        const bookIds = [];
        books.forEach(book => {
            bookIds.push(book.id);
        });
        return bookIds;
    }

    createListFromChunk = (chunk) => {
        let retList = [];
        chunk.forEach(array => {
            array.forEach(element => {
                retList.push(element);
            });
        });
        return retList;
    }
    
    render() {
        return (
            <Aux classes="Background">
                <div className={ classes.Images }>
                    { this.state.isBankVisible ? 
                        <Image type="VISA" clicked={ this.imageClickHandler.bind(this, "VISA") } /> : null }
                    { this.state.isPaypalVisible ? 
                        <Image type="PAYPAL" clicked={ this.imageClickHandler.bind(this, "PAYPAL") } />: null }
                    { this.state.isBitcoinVisible ? 
                        <Image type="BITCOIN" clicked={ this.imageClickHandler.bind(this, "BITCOIN") } />: null }
                </div>
                <br/>
                <br/>
                <br/>
                <hr/>
                {/* <span className={ classes.inlineSpan }>
                    <Image type="NEW_LU" clicked={ imageClickHandler.bind(this, "NEW_LU") } />
                </span> */}
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
}

const mapStateToProps = state => {
    return {
        merchantId: state.merchant.merchantId,
        merchantPassword: state.merchant.merchantPassword,
        totalPrice: state.bookCart.totalAmount
    };
};

export default connect(mapStateToProps)(Dashboard);