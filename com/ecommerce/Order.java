package com.ecommerce;

public class Order {
    private double totalPrice;
    private int totalQuantity;
    private double shippingCost;

    public void addItem(String item, int quantity, double price) {
        // To be implemented
    }

    public void attach(OrderObserver observer) {
        // To be implemented
    }

    public void detach(OrderObserver observer) {
        // To be implemented
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getCount() {
        return totalQuantity;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    private void notifyObservers() {
        // To be implemented
    }

    public String toString() {
        return "Order{" +
                "totalPrice=" + totalPrice +
                ", totalQuantity=" + totalQuantity +
                ", shippingCost=" + shippingCost +
                '}';
    }
}