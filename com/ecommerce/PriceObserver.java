package com.ecommerce;

public class PriceObserver implements OrderObserver {

    private boolean isApplied = false;

    @Override
    public void update(Order order) {
        if (order.getTotalPrice() > 200 && isApplied == false) {
            order.setDiscount(20);
            this.isApplied = true;
        }
    }
}