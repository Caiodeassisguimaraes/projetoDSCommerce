package com.devsuperior.newdscomerce.repositories;

import com.devsuperior.newdscomerce.entities.Order;
import com.devsuperior.newdscomerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {



}
