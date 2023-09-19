package md5.end.controller;

import md5.end.exception.BadRequestException;
import md5.end.exception.NotFoundException;
import md5.end.model.dto.request.OrderRequest;
import md5.end.model.dto.request.ProductRequest;
import md5.end.model.dto.response.OrderResponse;
import md5.end.model.entity.order.OrderStatus;
import md5.end.service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<OrderResponse> checkout(
            @Valid
            @RequestBody OrderRequest orderRequest) throws NotFoundException {
        return new ResponseEntity<>(orderService.checkout(orderRequest), HttpStatus.OK);

    }

    @GetMapping("")
    public ResponseEntity<List<OrderResponse>> getAll() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOne(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateStatus(
            @Valid
            @RequestBody OrderRequest orderRequest,
            @PathVariable Long id,
            @RequestParam (name = "status")OrderStatus status) throws NotFoundException {
        return new ResponseEntity<>(orderService.updateStatus(orderRequest, id,status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> cancel(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(orderService.cancel(id), HttpStatus.OK);

    }
}
