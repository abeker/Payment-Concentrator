import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../../shared/utility'; 

const initialState = {
    books: [],
    totalAmount: 0
};

const addBook = (state, action) => {
    return updateObject(state, { 
        books: state.books.concat(action.book),
        totalAmount: state.totalAmount + action.bookPrice
    });
}

const removeBook = (state, action) => {
    return updateObject(state, { authRedirectPath: action.path });
}

const clearCart = (state, action) => {
    return updateObject(state, { books: [], totalAmount: 0 });
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.ADD_BOOK: return addBook(state, action);
        case actionTypes.REMOVE_BOOK: return removeBook(state, action);
        case actionTypes.CLEAR_CART: return clearCart(state, action);
        default: return state;
    }
}

export default reducer;