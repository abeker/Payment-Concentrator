import { Button, Tooltip, TrashIcon } from 'evergreen-ui';
import Sidebar from '../../components/UI/Sidebar/Sidebar';
import Aux from '../../hoc/Auxiliary';
import axios from 'axios';
import { useState } from 'react'

const AdminPage = () => {
    const [inactiveButtonVisible, setinactiveButtonVisible] = useState(false)

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
        setinactiveButtonVisible(true);
    }

    return (
        <Aux>
            <Sidebar onclickToDeleteInactive = { changeInactiveButton }>
            { inactiveButtonVisible ?  <Tooltip content="Delete Users that is Inactive more than 150 days">
                    <Button marginY={50} 
                        marginLeft={100} 
                        iconBefore={TrashIcon} 
                        intent="danger"
                        height={40}
                        onClick={ deleteInactiveUsers }>Delete...</Button>
                </Tooltip> : null}
            </Sidebar>
        </Aux>
    );
}

export default AdminPage;