package com.shekhovtsov.core.service;

import com.shekhovtsov.core.converter.OrderConverter;
import com.shekhovtsov.core.converter.ProductConverter;
import com.shekhovtsov.core.dto.CartDto;
import com.shekhovtsov.core.dto.OrderDto;
import com.shekhovtsov.core.dto.PageDto;
import com.shekhovtsov.core.exception.ResourceNotFoundException;
import com.shekhovtsov.core.integration.CartServiceIntegration;
import com.shekhovtsov.core.model.Order;
import com.shekhovtsov.core.model.OrderItem;
import com.shekhovtsov.core.model.Product;
import com.shekhovtsov.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final ProductServiceImpl productService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductConverter productConverter;
    private final OrderConverter orderConverter;

    @Override
    @Transactional
    public Order createOrder(String username) {
        CartDto cartDto = cartServiceIntegration.getCurrentCart(username);
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        List<OrderItem> items = cartDto.getItems().stream().map(item -> {
            Product product = null;
            try {
                product = productConverter.dtoToEntity(productService.findById(item.getProductId()).get());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
            return new OrderItem(null,product, order, item.getQuantity(), item.getPricePerProduct(), item.getPrice(),null,null);
        }).collect(Collectors.toList());
        order.setItems(items);

        orderRepository.save(order);
        cartServiceIntegration.clear(username);

        return order;
    }

    @Override
    public PageDto<OrderDto> findByUsername(String username) {
    List<Order> orders = orderRepository.findByUsername(username);
        List<OrderDto> orderDtos = orders.stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
        PageDto<OrderDto> out = new PageDto<>();
        out.setPage(1);
        out.setItems(orderDtos);
        out.setTotalPages(1);
        return out;
    }
}
