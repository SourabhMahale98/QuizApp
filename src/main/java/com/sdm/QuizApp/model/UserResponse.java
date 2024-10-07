package com.sdm.QuizApp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserResponse {
    private Integer id;
    private String response;
}
