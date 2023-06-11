package com.itgeticaret.services;

import com.itgeticaret.models.PurchaseOrder;

import java.util.List;

public interface IOrderService {
    PurchaseOrder getOrderById(long id);
    List<PurchaseOrder> getAllOrders();
    List<PurchaseOrder> getOrdersByUserId(long userId);
    void createOrder(PurchaseOrder purchaseOrder);
    void updateOrder(long id, PurchaseOrder purchaseOrder);
    void deleteOrder(long id);
    void deleteOrder(PurchaseOrder purchaseOrder);
}

