package com.live.concert.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.live.concert.contract.CreateTicketOrderRequest;
import com.live.concert.contract.CreateTicketOrderResponse;
import com.live.concert.entity.TicketOrder;
import com.live.concert.exception.TicketNotAvailableException;
import com.live.concert.service.TicketOrderService;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TicketOrderController.class)
class TicketOrderControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    EasyRandomParameters parameters = new EasyRandomParameters()
            .randomize(FieldPredicates.named("quantity"), () -> 10);
    EasyRandom easyRandom = new EasyRandom(parameters);
    CreateTicketOrderRequest request = easyRandom.nextObject(CreateTicketOrderRequest.class);
    TicketOrder ticketOrder = easyRandom.nextObject(TicketOrder.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketOrderService ticketOrderService;

    @Test
    public void testCreateTicketOrder_ShouldReturnSuccess() throws Exception {

        when(ticketOrderService.createTicketOrder(anyInt(), anyLong(), anyLong()))
                .thenReturn(ticketOrder);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ticket")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);
        CreateTicketOrderResponse response = objectMapper.readValue(jsonNode.toString(),
                new TypeReference<CreateTicketOrderResponse>() {
                });


        assertEquals(request.getQuantity(), response.getQuantity());
    }

    @Test
    public void testCreateTicketOrder_TicketNotAvailable_ShouldReturnConflict4xx() throws Exception {

        when(ticketOrderService.createTicketOrder(anyInt(), anyLong(), anyLong()))
                .thenThrow(TicketNotAvailableException.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ticket")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void testCreateTicket_ShouldReturnInternalServerError5xx() throws Exception {

        when(ticketOrderService.createTicketOrder(anyInt(), anyLong(), anyLong()))
                .thenThrow(RuntimeException.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/ticket")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn();
    }
}