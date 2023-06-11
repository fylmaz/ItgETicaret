package com.itgeticaret.services;

import com.itgeticaret.models.PurchaseOrder;
import com.itgeticaret.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public PurchaseOrder getOrderById(long id) {
        Optional<PurchaseOrder> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new IllegalArgumentException("An error occurred - order does not exist");
        }
        return orderOptional.get();
    }

    @Override
    public List<PurchaseOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<PurchaseOrder> getOrdersByUserId(long userId) {
        return null;
    }

    @Override
    public void createOrder(PurchaseOrder purchaseOrder) {
        orderRepository.save(purchaseOrder);
    }

    @Override
    public void updateOrder(long id, PurchaseOrder purchaseOrder) {
        Optional<PurchaseOrder> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isEmpty()) {
            throw new IllegalArgumentException("An error occurred - order does not exist");
        }
        PurchaseOrder existingPurchaseOrder = existingOrderOptional.get();
        existingPurchaseOrder.setUser(purchaseOrder.getUser());
        existingPurchaseOrder.setProducts(purchaseOrder.getProducts());
        existingPurchaseOrder.setTotalAmount(purchaseOrder.getTotalAmount());
        orderRepository.save(existingPurchaseOrder);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteOrder(PurchaseOrder purchaseOrder) {
        orderRepository.delete(purchaseOrder);
    }
}
