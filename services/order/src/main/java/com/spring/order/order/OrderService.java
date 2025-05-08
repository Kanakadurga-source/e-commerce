package com.spring.order.order;

import com.spring.order.customer.CustomerClient;
import com.spring.order.exception.BusinessException;
import com.spring.order.kafka.OrderConfirmation;
import com.spring.order.kafka.OrderProducer;
import com.spring.order.orderline.OrderLineRequest;
import com.spring.order.orderline.OrderLineService;
import com.spring.order.payment.PaymentClient;
import com.spring.order.payment.PaymentRequest;
import com.spring.order.product.ProductClient;
import com.spring.order.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public Integer createdOrder(@Valid OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exist with the id"));

        //purchase the product : Product microservices (RestTemplate)
      var purchasedProducts =  this.productClient.purchaseProducts(request.products());

        //persist Order
       var order = this.repository.save(mapper.toOrder(request));

       //persist the order lines
        for (PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                            
                    )
            );
        }
        //to do start payment process

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        //send the order confirmation --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts

                )
        );


        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format("order not found with the provided id: %d",orderId)));
    }
}
