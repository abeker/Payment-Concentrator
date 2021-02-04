import { Checkbox, message } from 'antd';
import axios from 'axios';
import React, { Component } from 'react';
import Image from '../images/Image';
import RegistrationForm from '../UI/RegistrationForm/RegistrationForm';
import classes from './createLU.module.css';

class CreateLU extends Component {
    state = {
        isBank: false,
        isBitcoin: false,
        isPaypal: false,
        bankCheckbox: false,
        paypalCheckbox: null,
        bitcoinCheckbox: null
    }

    constructor(props) {
        super(props);
        axios.get("https://localhost:8443/api/kp/payment-type")
        .then(response => {
            console.log(response.data.paymentTypeNames);
            response.data.paymentTypeNames.forEach(paymentType => {
              if(paymentType === 'bitcoin') {
                this.setState({bitcoinCheckbox: 
                    <Checkbox className={ classes.Checkbox } onChange={ this.onBitcoinClicked }>
                        <Image type="LU_BITCOIN"/>
                    </Checkbox>
                });
              } if(paymentType === 'paypal') {
                this.setState({paypalCheckbox: 
                    <Checkbox className={ classes.Checkbox } onChange={ this.onPaypalClick }>
                        <Image type="LU_PAYPAL"/>
                    </Checkbox>
                });
              } if(paymentType === 'bank' || paymentType === 'bank1' || paymentType === 'unicredit' || paymentType === 'raiffeisen') {
                    this.setState({bankCheckbox: 
                        <Checkbox className={ classes.Checkbox } onChange={ this.onBankClick }>
                            <Image type="LU_BANK"/>
                        </Checkbox>
                    });
              }
            });
        })
    }

    onBankClick = () => {
        this.setState((state, props) => { return { 
            isBank: !state.isBank
        }});
    }

    onBitcoinClicked = () => {
        this.setState((state, props) => { return { 
            isBitcoin: !state.isBitcoin
        }});
    }

    onPaypalClick = () => {
        this.setState((state, props) => { return { 
            isPaypal: !state.isPaypal
        }});
    }

    onSubmit = (formValues) => {
        const formBody = {
            "name": formValues.fullname,
            "country": formValues.country,
            "city": formValues.city,
            "streetNumber": formValues.street,
            "zipCode": formValues.zipCode,
            "membershipAmount": formValues.membership
        }
        this.sendRequestToLU(formBody, formValues);
    }

    sendRequestToLU = (requestBody, formValues) => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.post('http://localhost:8084/la', requestBody, {headers: {'Auth-Token': token}})
            .then(response => {
                if(response.status === 200) {
                  this.sendRequestToKP(response.data.luId, formValues);
                }
            })
    }
    
    sendRequestToKP = (luId, formValues) => {
        axios.post('https://localhost:8443/api/kp/literary-association/'+luId, {
            "paymentTypeNames": this.getPaymentTypeListFromSelectedCheckboxes(formValues)
          }).then(() => {
            message.success('Successfuly created Seller Account');
        }).catch(() => {
            message.error('Something went wrong!');
        })
    }

    getPaymentTypeListFromSelectedCheckboxes = (formValues) => {
        let retList = [];
        if(this.state.isBank) {
            retList.push(formValues.selectedBank);
        } if (this.state.isBitcoin) {
            retList.push('bitcoin');
        } if(this.state.isPaypal) {
            retList.push('paypal');
        }
        return retList;
    }
   
    render() {
        return (
            <div className={ classes.Wallpaper }>
            <h1 className={classes.Headline}>Welcome to Seller Registration!</h1>
            <div className={ classes.Form }>
                <RegistrationForm
                    onSubmit = {this.onSubmit}
                    isBankSelected = { this.state.isBank }/>
            </div>
            <br/>
            <div className={ classes.divPayType }>
                <h3 className={ classes.h3 }>Select Payment Type</h3>
                <div>
                    { this.state.bitcoinCheckbox }
                    { this.state.paypalCheckbox }
                    { this.state.bankCheckbox }
                </div>
            </div>
        </div>
        )
    }
}

export default CreateLU;