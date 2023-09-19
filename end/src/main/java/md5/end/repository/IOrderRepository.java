package md5.end.repository;

import md5.end.model.entity.order.Order;
import md5.end.model.entity.order.OrderStatus;
import md5.end.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUserId(Long userId);
    Optional<Order> findByStatus(OrderStatus orderStatus);
    Optional<Order> findByOrderDate(String orderDate);

}
