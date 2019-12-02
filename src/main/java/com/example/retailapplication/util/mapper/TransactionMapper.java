package com.example.retailapplication.util.mapper;

import com.example.retailapplication.dto.TransactionDTO;
import com.example.retailapplication.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * This class is used to map Transaction entity to Transaction DTO and vice versa
 */
@Mapper
public interface TransactionMapper {

    /**
     * Map Transaction DTO entity into Transaction
     * @param transactionDTO to be mapped
     * @return Transaction entity
     */
    Transaction transactionDtoToTransaction(TransactionDTO transactionDTO);

    /**
     * Map List<Transaction> into List<TransactionDTO>
     * @param transactionList list of transaction entity to be mapped
     * @return list of Transaction DTO
     */
    List<TransactionDTO> transactionListToTransactionDtoList(List<Transaction> transactionList);
}
