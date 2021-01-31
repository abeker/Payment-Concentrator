import * as actionTypes from './actionTypes';

export const addMerchant = (merchantId, merchantPassword, luId) => {
    return {
        type: actionTypes.ADD_MERCHANT,
        merchantId: merchantId,
        merchantPassword: merchantPassword,
        luId: luId
    };
};

export const clearMerchant = () => {
    return {
        type: actionTypes.CLEAR_MERCHANT
    };
};