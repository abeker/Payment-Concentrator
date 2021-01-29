import { message } from 'antd';
import axios from 'axios';
import React, { Component } from 'react';
import Card from '../UI/Card/CardEntity';
import classes from './Books.module.css';
import Sidebar from '../UI/Sidebar/Sidebar';

class Books extends Component {
    state = {
        bookChunk: null
    }

    // create arrays with chunk size
    createChunks = (arr, chunkSize, cache = []) => {
        const tmp = [...arr]
        if (chunkSize <= 0) return cache
        while (tmp.length) cache.push(tmp.splice(0, chunkSize))
        return cache
    }

    retrieveBooks = () => {
        axios.get('http://localhost:8084/books')
        .then(retrievedBooks => {
            return retrievedBooks.data;
        })
        .then(books => {
            const chunk = this.createChunks(books, 3);
            console.log(chunk);
            this.setState({bookChunk: chunk});
        })
        .catch(error => {
            message.error('Error occured when retrieving books.');
        });
    }

    onClickToCard = (bookInfo) => {
        console.log(bookInfo);
    }

    render() {
        if(this.state.bookChunk === null) {
            this.retrieveBooks();
        }
        return (
            <div className={ classes.Background }>
                <Sidebar />
                { this.state.bookChunk !== null ? 
                    <Card 
                        bookChunk = { this.state.bookChunk }
                        onclickToCard = { this.onClickToCard }/> : null }
            </div>
        )
    }
}

export default Books;