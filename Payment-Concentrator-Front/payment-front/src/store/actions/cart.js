import * as actionTypes from './actionTypes';

export const addBook = (bookEntity) => {
    return {
        type: actionTypes.ADD_BOOK,
        payload: bookEntity
    };
};

export const clearCart = (bookEntity) => {
    return {
        type: actionTypes.CLEAR_CART
    };
};