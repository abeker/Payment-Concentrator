import React, { useState } from 'react';
import { FaConnectdevelop, FaGem, FaUserMinus, FaShoppingCart, FaBook } from "react-icons/fa";
import { Menu, MenuItem, ProSidebar, SidebarContent, SidebarFooter, SidebarHeader, SubMenu } from 'react-pro-sidebar';
import 'react-pro-sidebar/dist/css/styles.css';
import { useHistory } from "react-router-dom";
import Aux from '../../../hoc/Auxiliary';
import { useComponentWillMount } from '../../Hooks/useComponentWillMount';
import Logo from '../../Logo/Logo';
import classes from './Sidebar.module.css';

const Sidebar = (props) => {
    const history = useHistory();
    const [menuBar, setmenuBar] = useState(null);

    useComponentWillMount(() => {
        const userRole = JSON.parse(localStorage.getItem('user')).userRole;
        
        if(userRole === 'READER') {
            setmenuBar( <Menu>
                            <MenuItem
                                onClick={ props.onclickToCart } icon={<FaShoppingCart />}> Shopping Cart
                            </MenuItem>
                            <MenuItem
                                onClick={ props.onclickToMyLibrary } icon={<FaBook />}> My Library
                            </MenuItem>
                        </Menu>);
        } else if(userRole === 'WRITER') {
            setmenuBar( <Menu>
                            <MenuItem icon={<FaConnectdevelop />}>Dashboard</MenuItem>
                            <SubMenu title="Components" icon={<FaGem />}>
                              <MenuItem>Component 1</MenuItem>
                              <MenuItem>Component 2</MenuItem>
                            </SubMenu>
                        </Menu>);
        }
    });

    const onLogout = () => {
        props.clearCart();
        localStorage.removeItem('user');
        history.push('/');
    }

    return (
        <Aux>
             <div className={ classes.Sidebar }>
                <ProSidebar>
                  <SidebarHeader>
                    <div onClick={ props.onClickToHeader } style={{ cursor: "pointer" }}>
                        <span className={ classes.InlineSpan }><Logo /></span>
                        <span className={ classes.InlineSpan }><label>Literary Association</label></span>
                    </div>
                  </SidebarHeader>
                  <SidebarContent>
                    { menuBar }
                  </SidebarContent>
                  <SidebarFooter>
                    <Menu>
                        <MenuItem onClick={ onLogout } icon={<FaUserMinus />}>Logout</MenuItem>
                    </Menu>
                  </SidebarFooter>
                </ProSidebar>
            </div>
            <div className={ classes.Inline }>
                { props.children }
            </div>
        </Aux>
    );
}

export default Sidebar;