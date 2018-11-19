package com.vaibhavi.thanksgivingproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaibhavi.thanksgivingproject.entity.Item;
import com.vaibhavi.thanksgivingproject.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    ItemRepository itemRepositoryMock;

    List<Item> items;
    @Before
    public void setup() {

        items = Arrays.asList(new Item(1, "sword"),
                new Item(3, "wand"),
                new Item(5, "granade"));
    }

    //    Scenario: Create where object not in data store.
//    Given An object of type  "item" with the format { "id": 1, "name": "sword" }, that is not in the data store.
//    When a POST call is made to /object/create/{className}
//    Then The object is registered in the data store and a 200 is returned.
//    All objects have an "id" field that holds their key value.
    @Test
    public void test_addItem() throws Exception {

        when(itemRepositoryMock.save(items.get(1)))
                .thenReturn(items.get(1));

        String json = mapper.writeValueAsString(items.get(1));
        System.out.println(json);
        mockMvc.perform(MockMvcRequestBuilders.post("/object/create/item/wizard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(itemRepositoryMock, times(1)).save(isA(Item.class));
        verifyNoMoreInteractions(itemRepositoryMock);
    }
}
