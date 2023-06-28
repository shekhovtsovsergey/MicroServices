package com.shekhovtsov.core.service;

import com.shekhovtsov.core.converter.ProductConverter;
import com.shekhovtsov.core.dto.CartDto;
import com.shekhovtsov.core.model.Order;
import com.shekhovtsov.core.model.OrderItem;
import com.shekhovtsov.core.integration.CartServiceIntegration;
import com.shekhovtsov.core.dao.OrderDao;
import com.shekhovtsov.core.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final ProductServiceImpl productService;
    private final OrderDao orderRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductConverter productConverter;

    @Override
    @Transactional
    public Order createOrder(String username) {
        CartDto cartDto = cartServiceIntegration.getCurrentCart(username);
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        List<OrderItem> items = cartDto.getItems().stream().map(item -> {
            Product product = productConverter.dtoToEntity(productService.findById(item.getProductId()).get());
            return new OrderItem(null,product, order, item.getQuantity(), item.getPricePerProduct(), item.getPrice(),null,null);
        }).collect(Collectors.toList());
        order.setItems(items);

        orderRepository.save(order);
        cartServiceIntegration.clear(username);

        return order;
    }

    @Override
    public List<Order> findByUsername(String username) {
        return orderRepository.findByUsername(username);
    }
}
