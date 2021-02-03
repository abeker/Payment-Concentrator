import { message } from 'antd';
import axios from 'axios';
import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import { connect } from 'react-redux';
import Card from '../../components/UI/Card/CardEntity';
import Sidebar from '../../components/UI/Sidebar/Sidebar';
import * as actions from '../../store/actions/index';
import classes from './Books.module.css';
import Modal from '../../components/UI/Modal/SimpleModal';
import { mapArrayToString } from '../../shared/utility';
import { Redirect } from 'react-router';

class Books extends Component {
    state = {
        bookChunk: null,
        isAddToCartVisible: true,
        isModalVisible: false,
        modalContent: null,
        totalPrice: 0,
        isPurchaseFinished: false,
        isMyLibraryVisible: false,
        paymentTypes: []
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
            this.setState({bookChunk: chunk, 
                isAddToCartVisible: true, 
                isPurchaseFinished: false,
                isMyLibraryVisible: false
            });
        })
        .catch(error => {
            message.error('Error occured when retrieving books.');
        });
    }

    addToCart = (bookEntity) => {
        if(!this.props.books.includes(bookEntity)) {
            this.props.onAddToCart(bookEntity, bookEntity.price);
            message.info('Succesfully added ' + bookEntity.bookRequest.title);
        }
    }

    listCartContent = () => {
        if(this.props.books && this.props.books.length > 0) {
            const chunk = this.createChunks(this.props.books, 3);
            this.setState({bookChunk: chunk, 
                isAddToCartVisible: false,
                isMyLibraryVisible: false
            });
        } else {
            message.info('Shopping cart is empty.');
        }
    }

    purchaseCartContent = () => {
        this.setState({isModalVisible: true});
        this.getPaymentTypesForLU();

        let priceSum = 0;
        this.props.books.forEach(book => {
            priceSum += +book.price
        });
        this.setState({totalPrice: priceSum});

        const booksInListItems = this.props.books.map(book => {
            return <li key={book.id}>
                {book.bookRequest.title}, { mapArrayToString(book.bookRequest.writers)} - {book.price} rsd
            </li>;
        });

        this.setState({modalContent: (
            <div>
                <p>Do you want to finish purchase?</p>
                <p>Cart Content:</p>
                <ul>
                    {booksInListItems}
                </ul>
                <hr/>
                <p><strong>Total price: {priceSum} €</strong></p>
            </div>
        )});
    }

    modalClose = () => {
        this.setState({isModalVisible: false});
    }

    finishPurchase = () => {
        this.modalClose();
        this.getPaymentTypesForLU();
        this.setState({isPurchaseFinished: true});
        this.retrieveBooks();
        this.props.onClearCart();
    }

    getPaymentTypesForLU = () => {
        console.log(this.props.luId);
        axios.get(`https://localhost:8443/api/kp/literary-association/${this.props.luId}/payment-list`)
            .then(paymentTypes => {
                console.log(paymentTypes.data);
                this.setState({paymentTypes: paymentTypes.data});
            })
            .catch(error => {
                message.error('Error occured when retrieving payment types.');
            });
    }

    clickToMyLibrary = () => {
        const readerId = JSON.parse(localStorage.getItem('user')).id;
        axios.get(`http://localhost:8084/books/${readerId}`)
            .then(books => {
                const chunk = this.createChunks(books.data, 3);
                this.setState({bookChunk: chunk, 
                    isAddToCartVisible: false,
                    isMyLibraryVisible: true});
            })
            .catch(error => {
                message.error('Error occured when retrieving my books.');
            });
    }

    render() {
        if(this.state.bookChunk === null) {
            this.retrieveBooks();
        }
        return (
            <div>
                <Sidebar 
                    onclickToCart={ this.listCartContent }
                    onClickToHeader = { this.retrieveBooks }
                    clearCart = { this.props.onClearCart }
                    onclickToMyLibrary = { this.clickToMyLibrary }
                    >
                    { this.state.bookChunk !== null ? 
                        <Card 
                            bookChunk = { this.state.bookChunk }
                            onaddToCart = { this.addToCart }
                            isAddToCartVisible = { this.state.isAddToCartVisible }
                        /> : null 
                    }
                    
                    { !this.state.isAddToCartVisible && !this.state.isMyLibraryVisible ? 
                        <Button className={ classes.PurchaseButton } 
                            onClick = { this.purchaseCartContent }
                            active variant="primary" size="lg">
                            Purchase
                        </Button> : null
                    }

                    <Modal
                        isVisible = {this.state.isModalVisible}
                        onClose = {this.modalClose}
                        onConfirm = {this.finishPurchase}
                        modalTitle = "Confirm Purchase">
                        { this.state.modalContent }
                    </Modal>
                </Sidebar>

                { this.state.isPurchaseFinished ? 
                    <Redirect to={{
                        pathname: "/dashboard",
                        state: { 
                            cartContentPrice: this.state.totalPrice,
                            books: this.state.bookChunk,
                            paymentTypes: this.state.paymentTypes
                        }
                      }} /> 
                    : null 
                }
            </div>
        )
    }
}

const mapStateToProps = state => {
    return {
        books: state.bookCart.books,
        totalPrice: state.bookCart.totalPrice,
        luId: state.merchant.luId
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onAddToCart: (bookEntity, bookPrice) => dispatch(actions.addBook(bookEntity, bookPrice)),
        onClearCart: () => dispatch(actions.clearCart())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Books);