package com.vaibhavi.thanksgivingproject.repository;

import com.vaibhavi.thanksgivingproject.Exception.ItemNotFound;
import com.vaibhavi.thanksgivingproject.controller.ItemController;
import com.vaibhavi.thanksgivingproject.entity.Item;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTests {
    @Mock
    ItemRepository itemRepositoryMock;

    @InjectMocks
    ItemController itemController;

    Item testItem;

    @Before
    public void setUp() throws Exception {
        testItem = new Item(10, "knife");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected= ItemNotFound.class)
    public void test_save() {
        itemController.createItem(this.testItem, "warrior");
        verify(this.itemRepositoryMock, times(1)).save(this.testItem);
    }

    @Test(expected= ItemNotFound.class)
    public void test_getItemByIdAndNameNotPresentInDB() throws Exception {
        itemController.getItemByIdAndName(11, "warrior");
        verify(this.itemRepositoryMock, times(1)).findByItemNameAndId(11, "warrior");
    }

}
