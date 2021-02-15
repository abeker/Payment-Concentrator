import { message } from 'antd';
import axios from 'axios';
import React, { useState } from 'react';
import Membership from '../../components/MembershipPage/MembershipPage';
import NewBookForm from '../../components/UI/NewBookForm/NewBookForm';
import Sidebar from '../../components/UI/Sidebar/Sidebar';
import classes from './WriterPage.module.css';

const WriterPage = (props) => {
    const [isMembershipVisible, setisMembershipVisible] = useState(false);
    const [isBookFormVisible, setisBookFormVisible] = useState(false);
    const [genres, setGenres] = useState([]);
    const [selectedGenres, setselectedGenres] = useState([]);

    const changeMembershipVisibility = () => {
        setisMembershipVisible(true);
        setisBookFormVisible(false);
    }

    const changeAddBookVisibility = () => {
        // get genres
        axios.get('http://localhost:8084/genres')
            .then(genreList => {
                console.log(genreList.data);
                setGenres(genreList.data);
            })
            .catch(error => {
                console.log(error);
            });
        setisMembershipVisible(false);
        setisBookFormVisible(true);
    }

    const onSubmit = (event) => {
        const body = {...event, genreIds: selectedGenres}        
        console.log(body);
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.post('http://localhost:8084/writers/create-book', body, {headers: {'Auth-Token': token}})
            .then(response => {
                console.log(response.data);
                message.success('Successfuly added book.');
            })
            .catch(error => {
                console.log(error);
            });
    }

    const handleGenres = (value) =>  {
        console.log(value);
        setselectedGenres(value);
    }

    return (
        <div>
            <Sidebar 
                onclickToMembershipWriter={ changeMembershipVisibility }
                onclickToAddNewBook={ changeAddBookVisibility }>
                    
                { isMembershipVisible ? 
                        <Membership /> : null }
                
               
                { isBookFormVisible ? 
                 <div className={ classes.mainDiv }>
                        <NewBookForm 
                            onSubmit={ onSubmit }
                            handleGenres={ handleGenres }
                            genres={ genres } /></div> : null }
                
            </Sidebar>
        </div>
    );
}

export default WriterPage;