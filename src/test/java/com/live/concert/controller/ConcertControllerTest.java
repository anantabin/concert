package com.live.concert.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.live.concert.contract.GetConcertResponse;
import com.live.concert.entity.Concert;
import com.live.concert.exception.ConcertNotFoundException;
import com.live.concert.service.ConcertService;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConcertController.class)
class ConcertControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    EasyRandomParameters parameters = new EasyRandomParameters();
    EasyRandom easyRandom = new EasyRandom(parameters);
    List<Concert> concerts = easyRandom.objects(Concert.class, 1).collect(Collectors.toList());
    Concert concert = easyRandom.nextObject(Concert.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConcertService concertService;

    @Test
    public void testGetConcerts_withParameter_ShouldReturnSuccess() throws Exception {

        when(concertService.filterConcerts(anyString(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(concerts));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/concert")
                        .param("name", "ConcertName")
                        .param("concertStartDate", "2022-04-01")
                        .param("concertEndDate", "2022-04-30")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);
        JsonNode contentNode = jsonNode.get("content");
        List<GetConcertResponse> concertResponses = objectMapper.readValue(contentNode.toString(),
                new TypeReference<List<GetConcertResponse>>() {
                });

        assertEquals(1, concertResponses.size());
    }

    @Test
    public void testGetConcert_ShouldReturnSuccess() throws Exception {
        when(concertService.getConcertById(anyLong()))
                .thenReturn(concert);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/concert/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(content);
        GetConcertResponse concertResponse = objectMapper.readValue(jsonNode.toString(),
                new TypeReference<GetConcertResponse>() {
                });


        assertEquals(concert.getId(), concertResponse.getId());
        assertEquals(concert.getName(), concertResponse.getName());
    }

    @Test
    public void testGetConcert_ShouldReturnNotFound4xx() throws Exception {
        when(concertService.getConcertById(anyLong()))
                .thenThrow(ConcertNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/concert/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    public void testGetConcert_ShouldReturnInternalServerError5xx() throws Exception {
        when(concertService.getConcertById(anyLong()))
                .thenThrow(RuntimeException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/concert/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }


}
