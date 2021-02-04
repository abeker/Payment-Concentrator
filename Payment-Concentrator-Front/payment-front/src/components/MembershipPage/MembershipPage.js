import React, { useState } from 'react';
import { useComponentWillMount } from '../Hooks/useComponentWillMount';
import axios from 'axios';
import { message } from 'antd';
import Table from '../UI/Table/Table';
import Button from '@material-ui/core/Button';
import { toaster } from 'evergreen-ui';
import AccountBalanceWalletRoundedIcon from '@material-ui/icons/AccountBalanceWalletRounded';

const MembershipPage = () => {
    const [memberships, setMemberships] = useState(null);
    const [isNeedToPay, setisNeedToPay] = useState(false);
    const [user, setuser] = useState(JSON.parse(localStorage.getItem('user')));

    useComponentWillMount(() => {
        axios.get(`http://localhost:8084/user-memberships/365`, { headers: {'Auth-Token': user.token} })
            .then(retrievedMemberships => {
                console.log(retrievedMemberships.data);
                setMemberships(retrievedMemberships.data.userMemberships);
                setisNeedToPay(retrievedMemberships.data.needToPay);
            })
            .catch(error => {
                message.error('Error occured when retrieving memberships.');
            });
    });

    const payMembership = () => {
        let membershipId;
        if(memberships.length === 0) {
            membershipId = 'empty';
        } else {
            membershipId = memberships[0].membership.id;
        }
        axios.put(`http://localhost:8084/user-memberships/pay/${membershipId}`, {}, { headers: {'Auth-Token': user.token} })
            .then(retrievedMemberships => {
                setMemberships(retrievedMemberships.data.userMemberships);
                setisNeedToPay(retrievedMemberships.data.needToPay);
                toaster.success('You are successfuly paid mambership!');
            })
            .catch(error => {
                message.error('Error occured when retrieving my memberships.');
            });
    }

    return (
        <div>
            { memberships !== null ? <div>
                <Table  content={ memberships } />
                <br/>
                { isNeedToPay ? <Button
                        style={{ marginLeft: "70%" }}
                        variant="contained"
                        color="primary"
                        endIcon={<AccountBalanceWalletRoundedIcon />}
                        onClick={ payMembership }>
                    Pay Membership</Button> : null 
                }
            </div> : null }
        </div>
    );
}

export default MembershipPage;