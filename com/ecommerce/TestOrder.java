package com.ecommerce;

public class TestOrder {

    public static void main(String[] args) {
        Order order = new Order();
        order.addItem("Laptop", 1, 1500);
        order.addItem("Mouse", 2, 50);
        order.addItem("Keyboard", 4, 100);

        order.attach(new PriceObserver());
        order.attach(new QuantityObserver());

        order.showItems();

        System.out.println("Second order:");
        Order order2 = new Order();
        order2.attach(new PriceObserver());
        order2.attach(new QuantityObserver());

        order2.addItem("pen", 2, 1);
        order2.addItem("rule", 1, 1);

        order2.showItems();

    }
}
