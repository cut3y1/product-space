package com.example.product;

import com.example.product.controller.ProductController;
import com.example.product.service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired MockMvc mvc;
    @MockitoBean ProductService service;
    @Test void invalidProductIsRejected() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\" \",\"price\":-1,\"stock\":-2}"))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("errors.name").exists())
            .andExpect(jsonPath("errors.price").exists()).andExpect(jsonPath("errors.stock").exists());
        verifyNoInteractions(service);
    }
    @Test void invalidPaginationIsRejected() throws Exception {
        mvc.perform(get("/api/products?size=101")).andExpect(status().isBadRequest());
        mvc.perform(get("/api/products?page=-1")).andExpect(status().isBadRequest());
        verifyNoInteractions(service);
    }
    @Test void malformedJsonIsRejected() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content("{"))
            .andExpect(status().isBadRequest());
    }
    @Test void missingProductReturns404() throws Exception {
        when(service.get(99)).thenThrow(new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,"商品不存在"));
        mvc.perform(get("/api/products/99")).andExpect(status().isNotFound()).andExpect(jsonPath("message").value("商品不存在"));
    }
}

