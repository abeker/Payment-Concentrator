import React, { useState } from 'react'
import axios from 'axios'
import {Container, Form, Grid, Button} from 'semantic-ui-react'
import { message } from 'antd';

const Bitcoin = (props) => {
    const [title,setTitle] = useState("Request for book payment.");
    const [price,setPrice] = useState(props.location.state.amount);
    const [priceCurrency, setPriceCurrency] = useState('EUR');
    const [receiveCurrency, setReceiveCurrency] = useState('BTC');
    const currencyOptions = ['USD',"EUR","CAD","BTC","ETH"];
    const receiveOptions = ['USD',"EUR","USDT","BTC","ETH","LTC","DO_NOT_CONVERT"];

    const handleSubmit = (e) => {
        e.preventDefault()
        axios.post("https://localhost:8443/api/bitcoin/order",{
            title: title,
            price_amount: parseFloat(price),
            price_currency: priceCurrency,
            receive_currency: receiveCurrency
        },{
            headers: {'Content-type':"application/json"}
        }).then(resp => {
            // TODO
            // dodaj tu knjigu u literarnom
            console.log(resp.data)
            window.location = resp.data.payment_url
            
        }).catch(error => {
            alert(error)
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

    return (
        <Container>
            <Grid>
                <Grid.Row centered>
                    <Grid.Column width={6}>
                    <Form>
                            <Form.Field>
                                <label>Title</label>
                                <input placeholder='Title' autoComplete="off" value={title} onChange={(e)=>setTitle(e.target.value)} />
                            </Form.Field>
                            <Form.Field>
                                <label>Price Amount</label>
                                <input placeholder='Price' type="number" autoComplete="off" value={price} onChange={(e)=>setPrice(parseFloat(e.target.value))}/>
                            </Form.Field>
                            <Form.Field>
                                <label>Price Currency</label>
                                <select value={priceCurrency} onChange={(e)=>setPriceCurrency(e.target.value)}>
                                    {currencyOptions.map((option) => (
                                    <option key={option} value={option}>{option}</option>
                                    ))}
                                </select>
                                {/* <input placeholder="Price Currency"  autoComplete="off" value={priceCurrency} onChange={(e)=>setPriceCurrency(e.target.value)}/> */}
                            </Form.Field>
                            <Form.Field>
                                <label>Receive Curency</label>
                                <select value={receiveCurrency} onChange={(e)=>setReceiveCurrency(e.target.value)}>
                                    {receiveOptions.map((option) => (
                                    <option key={option} value={option}>{option}</option>
                                    ))}
                                </select>
                            </Form.Field>
                            <Button color="green" onClick={handleSubmit}>Checkout</Button>
                        
                    </Form>
                    </Grid.Column>
                </Grid.Row>
            </Grid>
            <br/>
            <div style={{
                alignContent: 'center',
                backgroundImage: "url(/bitcoin.jpg)",
                backgroundSize: "contain",
                backgroundRepeat: 'no-repeat',
                width: 1200,
                height: 800,
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                paddingtop: "66%",
            }}>

            </div>
        </Container>
    )
}

export default Bitcoin
