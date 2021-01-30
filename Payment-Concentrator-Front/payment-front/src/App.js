import { Redirect, Route, Switch } from 'react-router-dom';
import Dashboard from './components/dashboard/Dashboard';
import PayPalComponent from './components/paypal/PayPalComponent';
import Aux from './hoc/Auxiliary';
import Unicredit from './components/bank/unicredit/Unicredit';
import Raiffeisen from './components/bank/raiffeisen/Raiffeisen';
import "antd/dist/antd.css";
import SuccessPayment from './components/paypal/SuccessfullPayment';
import Bitcoin from './components/bitcoin/Bitcoin';
import Books from './containers/Books/Books';
import CreateLU from './components/createLU/CreateLU';
import ErrorPage from './components/UI/ErrorPage/Error';
import Login from './containers/login/Login';

function App() {
    let routes = (
        <Switch>
            <Route path="/paypal" render={ (props) => <PayPalComponent {...props} /> } />
            <Route path="/create-lu" render={ (props) => <CreateLU {...props} /> } />
            <Route path="/payment-unicredit/:paymentId" render={ (props) => <Unicredit {...props} /> } />
            <Route path="/payment-raiffeisen/:paymentId" render={ (props) => <Raiffeisen {...props} /> } />
            <Route path="/dashboard" render={ (props) => <Dashboard {...props} /> } />
            <Route path="/success" exact  render= {() => <SuccessPayment/>}/>
            <Route path="/error" exact  render= {() => <ErrorPage/>}/>
            <Route path="/bitcoin" exact  render= {() => <Bitcoin/>}/>
            <Route path="/books" exact  render= {() => <Books/>}/>
            <Route path="/" exact render={ (props) => <Login {...props} /> } />
            <Redirect to="/" />
        </Switch>
    );

  return (
    <Aux>
        { routes }
    </Aux>
  );
}

export default App;
