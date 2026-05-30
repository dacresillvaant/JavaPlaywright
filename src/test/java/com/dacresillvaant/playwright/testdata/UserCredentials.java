package com.dacresillvaant.playwright.testdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserCredentials {

    STANDARD_USER("standard_user", "secret_sauce"),
    LOCKED_OUT_USER("locked_out_user", "secret_sauce"),
    PROBLEM_USER("problem_user", "secret_sauce"),
    PERFORMANCE_GLITCH_USER("performance_glitch_user", "secret_sauce");

    private final String username;
    private final String password;
}