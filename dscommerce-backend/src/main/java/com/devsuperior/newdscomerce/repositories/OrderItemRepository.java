package com.devsuperior.newdscomerce.repositories;

import com.devsuperior.newdscomerce.entities.OrderItem;
import com.devsuperior.newdscomerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {



}
