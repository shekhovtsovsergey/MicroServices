package com.example.core.service;

import com.example.core.converter.ProductConverter;
import com.example.core.dto.CartDto;
import com.example.core.integration.BookingServiceIntegration;
import com.example.core.kafka.MessageProducer;
import com.example.core.model.Order;
import com.example.core.model.OrderItem;
import com.example.core.integration.CartServiceIntegration;
import com.example.core.dao.OrderDao;
import com.example.core.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final ProductServiceImpl productService;
    private final OrderDao orderRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductConverter productConverter;
    private final BookingServiceIntegration bookingServiceIntegration;
    private final MessageProducer messageProducer;

    @Override
    @Transactional
    public Order createOrder(String username) {

        System.out.println("метка: зашли в метод");
        if (!bookingServiceIntegration.createOrder()) {
            System.out.println("метка: вернулась ошибка");
            return null;
        }
        System.out.println("метка: вернулся успех");

        CartDto cartDto = cartServiceIntegration.getCurrentCart(username);
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(BigDecimal.valueOf(1000));
        order.setAddress("Триумфальная пл., Москва, 125047");
        order.setPhone("+7 000-000-00-00");
        List<OrderItem> items = cartDto.getItems().stream().map(item -> {
            Product product = productConverter.dtoToEntity(productService.findById(item.getProductId()).get());
            return new OrderItem(null,product, order, item.getQuantity(), item.getPricePerProduct(), item.getPrice(),null,null);
        }).collect(Collectors.toList());
        order.setItems(items);

        orderRepository.save(order);
        messageProducer.generateAndSendMessage();
        cartServiceIntegration.clear(username);

        return order;
    }

    @Override
    public List<Order> findByUsername(String username) {
        return orderRepository.findByUsername(username);
    }
}
