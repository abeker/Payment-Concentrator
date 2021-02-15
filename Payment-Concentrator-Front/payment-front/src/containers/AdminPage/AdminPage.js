import { Button, Tooltip, TrashIcon } from 'evergreen-ui';
import Sidebar from '../../components/UI/Sidebar/Sidebar';
import Aux from '../../hoc/Auxiliary';
import axios from 'axios';
import { useState } from 'react';
import CreateLUComponent from '../../components/createLU/CreateLU';
import classes from './AdminPage.module.css';

const AdminPage = () => {
    const [inactiveButtonVisible, setinactiveButtonVisible] = useState(false);
    const [isCreateLUVisible, setisCreateLUVisible] = useState(false);

    const deleteInactiveUsers = () => {
        axios.get('http://localhost:8084/admins/check-inactive-users')
            .then(deletedUsers => {
                // console.log(deletedUsers);
            })
            .catch(error => {
                console.log(error);
            });
    }

    const changeInactiveButton = () => {
        setisCreateLUVisible(false);
        setinactiveButtonVisible(true);
    }

    const changeCreateLUVisibility = () => {
        setinactiveButtonVisible(false);
        setisCreateLUVisible(true);
    }

    return (
        <Aux>
            <Sidebar
                 onclickToDeleteInactive = { changeInactiveButton }
                 onclickToCreateLU = { changeCreateLUVisibility }>
                { inactiveButtonVisible ?  
                    <Tooltip content="Delete Users that is Inactive more than 150 days">
                        <Button marginY={50} 
                            marginLeft={100} 
                            iconBefore={TrashIcon} 
                            intent="danger"
                            height={40}
                            onClick={ deleteInactiveUsers }>Delete...</Button>
                    </Tooltip> : null}
                
                <div className={ classes.DivLUCreate }>
                    { isCreateLUVisible ? <CreateLUComponent /> : null }
                </div>
            </Sidebar>
        </Aux>
    );
}

export default AdminPage;