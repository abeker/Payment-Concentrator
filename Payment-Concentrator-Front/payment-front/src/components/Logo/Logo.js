import React from 'react';
import kpLogo from '../../assets/images/logo.svg';
import classes from './Logo.module.css';

const logo = (props) => (
    <div className={ classes.Logo } style={{ height: props.height }}>
        <img src={ kpLogo } alt="KP logo" />
    </div>
);

export default logo;