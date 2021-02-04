import React, { useState } from 'react';
import { FaConnectdevelop, FaGem, FaUserMinus, FaShoppingCart, FaBook, FaUserClock, FaPlusCircle, FaMoneyCheckAlt } from "react-icons/fa";
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
    const [userRole, setuserRole] = useState(JSON.parse(localStorage.getItem('user')).userRole)

    useComponentWillMount(() => {
        if(userRole === 'READER') {
            setmenuBar( <Menu>
                            <MenuItem
                                onClick={ props.onclickToCart } icon={<FaShoppingCart />}> Shopping Cart
                            </MenuItem>
                            <MenuItem
                                onClick={ props.onclickToMyLibrary } icon={<FaBook />}> My Library
                            </MenuItem>
                            <MenuItem
                                onClick={ props.onclickToMembership } icon={<FaMoneyCheckAlt />}> Membership
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
        } else if(userRole === 'ADMIN') {
            setmenuBar( <Menu>
                            <MenuItem
                                onClick={ props.onclickToDeleteInactive } 
                                icon={<FaUserClock />}>Delete Inactive Users</MenuItem>
                            <MenuItem
                                onClick={ props.onclickToCreateLU } 
                                icon={<FaPlusCircle />}>New Literary Association</MenuItem>
                        </Menu>);
        }
    });

    const onLogout = () => {
        if(userRole === 'READER') {
            props.clearCart();
        }
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
            <div className={ classes.Background }>
                <div className={ classes.Inline }>
                    { props.children }
                </div>
            </div>
        </Aux>
    );
}

export default Sidebar;