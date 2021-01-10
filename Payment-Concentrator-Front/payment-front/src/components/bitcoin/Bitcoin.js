import React, { useState } from 'react'
import axios from 'axios'
import {Container, Form, Grid, Button} from 'semantic-ui-react'
const Bitcoin = () => {

    const [title,setTitle] = useState('')
    const [price,setPrice] = useState(0.0)
    const [priceCurrency, setPriceCurrency] = useState('USD')
    const [receiveCurrency, setReceiveCurrency] = useState('USD')
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
            console.log(resp.data)
            window.location = resp.data.payment_url
        }).catch(error => {
            alert(error)
        })

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
