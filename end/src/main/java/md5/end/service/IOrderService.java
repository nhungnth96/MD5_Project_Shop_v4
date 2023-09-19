package md5.end.service;

import md5.end.exception.NotFoundException;
import md5.end.model.dto.request.OrderRequest;
import md5.end.model.dto.response.OrderResponse;
import md5.end.model.entity.order.Order;
import md5.end.model.entity.order.OrderStatus;

import java.util.Optional;

public interface IOrderService extends IGenericService<OrderRequest, OrderResponse> {
    OrderResponse findByUserId(Long userId) throws NotFoundException;
    OrderResponse findByStatus(OrderStatus orderStatus) throws NotFoundException;
    OrderResponse findByOrderDate(String orderDate) throws NotFoundException;
    OrderResponse updateStatus(OrderRequest orderRequest, Long orderId, OrderStatus orderStatus) throws NotFoundException;
    OrderResponse cancel(Long id) throws NotFoundException;
    OrderResponse checkout(OrderRequest orderRequest) throws NotFoundException;
}
