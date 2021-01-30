import { message } from 'antd';
import axios from 'axios';
import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import { connect } from 'react-redux';
import Card from '../../components/UI/Card/CardEntity';
import Sidebar from '../../components/UI/Sidebar/Sidebar';
import * as actions from '../../store/actions/index';
import classes from './Books.module.css';

class Books extends Component {
    state = {
        bookChunk: null,
        isAddToCartVisible: true
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
            this.setState({bookChunk: chunk, isAddToCartVisible: true});
        })
        .catch(error => {
            message.error('Error occured when retrieving books.');
        });
    }

    addToCart = (bookEntity) => {
        if(!this.props.books.includes(bookEntity)) {
            this.props.onAddToCart(bookEntity);
            message.info('Succesfully added ' + bookEntity.bookRequest.title);
        }
    }

    listCartContent = () => {
        const chunk = this.createChunks(this.props.books, 3);
        this.setState({bookChunk: chunk, isAddToCartVisible: false});
    }

    render() {
        if(this.state.bookChunk === null) {
            this.retrieveBooks();
        }
        return (
            <div className={ classes.Background }>
                <Sidebar 
                    onclickToCart={ this.listCartContent }
                    onClickToHeader = { this.retrieveBooks }
                    clearCart = { this.props.onClearCart }>
                    { this.state.bookChunk !== null ? 
                        <Card 
                            bookChunk = { this.state.bookChunk }
                            onaddToCart = { this.addToCart }
                            isAddToCartVisible = { this.state.isAddToCartVisible }
                        /> : null 
                    }
                    
                    { !this.state.isAddToCartVisible ? 
                        <Button className={ classes.PurchaseButton } 
                            active variant="primary" size="lg">
                            Purchase
                        </Button> : null
                    }
                </Sidebar>
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        books: state.bookCart.books,
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onAddToCart: (bookEntity) => dispatch(actions.addBook(bookEntity)),
        onClearCart: () => dispatch(actions.clearCart())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Books);