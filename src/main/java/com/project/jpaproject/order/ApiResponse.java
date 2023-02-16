package com.project.jpaproject.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    private int statusCode;   //상태Code
    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    /**
     * 정상 동작
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, data);
    }

    /**
     * error 응답
     */
    public static <T> ApiResponse<T> fail(int statusCode, T data) {
        return new ApiResponse<>(statusCode, data);
    }
}
