package md5.end.service.amapper;


import md5.end.model.dto.request.OrderRequest;
import md5.end.model.dto.response.OrderResponse;
import md5.end.model.entity.order.Order;
import md5.end.model.entity.order.OrderDetail;
import md5.end.model.entity.order.OrderStatus;
import md5.end.model.entity.product.Product;
import md5.end.repository.*;
import md5.end.security.principal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderMapper implements IGenericMapper<Order, OrderRequest, OrderResponse> {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IUserRepository userRepository;
    private @Autowired
    IPaymentRepository paymentRepository;
    private @Autowired
    IShippingRepository shippingRepository;
    @Override
    public Order getEntityFromRequest(OrderRequest orderRequest) {
        Order order = new Order();
        order.setUser(userDetailService.getCurrentUser());
        order.setReceiver(orderRequest.getReceiver());
        order.setAddress(orderRequest.getAddress());
        order.setTel(orderRequest.getTel());
        order.setNote(orderRequest.getNote());
        order.setOrderDate(LocalDateTime.now());
        order.setPayment(paymentRepository.findById(orderRequest.getPaymentId()).get());
        order.setStatus(OrderStatus.PENDING);
        order.setActive(true);

        return order;
    }

    @Override
    public OrderResponse getResponseFromEntity(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setOwner(order.getUser().getFullName());
        orderResponse.setReceiver(order.getReceiver());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setTel(order.getTel());
        orderResponse.setNote(order.getNote());
        orderResponse.setTotal(NumberFormat.getInstance().format(order.getTotal()) + "₫");
        orderResponse.setOrderDate(order.getOrderDate().toString());
        orderResponse.setStatus(order.getStatus().name());
        orderResponse.setPayment(order.getPayment().getType().toString());
        orderResponse.setShipping(order.getShipping().getType().toString());
        if (orderResponse.getShipping().equals("ECONOMY")) {
            orderResponse.setShippingDate(order.getOrderDate().plusDays(3).toString());
        } else if (orderResponse.getShipping().equals("FAST")) {
            orderResponse.setShippingDate(order.getOrderDate().plusDays(2).toString());
        } else if (orderResponse.getShipping().equals("EXPRESS")) {
            orderResponse.setShippingDate(order.getOrderDate().plusDays(1).toString());
        }
        return orderResponse;
    }
}
