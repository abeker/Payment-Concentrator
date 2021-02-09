import React, { Component } from 'react';
import LoginForm from '../../components/UI/Login/LoginForm';
import classes from './Login.module.css';
import { message } from 'antd';
import axios from 'axios';
import PasswordAdvices from './PasswordAdvices';

class Login extends Component {
    state = {
        passwordAdvicesVisible: false,
        literaryAssociations: null,
        selectedLiteraryAssociationIndex: 0
    }

    componentWillMount() {
        const isUserBlocked = localStorage.getItem('userBlocked');
        if(isUserBlocked) {
            this.props.history.push({ pathname: "/auth/block" });
        }
        this.getLiteraryAssociations();
    }

    getLiteraryAssociations = () => {
        axios.get('http://localhost:8084/la')
            .then(response => {
                this.setState({ literaryAssociations: [ ...response.data ] });
                this.setState({ selectedLiteraryAssociationIndex: 0 })
            })
            .catch(error => {
                console.log(error);
            })
    }
    
    onFinish = (values) => {
        const selectedLiteraryName = this.state.literaryAssociations[this.state.selectedLiteraryAssociationIndex].name;
        console.log(selectedLiteraryName);
        const body = {
            username: values.username,
            password: values.password
        }
        axios.put(`http://localhost:8084/auth/login/${selectedLiteraryName}`, body)
             .then(user => {
                localStorage.setItem('user', JSON.stringify(user.data));
                localStorage.removeItem('userBlocked');
                if(user.data.userRole === 'ADMIN') {
                    this.props.history.push({ pathname: "/admin" });
                } else if (user.data.userRole === 'WRITER') {
                    this.props.history.push({ pathname: "/writer" });
                } else {
                    this.props.history.push({ pathname: "/books" });
                }
            })
            .catch(error => {
                console.log(error);
                message.error(error.response.data);
                if(error.response.status === 409) {
                    localStorage.setItem('userBlocked', '1');
                    this.props.history.push({ pathname: "/auth/block" });
                }
            });
    };
    
    onFinishFailed = (errorInfo) => {
        message.warning("Login info doesn't submitted");
    };

    onAdvicesForPassword = () => {
        this.setState({passwordAdvicesVisible: !this.state.passwordAdvicesVisible});
    }

    changeLiteraryAssociation = () => {
        // console.log('array length: ' + this.state.literaryAssociations.length);
        // console.log('index before: ' + this.state.selectedLiteraryAssociationIndex);
        let index;
        if(this.state.selectedLiteraryAssociationIndex < this.state.literaryAssociations.length-1){
            index = this.state.selectedLiteraryAssociationIndex + 1;
        } else {
            index = 0;
        }
        // console.log('index after: ' + index);
        this.setState({
            selectedLiteraryAssociationIndex: index
        });
    }
    
    render() {
        let name = "";
        if(this.state.literaryAssociations !== null) {
            name = this.state.literaryAssociations[this.state.selectedLiteraryAssociationIndex].name;
        }
        return (
            <div className={ classes.Wallpaper }>
                { this.state.literaryAssociations !== null ?
                    <h1 className={classes.Headline} 
                        onClick={this.changeLiteraryAssociation}>
                        Welcome to { name }!
                    </h1> : null
                }
                <div className={ classes.Form }>
                    <LoginForm 
                        onFinish = { this.onFinish }
                        onFinishFailed = { this.onFinishFailed }
                        onAdvicesForPassword = { this.onAdvicesForPassword }/>
                </div>
                { this.state.passwordAdvicesVisible ? <PasswordAdvices /> : null }
            </div>
        );
    }
}

export default Login;