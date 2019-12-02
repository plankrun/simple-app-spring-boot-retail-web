package com.example.retailapplication.util;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


/**
 * This class is used to store response data that will be shown after request is processed
 * @param <T> for storing various DTO response data
 */
@Data
@Builder
public class Response<T> {
    private LocalDate timestamp;
    private String responseCode;
    private String responseMessage;
    private T data;
}
