package com.example.retailapplication.util;

import com.example.retailapplication.dto.TransactionDTO;
import com.example.retailapplication.entity.Transaction;
import com.example.retailapplication.util.mapper.ProductMapper;
import com.example.retailapplication.util.mapper.TransactionMapper;
import com.example.retailapplication.util.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    @Test
    public void productToProductDto() {
        assertNull(productMapper.productToProductDto(null));
    }

    @Test
    public void productListToProductDtoList() {
        assertNull(productMapper.productListToProductDtoList(null));
    }

    @Test
    public void transactionDtoToTransaction() {
        assertNull(transactionMapper.transactionDtoToTransaction(null));
    }

    @Test
    public void transactionListToTransactionDtoList() {
        assertNull(transactionMapper.transactionListToTransactionDtoList(null));
    }

    @Test
    public void userToUserDto() {
        assertNull(userMapper.userToUserDto(null));
    }

    @Test
    public void userDtoToUser() {
        assertNull(userMapper.userDtoToUser(null));
    }

    @Test
    public void userListToUserDtoList() {
        assertNull(userMapper.userListToUserDtoList(null));
    }

    @Test
    public void transactionToTransactionDTO() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(null);

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(null);

        assertEquals(transactionDTOList, transactionMapper.transactionListToTransactionDtoList(transactionList));
    }
}