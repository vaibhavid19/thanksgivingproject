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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                new Item(5, "granade"),
                new Item(300, "rifle"),
                new Item(101, "dagger"));
    }

    @Test
    public void test_createItemNotPresentInDB() throws Exception {

        Item testItem = new Item();
        testItem.setName("dagger");

        Item testItemSavedInDB = new Item(1001, "dagger");
        when(itemRepositoryMock.save(testItem))
                .thenReturn(testItemSavedInDB);

        String json = mapper.writeValueAsString(testItem);
        System.out.println(json);
        mockMvc.perform(MockMvcRequestBuilders.post("/object/create/dagger")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(itemRepositoryMock, times(1)).save(isA(Item.class));
        verifyNoMoreInteractions(itemRepositoryMock);
    }

    @Test
    public void test_createItemPresentInDB() throws Exception {

        when(itemRepositoryMock.save(items.get(3)))
                .thenReturn(new Item(items.get(3).getId(), "rifle new!"));

        when(itemRepositoryMock.existsById(items.get(3).getId()))
                .thenReturn(true);
        String json = mapper.writeValueAsString(items.get(3));
        System.out.println(json);
        mockMvc.perform(MockMvcRequestBuilders.post("/object/create/rifle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                //.andExpect(jsonPath("$.name", instanceOf(String.class)))
                //.andExpect(MockMvcResultMatchers.jsonPath("$").value("rifle new!))
                .andExpect(status().isOk());

        verify(itemRepositoryMock, times(1)).save(isA(Item.class));
        verify(itemRepositoryMock, times(1)).existsById(items.get(3).getId());
        verifyNoMoreInteractions(itemRepositoryMock);
    }

    @Test
    public void test_deleteItemNotPresentInDB() throws Exception {

        Item testItem = new Item(4044, "new-weapon");
        when(itemRepositoryMock.existsById(4044)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/object/delete/new-weapon/4044"))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(itemRepositoryMock, times(1)).existsById(4044);
        verifyNoMoreInteractions(itemRepositoryMock);
    }

    @Test
    public void test_deleteItemPresentInDB() throws Exception {

        //doNothing().when(reviewRepository).(anyLong());
        Item testItem = new Item(999, "new-weapon");
        when(itemRepositoryMock.existsById(999)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/object/delete/new-weapon/999"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(itemRepositoryMock, times(1)).existsById(999);
        verify(itemRepositoryMock, times(1)).deleteById(999);
        verifyNoMoreInteractions(itemRepositoryMock);
    }

    @Test
    public void test_getItemByIdAndNamePresentInDB() throws Exception {

        when(itemRepositoryMock.findByItemNameAndId(11, "bomb"))
                .thenReturn(Optional.of(new Item(11,"bomb")));


        mockMvc.perform(MockMvcRequestBuilders.get("/object/get/bomb/11"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(itemRepositoryMock, times(1)).findByItemNameAndId(11, "bomb");
        verifyNoMoreInteractions(itemRepositoryMock);
    }

    @Test
    public void test_getAllItemsByName() throws Exception {

        when(itemRepositoryMock.findByItemName("bomb")).thenReturn(Arrays.asList(items.get(1), items.get(2)));
        mockMvc.perform(MockMvcRequestBuilders.get("/object/get/bomb"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
        //.andExpect(jsonPath("$[0].movieId", is(10L)));
        //.andExpect((ResultMatcher) jsonPath("$[0].movieTitle", is("Avengers")))
        //.andExpect((ResultMatcher) jsonPath("$[1].movieId", is(13L)))
        //.andExpect((ResultMatcher) jsonPath("$[1].movieTitle", is("Antman")));

        verify(itemRepositoryMock, times(1)).findByItemName("bomb");
        verifyNoMoreInteractions(itemRepositoryMock);
    }

    @Test
    public void test_getAllItems() throws Exception {

        when(itemRepositoryMock.findAll()).thenReturn(items);
        mockMvc.perform(MockMvcRequestBuilders.get("/object/get/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(5)));
        //.andExpect(jsonPath("$[0].movieId", is(10L)));
        //.andExpect((ResultMatcher) jsonPath("$[0].movieTitle", is("Avengers")))
        //.andExpect((ResultMatcher) jsonPath("$[1].movieId", is(13L)))
        //.andExpect((ResultMatcher) jsonPath("$[1].movieTitle", is("Antman")));

        verify(itemRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(itemRepositoryMock);
    }

}
