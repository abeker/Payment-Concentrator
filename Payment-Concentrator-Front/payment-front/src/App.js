import { Redirect, Route, Switch } from 'react-router-dom';
import Dashboard from './components/dashboard/Dashboard';
import PayPalComponent from './components/paypal/PayPalComponent';
import Aux from './hoc/Auxiliary';
import Unicredit from './components/bank/unicredit/Unicredit';
import Raiffeisen from './components/bank/raiffeisen/Raiffeisen';
import "antd/dist/antd.css";
import SuccessPayment from './components/paypal/SuccessfullPayment';

// const RenderRoutes=(route)=>{
//   document.title = route.title || 'App name'

//   return (
//     <Route
//       path={route.path}
//       exact
//       render={(props)=><route.component {...props}/>}>
//     </Route>
//   )
// }

function App() {
    let routes = (
        <Switch> 
            <Route path="/paypal" render={ (props) => <PayPalComponent {...props} /> } />
            <Route path="/payment-unicredit/:paymentId" render={ (props) => <Unicredit {...props} /> } />
            <Route path="/payment-raiffeisen/:paymentId" render={ (props) => <Raiffeisen {...props} /> } />
            <Route path="/" exact render={ (props) => <Dashboard {...props} /> } />
            <Route path="/success" exact  render= {() => <SuccessPayment/>}/>
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
