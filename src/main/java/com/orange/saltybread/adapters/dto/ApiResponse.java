package com.orange.saltybread.adapters.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ApiResponse<TResponse> {

  private int statusCode;
  private TResponse body;


  public static <TResponse> ApiResponse<TResponse> ok(TResponse body) {
    return new ApiResponse<TResponse>(200, body);
  }

  public static <TResponse> ApiResponse<TResponse> conflict() {
    return new ApiResponse<TResponse>(409, null);
  }

  public static <TResponse> ApiResponse<TResponse> unauthorized() {
    return new ApiResponse<TResponse>(401, null);
  }

  public static <TResponse> ApiResponse<TResponse> badRequest() {
    return new ApiResponse<TResponse>(400, null);
  }

  public static <TResponse> ApiResponse<TResponse> notFound() {
    return new ApiResponse<TResponse>(404, null);
  }

  public static <TResponse> ApiResponse<TResponse> forbidden() {
    return new ApiResponse<TResponse>(403, null);
  }
}
