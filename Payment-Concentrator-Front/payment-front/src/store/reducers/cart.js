import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../../shared/utility'; 

const initialState = {
    books: []
};

const addBook = (state, action) => {
    return updateObject(state, { 
        books: state.books.concat(action.payload)
    });
}

const removeBook = (state, action) => {
    return updateObject(state, { authRedirectPath: action.path });
}

const clearCart = (state, action) => {
    return updateObject(state, { books: [] });
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