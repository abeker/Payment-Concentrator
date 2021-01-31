import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../../shared/utility'; 

const initialState = {
    merchantId: 'LMo0aUBivXliLs9rjBijU096ufdv56',
    merchantPassword: 'p62om0FvEhG70wzCjwrW6rsZCYSY9SikETjbpHNIrJ37Ul6odV4GgV025kFLP7Vwa79XJ8WTsAsDB2D3r9jW26G7a3zp78HbW9zQ',
    luId: 'd0c213f7-1e95-4ce4-8a65-334071e31ce8'
};

const addMerchant = (state, action) => {
    return updateObject(state, { 
        merchantId: action.merchantId,
        merchantPassword: action.merchantPassword,
        luId: action.luId
    });
}

const clearMerchant = (state, action) => {
    return updateObject(state, { merchant: initialState });
}

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.ADD_MERCHANT: return addMerchant(state, action);
        case actionTypes.CLEAR_MERCHANT: return clearMerchant(state, action);
        default: return state;
    }
}

export default reducer;