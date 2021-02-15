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

export const addLuCredentials = (lu_secret, lu_password) => {
    return {
        type: actionTypes.ADD_LU_CREDENTIALS,
        lu_secret: lu_secret,
        lu_password: lu_password
    };
};

export const addLuToken = (lu_token) => {
    return {
        type: actionTypes.ADD_LU_TOKEN,
        lu_token: lu_token
    };
};