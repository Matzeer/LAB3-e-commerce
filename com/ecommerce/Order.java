package com.ecommerce;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order {
    private double totalPrice;
    private int totalQuantity;
    private double shippingCost;
    private double discount;
    private String id;
    private double orderPrice;
    private List<OrderObserver> observers = new ArrayList<>();
    private List<Map<String, Object>> items = new ArrayList<>();

    public Order() {
        this.totalPrice = 0.0;
        this.totalQuantity = 0;
        this.shippingCost = 10.0;
        this.discount = 0.0;
        this.orderPrice = getTotalPrice() + getShippingCost();
        this.id = UUID.randomUUID().toString();
    }

    /**
     * give the index of the item in the list of items.
     * 
     * @param item  The name of the item.
     * @param price The price of the item.
     * @return The index of the list if item is found, otherwise -1.
     */
    public int getIdItemInOrder(String item, double price) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).get("name") == item) {
                // If price is not same as the actual price, throw an exception
                Double actualItemPrice = (Double) items.get(i).get("price");
                if (actualItemPrice != price) {
                    throw new IllegalArgumentException("Price does not match for item: " + item);
                }
                return i;
            }
        }
        return -1; // Item not found, we will add it as a new item
    }

    public void addItem(String item, int quantity, double price) {
        if (item == null || item.isEmpty() || quantity <= 0 || price < 0) {
            throw new IllegalArgumentException("Invalid item details");
        }

        int itemIndex = getIdItemInOrder(item, price);
        if (itemIndex != -1) {
            // We update the existing item
            Map<String, Object> existingItem = items.get(itemIndex);
            int existingQuantity = (Integer) existingItem.get("quantity");
            existingItem.put("quantity", existingQuantity + quantity);
            setTotalQuantity(getTotalQuantiy() + quantity);
        } else {
            // We add a new item
            Map<String, Object> newItem = Map.of("name", item, "quantity", quantity, "price", price);
            items.add(newItem);
            setTotalQuantity(getTotalQuantiy() + quantity);
            setTotalPrice(getTotalPrice() + price * quantity);
        }
        updateOrderPrice();
        notifyObservers();
    }

    public void attach(OrderObserver observer) {
        if (observer != null) {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Observer cannot be null");
        }
    }

    public void detach(OrderObserver observer) {
        if (observer != null) {
            if (!observers.contains(observer)) {
                throw new IllegalArgumentException("Observer not found in the list");
            }
            observers.remove(observer);
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Observer cannot be null");
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getTotalQuantiy() {
        return totalQuantity;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void updateOrderPrice() {
        this.orderPrice = getTotalPrice() + getShippingCost() - getDiscount();
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            if (observer != null) {
                observer.update(this);
            }
        }
    }

    public String toString() {
        return "Order{" +
                "totalPrice=" + totalPrice +
                ", totalQuantity=" + totalQuantity +
                ", shippingCost=" + shippingCost +
                '}';
    }

    public void showItems() {
        System.out.println("Order #" + id + ":");

        for (Map<String, Object> item : items) {
            System.out.println("Item: " + item.get("name") + ", Quantity: " + item.get("quantity") + ", Price: "
                    + item.get("price"));
        }
        System.out.println("============================");
        System.out.println("Total Price: " + getOrderPrice());
        System.out.println("Shipping Cost: " + getShippingCost());
        System.out.println("Discount: " + getDiscount());
        System.out.println("Total Quantity: " + getTotalQuantiy());
        System.out.println("============================");
    }
}