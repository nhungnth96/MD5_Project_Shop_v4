package md5.end.service.impl;

import md5.end.exception.BadRequestException;
import md5.end.exception.NotFoundException;
import md5.end.model.dto.request.OrderRequest;
import md5.end.model.dto.response.OrderResponse;
import md5.end.model.entity.order.*;
import md5.end.model.entity.product.Product;
import md5.end.model.entity.user.User;
import md5.end.repository.ICartItemRepository;
import md5.end.repository.IOrderDetailRepository;
import md5.end.repository.IOrderRepository;
import md5.end.repository.IShippingRepository;
import md5.end.security.principal.UserDetailService;
import md5.end.service.ICartItemService;
import md5.end.service.IOrderService;
import md5.end.service.IShippingService;
import md5.end.service.IUserService;
import md5.end.service.amapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService implements IOrderService {
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IShippingService shippingService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserDetailService userDetailService;
    @Override
    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> orderMapper.getResponseFromEntity(order))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findAllByUserId(Long id) {
        List<Order> orders = orderRepository.findAllByUserId(id);
        return orders.stream()
                .map(order -> orderMapper.getResponseFromEntity(order))
                .collect(Collectors.toList());
    }



    @Override
    public OrderResponse findByStatus(OrderStatus orderStatus) throws NotFoundException {
        Optional<Order> orderOptional = orderRepository.findByStatus(orderStatus);
        if(!orderOptional.isPresent()){
            throw new NotFoundException("Status "+orderStatus+" not found.");
        }
        return orderMapper.getResponseFromEntity(orderOptional.get());
    }

    @Override
    public OrderResponse findByOrderDate(String orderDate) throws NotFoundException {
        Optional<Order> orderOptional = orderRepository.findByOrderDate(orderDate);
        if(!orderOptional.isPresent()){
            throw new NotFoundException("Order of date "+orderDate+" not found.");
        }
        return orderMapper.getResponseFromEntity(orderOptional.get());
    }

    @Override
    public OrderResponse findById(Long id) throws NotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(!orderOptional.isPresent()){
            throw new NotFoundException("Order's id "+id+" not found.");
        }
        return orderMapper.getResponseFromEntity(orderOptional.get());
    }

    @Override
    public OrderResponse findByUserId (Long id) throws NotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(!orderOptional.isPresent()){
            throw new NotFoundException("Order's id "+id+" not found.");
        }
        Order order = orderOptional.get();
        if(!order.getUser().getId().equals(userDetailService.getCurrentUser().getId())){
            throw new NotFoundException("Your orders don't have id "+id);
        }
        return orderMapper.getResponseFromEntity(orderOptional.get());
    }

    @Override
    public OrderResponse save(OrderRequest orderRequest) throws BadRequestException, NotFoundException {
        Order order = orderRepository.save(orderMapper.getEntityFromRequest(orderRequest));
        return orderMapper.getResponseFromEntity(order);
    }
    @Override
    public OrderResponse updateStatus(OrderRequest orderRequest, Long orderId, OrderStatus orderStatus) throws NotFoundException, BadRequestException {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("Order's id "+orderId+" not found.");
        }
        Order order = orderMapper.getEntityFromRequest(orderRequest);
        order.setId(orderId);
        order.setStatus(orderStatus);
        return orderMapper.getResponseFromEntity(orderRepository.save(order));
    }
    @Override
    public OrderResponse cancel(Long id) throws NotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("Order's id "+id+" not found.");
        }
        orderOptional.get().setActive(false);
        orderOptional.get().setStatus(OrderStatus.CANCEL);
        return orderMapper.getResponseFromEntity(orderRepository.save(orderOptional.get()));
    }



    @Override
    public OrderResponse update(OrderRequest orderRequest, Long id) throws NotFoundException {
        return null;
    }

    @Override
    public OrderResponse deleteById(Long id) throws NotFoundException {
        return null;
    }
}
