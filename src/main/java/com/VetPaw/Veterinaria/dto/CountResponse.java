package com.VetPaw.Veterinaria.dto;

public class CountResponse {
    private Long count;

    public CountResponse(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }
}
