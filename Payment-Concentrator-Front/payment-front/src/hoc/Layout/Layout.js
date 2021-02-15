import React, { useState } from 'react';
import Aux from '../Auxiliary';
import classes from './Layout.module.css';
import SideDrawer from '../../components/Navigation/SideDrawer/SideDrawer';
import Toolbar from '../../components/Navigation/Toolbar/Toolbar';

const Layout = (props) => {
    const [showSideDrawer, setShowSideDrawer] = useState(false);

    const sideDrawerClosedHandler = () => {
        setShowSideDrawer(false);
    }

    const sideDrawerToggleHandler = () => {
        setShowSideDrawer(prevState => ({
            showSideDrawer: !prevState.showSideDrawer
        }));
    }

    return (
        <Aux>
            <Toolbar 
                isAuth={ props.isAuthenticated }
                toggle={ sideDrawerToggleHandler }/>
            <SideDrawer 
                isAuth={ props.isAuthenticated }
                open={ showSideDrawer }
                closed={ sideDrawerClosedHandler }/>
            <main className={ classes.Content }>
                { props.children }
            </main>
        </Aux>
    );
}

export default Layout;