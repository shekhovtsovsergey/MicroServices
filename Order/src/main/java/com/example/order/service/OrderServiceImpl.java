package com.example.order.service;

import com.example.order.dto.OrderDto;
import com.example.order.dto.PaymentDto;
import com.example.order.dto.PaymentDtoRequest;
import com.example.order.dto.PaymentDtoResponse;
import com.example.order.integration.DeliveryServiceIntegration;
import com.example.order.integration.PaymentServiceIntegration;
import com.example.order.integration.ResponseWrapper;
import com.example.order.integration.WareServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final DeliveryServiceIntegration deliveryServiceIntegration;
    private final WareServiceIntegration wareServiceIntegration;
    private final PaymentServiceIntegration paymentServiceIntegration;


    @Override
    public ResponseEntity<String> createOrder(OrderDto orderDto) {

        PaymentDtoRequest paymentDtoRequest = new PaymentDtoRequest(orderDto.getDebitAccount(),orderDto.getAmount());

        if (deliveryServiceIntegration.getDelivery() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

        PaymentDtoResponse paymentDtoResponse = paymentServiceIntegration.getPayment(paymentDtoRequest);
        Long prepareFromPayment = paymentDtoResponse.getId();
        if (prepareFromPayment == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }


        ResponseWrapper<Long> wareResponse = wareServiceIntegration.getWare();
        if (wareResponse.isError()) {
            paymentServiceIntegration.cancelPayment(prepareFromPayment);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
        Long prepareFromWare = wareResponse.unwrap();

        deliveryServiceIntegration.getDelivery();
        wareServiceIntegration.getWare();
        paymentServiceIntegration.commitePayment(prepareFromPayment);
        return ResponseEntity.status(HttpStatus.OK).body("Ok");
    }



}