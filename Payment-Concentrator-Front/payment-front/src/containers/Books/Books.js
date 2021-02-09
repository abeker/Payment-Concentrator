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
import Membership from '../../components/MembershipPage/MembershipPage';

class Books extends Component {
    state = {
        bookChunk: null,
        isAddToCartVisible: true,
        isModalVisible: false,
        modalContent: null,
        totalPrice: 0,
        isPurchaseFinished: false,
        isMyLibraryVisible: false,
        isMembershipVisible: false,
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
                isMyLibraryVisible: false,
                isMembershipVisible: false
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
            this.getPaymentTypesForLU();
            this.getLuCredentials();
            const chunk = this.createChunks(this.props.books, 3);
            this.setState({bookChunk: chunk, 
                isAddToCartVisible: false,
                isMyLibraryVisible: false,
                isMembershipVisible: false
            });
        } else {
            message.info('Shopping cart is empty.');
        }
    }

    purchaseCartContent = () => {
        this.setState({isModalVisible: true});

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

    getLuCredentials = () => {
        const token = JSON.parse(localStorage.getItem('user')).token;
        axios.get(`http://localhost:8084/la/secret`, {headers: {'Auth-Token': token}})
            .then(luCredentials => {
                console.log(luCredentials.data);
                this.props.onAddLuCredentials(luCredentials.data.secret, luCredentials.data.password);
                this.getLuToken(luCredentials.data.secret, luCredentials.data.password);
            })
            .catch(error => {
                console.log('Fail to load lu credentials.');
            });
    }

    getLuToken = (secret, password) => {
        console.log(secret + " - " + password);
        // this.props.onAddLuToken('eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZXAiLCJzdWIiOiIkMnkkMTIkRXVHSkFrd0RoTzdFa0x0cHRndEJkT1VLL0xKdTRFd05lSHNhbHd4Ni5sVzBaYTM0OVhXLksiLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTI4Nzk1NjYsImV4cCI6MTYxMjg4MDc2NiwidXNlcm5hbWUiOiIkMnkkMTIkRXVHSkFrd0RoTzdFa0x0cHRndEJkT1VLL0xKdTRFd05lSHNhbHd4Ni5sVzBaYTM0OVhXLksifQ.yOmZY6khlSwoXv_t0-rA1HE1wqjgCHkbHM3UwxNGT1m6A3CHPEvCaPDRcCvp0NvF-CB1xJF72Zyg-5ucZIxJ1g');
        axios({
            method: 'put', 
            url: 'https://localhost:8443/api/auth/login',
            data: {
                "secret": secret,
                "password": password
            },
        })
       .then(response => {
            console.log(response.data);
            this.props.onAddLuToken(response.data.token);
        })
        .catch(error => {
            console.log(error);
        });
    }

    clickToMyLibrary = () => {
        const readerId = JSON.parse(localStorage.getItem('user')).id;
        axios.get(`http://localhost:8084/books/${readerId}`)
            .then(books => {
                const chunk = this.createChunks(books.data, 3);
                this.setState({bookChunk: chunk, 
                    isAddToCartVisible: false,
                    isMyLibraryVisible: true,
                    isMembershipVisible: false
                });
            })
            .catch(error => {
                message.error('Error occured when retrieving my books.');
            });
    }

    clickToMembership = () => {
        this.setState({
            bookChunk: null, 
            isAddToCartVisible: true,
            isMyLibraryVisible: false,
            isMembershipVisible: true
        });
    }

    render() {
        if(this.state.bookChunk === null && !this.state.isMembershipVisible) {
            this.retrieveBooks();
        }
        return (
            <div>
                <Sidebar 
                    onclickToCart={ this.listCartContent }
                    onClickToHeader = { this.retrieveBooks }
                    clearCart = { this.props.onClearCart }
                    onclickToMyLibrary = { this.clickToMyLibrary }
                    onclickToMembership = { this.clickToMembership }
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

                    { this.state.isMembershipVisible ? 
                        <Membership /> : null }

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
        luId: state.merchant.luId,
        lu_secret: state.merchant.lu_secret,
        lu_password: state.merchant.lu_password
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onAddToCart: (bookEntity, bookPrice) => dispatch(actions.addBook(bookEntity, bookPrice)),
        onClearCart: () => dispatch(actions.clearCart()),
        onAddLuCredentials: (secret, password) => dispatch(actions.addLuCredentials(secret, password)),
        onAddLuToken: (token) => dispatch(actions.addLuToken(token))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Books);