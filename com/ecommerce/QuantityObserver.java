package com.ecommerce;

public class QuantityObserver implements OrderObserver {
    @Override
    public void update(Order order) {
        if (order.getTotalQuantiy() > 5) {
            order.setShippingCost(0);
            order.updateOrderPrice();
        }
    }
}