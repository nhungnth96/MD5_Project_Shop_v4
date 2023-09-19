package md5.end.service.impl;

import md5.end.exception.BadRequestException;
import md5.end.exception.NotFoundException;
import md5.end.model.dto.request.OrderRequest;
import md5.end.model.dto.response.OrderResponse;
import md5.end.model.entity.order.*;
import md5.end.model.entity.product.Product;
import md5.end.repository.ICartItemRepository;
import md5.end.repository.IOrderDetailRepository;
import md5.end.repository.IOrderRepository;
import md5.end.repository.IShippingRepository;
import md5.end.service.ICartItemService;
import md5.end.service.IOrderService;
import md5.end.service.IShippingService;
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

    public OrderResponse checkout(OrderRequest orderRequest) throws NotFoundException {
        Order order = orderMapper.getEntityFromRequest(orderRequest);
         orderRepository.save(order);
        double total = 0.0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        List<CartItem> cartItems = order.getUser().getCartItems();
        if(cartItems.isEmpty()){
            throw new NotFoundException("Empty cart");
        }
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(orderDetail.getProduct().getExportPrice());
            orderDetail.setAmount(orderDetail.getProduct().getExportPrice() * orderDetail.getQuantity());
            orderDetail.setOrder(order);
            orderDetails.add(orderDetail);
            total += orderDetail.getAmount();
        }
        if(orderRequest.getShippingId()==1){
            order.setShipping(shippingService.findByType(ShippingType.ECONOMY));
            order.setShippingDate(order.getOrderDate().plusDays(3));
            order.setTotal(order.getTotal()+order.getShipping().getPrice());
        } else if (orderRequest.getShippingId()==2) {
            order.setShipping(shippingService.findByType(ShippingType.FAST));
            order.setShippingDate(order.getOrderDate().plusDays(2));
            order.setTotal(order.getTotal()+order.getShipping().getPrice());
        }  else if (orderRequest.getShippingId()==3) {
            order.setShipping(shippingService.findByType(ShippingType.EXPRESS));
            order.setShippingDate(order.getOrderDate().plusDays(1));
            order.setTotal(order.getTotal()+order.getShipping().getPrice());
        } else {
            throw new NotFoundException("Cannot find this shipping type");
        }
        orderDetailRepository.saveAll(orderDetails);
        order.setItems(orderDetails);
        order.setTotal(total);
        cartItemRepository.deleteAll(cartItems);
        return orderMapper.getResponseFromEntity(order);
    }

    @Override
    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> orderMapper.getResponseFromEntity(order))
                .collect(Collectors.toList());
    }
    @Override
    public OrderResponse findByUserId(Long userId) throws NotFoundException {
        Optional<Order> orderOptional = orderRepository.findByUserId(userId);
        if(!orderOptional.isPresent()){
            throw new NotFoundException("Order of user's id "+userId+" not found.");
        }
        return orderMapper.getResponseFromEntity(orderOptional.get());
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
    public OrderResponse save(OrderRequest orderRequest) throws BadRequestException, NotFoundException {
        Order order = orderRepository.save(orderMapper.getEntityFromRequest(orderRequest));
        return orderMapper.getResponseFromEntity(order);
    }
    @Override
    public OrderResponse updateStatus(OrderRequest orderRequest, Long orderId, OrderStatus orderStatus) throws NotFoundException {
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
