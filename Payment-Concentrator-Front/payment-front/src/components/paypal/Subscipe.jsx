import React from 'react'
import { PayPalButton } from "react-paypal-button-v2";

const Subscipe = () => {
    return (
        <div>
            <br/>
            Subscribe
            <PayPalButton
                amount="1"
                currency="USD"
                options={{vault: true}}
                // shippingPreference="NO_SHIPPING" // default is "GET_FROM_FILE"
                createSubscription={(data, actions) => {
                    return actions.subscription.create({
                        plan_id: 'P-8NG57212U9943414UL75SUCA'
                    });
                }}

                onApprove={(data, actions) => {
                // Capture the funds from the transaction
                return actions.subscription.get().then(function(details) {
                    // Show a success message to your buyer
                    alert("Subscription completed");
        
                    // OPTIONAL: Call your server to save the subscription
                    // return fetch("/paypal-subscription-complete", {
                    // method: "post",
                    // body: JSON.stringify({
                    //     orderID: data.orderID,
                    //     subscriptionID: data.subscriptionID
                    // })
                    // });
                });
                } 
            }
            />
        </div>
        )
}

export default Subscipe
