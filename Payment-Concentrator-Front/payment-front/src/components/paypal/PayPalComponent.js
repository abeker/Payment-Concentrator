import React from 'react';
//import axiosInstance from '../axios/axios'
import axios from 'axios';
import Image from '../images/Image';

const PayPal = (props) => {
    const [price,setPrice] = React.useState(props.price);
    const [currency,setCurrency] = React.useState("USD");
    const [method,setMethod] = React.useState("");
    const [intent,setIntent] = React.useState("SALE");
    const [desc,setDesc] = React.useState("");
    
    const handleSubmit = e => {
        e.preventDefault();
        console.log(price, currency,method, intent,desc);
        const json = JSON.stringify({ price,currency,method,intent,description: desc });
        axios.post("https://localhost:8443/api/paypal/pay",json,{
            headers: {
                'Content-Type': 'application/json',
            }}).then(res => {
            console.log(res)
            if(res.status==200){
                window.location = res.data
            }
            if(res.status >= 400){
                alert("Some error occurred, please check your bank account")
            }
        })
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

