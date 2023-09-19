package md5.end.service.amapper;


import md5.end.exception.NotFoundException;
import md5.end.model.dto.request.OrderRequest;
import md5.end.model.dto.response.OrderDetailResponse;
import md5.end.model.dto.response.OrderResponse;
import md5.end.model.entity.order.*;
import md5.end.model.entity.product.Product;
import md5.end.repository.*;
import md5.end.security.principal.UserDetailService;
import md5.end.service.impl.OrderService;
import md5.end.service.impl.ShippingService;
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
    @Autowired
    private IPaymentRepository paymentRepository;
    @Autowired
    private IShippingRepository shippingRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository ;
    @Autowired
    private ShippingService shippingService;
    @Override
    public Order getEntityFromRequest(OrderRequest orderRequest) throws NotFoundException {
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
        double total = 0;
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
            order.getItems().add(orderDetail);
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
        order.setTotal(total);
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
        orderResponse.setShippingDate(order.getShippingDate().toString());
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (OrderDetail item : order.getItems()) {
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setProductId(item.getProduct().getId());
            orderDetailResponse.setProductName(item.getProduct().getName());
            orderDetailResponse.setQuantity(item.getQuantity());
            orderDetailResponse.setPrice(NumberFormat.getInstance().format(item.getProduct().getExportPrice())+"₫");
            orderDetailResponse.setAmount(NumberFormat.getInstance().format(item.getAmount())+"₫");
            orderDetailResponses.add(orderDetailResponse);
        }
        orderResponse.setItems(orderDetailResponses);
        return orderResponse;
    }
}
