package com.payment.bitcoinservice.service;

import com.payment.bitcoinservice.model.BitcoinOrder;
import com.payment.bitcoinservice.model.OrderType;
import com.payment.bitcoinservice.repository.BitcoinOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitcoinPayment {
    @Autowired
    private BitcoinOrderRepository bitcoinOrderRepository;

    public BitcoinOrder saveOrder(BitcoinOrder order){
        return bitcoinOrderRepository.save(order);
    }

    public boolean successfullPayment(String order_id){
        BitcoinOrder bitcoinOrder = bitcoinOrderRepository.findByOrderId(order_id);
        System.out.println(bitcoinOrder.getId() + " " + bitcoinOrder.getOrderId());
        if(bitcoinOrder != null){
            bitcoinOrder.setStatus(OrderType.PAID);
            bitcoinOrderRepository.save(bitcoinOrder);
        }
        return true;
    }

    public boolean cancelledPayment(String order_id){
        BitcoinOrder bitcoinOrder = bitcoinOrderRepository.findByOrderId(order_id);
        if(bitcoinOrder!= null){
            bitcoinOrder.setStatus(OrderType.CANCELED);
            bitcoinOrderRepository.save(bitcoinOrder);
        }
        return true;
    }

}
