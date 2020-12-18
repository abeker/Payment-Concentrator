import PayPal from '../components/paypal/PayPalComponent';
import SuccessPayment from '../components/paypal/SuccessfullPayment';

const routes = [
    {
        path: '/paypal',
        component: PayPal,
        title:"PayPal",
    },
    {
        path: '/success',
        component: SuccessPayment,
        title:"Successful payment",
    }
]

export default routes;