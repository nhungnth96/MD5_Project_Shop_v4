package md5.end.repository;

import md5.end.model.entity.order.Order;
import md5.end.model.entity.order.OrderStatus;
import md5.end.model.entity.product.Product;
import md5.end.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUserId(Long id);
    List<Order> findAllByUserId(Long id);
    Optional<Order> findByStatus(OrderStatus orderStatus);
    Optional<Order> findByOrderDate(String String);

}
