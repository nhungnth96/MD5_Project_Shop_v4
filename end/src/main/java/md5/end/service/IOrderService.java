package md5.end.service;

import md5.end.exception.BadRequestException;
import md5.end.exception.NotFoundException;
import md5.end.model.dto.request.OrderRequest;
import md5.end.model.dto.response.OrderResponse;
import md5.end.model.entity.order.Order;
import md5.end.model.entity.order.OrderStatus;
import md5.end.model.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface IOrderService extends IGenericService<OrderRequest, OrderResponse> {
    OrderResponse findByUserId(Long id) throws NotFoundException;
    List<OrderResponse> findAllByUserId(Long id);
    OrderResponse findByStatus(OrderStatus orderStatus) throws NotFoundException;
    OrderResponse findByOrderDate(String orderDate) throws NotFoundException;
    OrderResponse updateStatus(OrderRequest orderRequest, Long orderId, OrderStatus orderStatus) throws NotFoundException, BadRequestException;
    OrderResponse cancel(Long id) throws NotFoundException;

}
