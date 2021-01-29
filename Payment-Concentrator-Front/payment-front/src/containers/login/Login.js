import React, { Component } from 'react';
import LoginForm from '../../components/UI/Login/LoginForm';
import classes from './Login.module.css';
import { message } from 'antd';
import axios from 'axios';

class Login extends Component {

    onFinish = (values) => {
        const body = {
            username: values.username,
            password: values.password
        }
        axios.put('http://localhost:8084/auth/login', body)
             .then(user => {
            localStorage.setItem('user', JSON.stringify(user.data));
            this.props.history.push({ pathname: "/books" })
        })
        .catch(error => {
            message.error('Error while login.');
        });
    };
    
    onFinishFailed = (errorInfo) => {
        message.warning("Login info doesn't submitted");
    };

    render() {
        return (
            <div className={ classes.Wallpaper }>
                <h1 className={classes.Headline}>Welcome to PC!</h1>
                <div className={ classes.Form }>
                    <LoginForm 
                        onFinish = { this.onFinish }
                        onFinishFailed = { this.onFinishFailed }/>
                </div>
            </div>
        );
    }
}

export default Login;