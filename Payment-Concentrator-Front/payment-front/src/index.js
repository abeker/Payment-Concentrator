import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { applyMiddleware, combineReducers, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import App from './App';
import './index.css';
import reportWebVitals from './reportWebVitals';
import cartReducer from './store/reducers/cart';
import merchantReducer from './store/reducers/merchant';

const rootReducer = combineReducers({
    bookCart: cartReducer,
    merchant: merchantReducer
});

const composeEnhancers = process.env.NODE_ENV === 'development' 
                            ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
                            :null 
                        || compose;
const store = createStore(
    rootReducer, 
    composeEnhancers(applyMiddleware(thunk))
);

const app = (
    <Provider store={ store }>
        <BrowserRouter> 
            <App /> 
        </BrowserRouter>
    </Provider>
);

ReactDOM.render(app , document.getElementById('root'));
reportWebVitals();
