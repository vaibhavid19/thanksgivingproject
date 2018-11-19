package com.vaibhavi.thanksgivingproject.repository;

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
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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

    @Test
    public void testPostReview() {
        itemController.addItem(this.testItem, "warrier");
        verify(this.itemRepositoryMock, times(1)).save(this.testItem);
    }

}
