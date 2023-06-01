package com.example.cart.comtroller;


import com.example.cart.controller.CartController;
import com.example.cart.converter.CartConverter;
import com.example.cart.dto.CartDto;
import com.example.cart.dto.StringResponse;
import com.example.cart.model.Cart;
import com.example.cart.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        value = CartController.class,
        properties = {"spring.cloud.config.enabled=false"}
)
@DisplayName("Контроллер корзины должен")
public class CartControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private CartConverter cartConverter;

    @Test
    @DisplayName("генерировать UUID")
    void shouldGenerateUuid() throws Exception {
        when(cartService.generateUuid()).thenReturn(new StringResponse("abcd1234"));
        mockMvc.perform(get("/api/v1/cart/uuid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("abcd1234"));
    }

    @Test
    @DisplayName("добавлять в корзину")
    void shouldAddToCart() throws Exception {
        doNothing().when(cartService).add(anyString(), anyString(), anyLong());
        mockMvc.perform(post("/api/v1/cart/abcd1234/add/1").header("username", "user"))
                .andExpect(status().isOk());
        verify(cartService).add("user", "abcd1234", 1L);
    }

    @Test
    @DisplayName("очищать корзину")
    void shouldClearCart() throws Exception {
        doNothing().when(cartService).clear(anyString(), anyString());
        mockMvc.perform(delete("/api/v1/cart/abcd1234/clear").header("username", "user"))
                .andExpect(status().isOk());
        verify(cartService).clear("user", "abcd1234");
    }

    @Test
    @DisplayName("удалять из корзины")
    void shouldRemoveFromCart() throws Exception {
        doNothing().when(cartService).remove(anyString(), anyString(), anyLong());
        mockMvc.perform(delete("/api/v1/cart/abcd1234/remove/1").header("username", "user"))
                .andExpect(status().isOk());
        verify(cartService).remove("user", "abcd1234", 1L);
    }

    @Test
    @DisplayName("возвращать текущую корзину")
    void shouldGetCurrentCart() throws Exception {
        Cart cart = new Cart();
        CartDto cartDto = new CartDto();
        when(cartService.getCurrentCart(anyString(), anyString())).thenReturn(cart);
        when(cartConverter.entityToDto(cart)).thenReturn(cartDto);
        mockMvc.perform(get("/api/v1/cart/abcd1234").header("username", "user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isEmpty())
                .andExpect(jsonPath("$.totalPrice").isEmpty());
        verify(cartService).getCurrentCart("user", "abcd1234");
        verify(cartConverter).entityToDto(cart);
    }


}
