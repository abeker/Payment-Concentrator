import React from 'react';
import { Link } from 'react-router-dom';
import classes from './errorPage.module.css';

const Error = () => {
    return (
        <div className={ classes.Background }>
            <Link to="/books" className={ classes.Link }>Go to the main page</Link>
        </div>
    );
}

export default Error;