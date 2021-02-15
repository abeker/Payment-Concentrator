import React from 'react';
//import axiosInstance from '../axios/axios'
import axios from 'axios';
import Image from '../images/Image';
import { message } from 'antd';

const PayPal = (props) => {
    const [price,setPrice] = React.useState(props.location.state.amount);
    const [currency,setCurrency] = React.useState("USD");
    const [method,setMethod] = React.useState("paypal");
    const [intent,setIntent] = React.useState("SALE");
    const [desc,setDesc] = React.useState("Request for book payment.");
    
    const handleSubmit = e => {
        e.preventDefault();
        console.log(price, currency,method, intent,desc);
        const json = JSON.stringify({ price,currency,method,intent,description: desc });
        console.log(json);

        const readerId = props.location.state.readerId;
        const bookIds = props.location.state.bookIds;
        console.log("readerId: "+  readerId + ", books: ");
        console.log(bookIds);
        axios.post("https://localhost:8443/api/paypal/pay",json,{
            headers: {
                'Content-Type': 'application/json',
            }}).then(res => {
            console.log(res)
            if(res.status===200){
                window.location = res.data
            }
            if(res.status >= 400){
                alert("Some error occurred, please check your bank account")
            }
        })
    }

    const sendToLiteraryAssociation = (url, body) => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.post(url, body, {headers: {'Auth-Token': token}})
            .then(response => {
                console.log(response.data);
            })
            .catch(error => {
                console.log(error.response);
                if(error.response.status === 409) {
                    message.info('Book is not purchased. You have not paid your membership yet.');
                    props.history.push('/error');
                }
            });
    }

    const handleChange=(event)=>{
        setCurrency(event.target.value);
    }

    return (
        <div>
            <form onSubmit={handleSubmit} className="ui form">
                <div className="equal width fields">
                    <div className="field">
                        <label htmlFor="price">Price</label>
                        <div class="ui input">
                            <input type="number" id="number" value={price} onChange={(e)=>setPrice(e.target.value)} />
                        </div>
                    </div>
               
                    <div className="field">
                        <label htmlFor="currency">Currency</label>
                        <div class="ui input">
                            <select id="currency"  defaultValue = "USD" name="currency" value={currency} onChange={handleChange}>
                                <option value="USD">USD</option>
                                <option value="EUR">EUR</option>
                            </select>
                        </div>
                    </div>
                    <div className="field">
                        <label htmlFor="method">Method</label>
                        <div class="ui input">
                            <input type="method" id="method" value={method} onChange={(e)=>setMethod(e.target.value)} />
                        </div>
                    </div>
                    <div className="field">
                        <label htmlFor="intent">Intent</label>
                        <div class="ui input">
                            <select id="intent"  defaultValue = "SALE" name="intent" value={intent} onChange={e=>setIntent(e.target.value)}>
                                <option value="SALE">SALE</option>
                                <option value="AUTHORIZE">AUTHORIZE</option>
                                <option value="ORDER">ORDER</option>
                                <option value="NONE">NONE</option>
                            </select>
                        </div>
                    </div>
                    <div className="field">
                        <label htmlFor="description">description</label>
                        <div class="ui input">
                            <input type="description" id="description" value={desc} onChange={(e)=>setDesc(e.target.value)} />
                        </div>
                    </div>
                </div>
                <div class="field">
                    <button type="submit" id="form-button-control-public" class="ui button" style={{ height: 100, width:100, marginTop: 10 }}>
                    <Image type="PAYPAL" />
                        Confirm
                    </button>
                </div>
            </form>                      
        </div>
    )
}

export default PayPal;

