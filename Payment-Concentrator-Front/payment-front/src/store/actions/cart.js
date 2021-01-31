import * as actionTypes from './actionTypes';

export const addBook = (book, bookPrice) => {
    return {
        type: actionTypes.ADD_BOOK,
        book: book,
        bookPrice: bookPrice
    };
};

export const clearCart = () => {
    return {
        type: actionTypes.CLEAR_CART
    };
};