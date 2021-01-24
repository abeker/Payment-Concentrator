import React, { Component } from 'react';
import LoginForm from '../../components/UI/Login/LoginForm';
import classes from './Login.module.css';

class Login extends Component {

    onFinish = (values) => {
      console.log('Success:', values);
    };
    
    onFinishFailed = (errorInfo) => {
      console.log('Failed:', errorInfo);
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