package io.swagger.petstore.model.builder;

import io.swagger.petstore.model.ApiResponse;

public class ApiResponseBuilder {

    private ApiResponse apiResponse;

    public ApiResponseBuilder() {
        apiResponse = new ApiResponse();
    }

    public ApiResponseBuilder setCode(int code) {
        apiResponse.setCode(code);
        return this;
    }

    public ApiResponseBuilder setType(String type) {
        apiResponse.setType(type);
        return this;
    }

    public ApiResponseBuilder setMessage(String message) {
        apiResponse.setMessage(message);
        return this;
    }

    public ApiResponse build() {
        return apiResponse;
    }
}
