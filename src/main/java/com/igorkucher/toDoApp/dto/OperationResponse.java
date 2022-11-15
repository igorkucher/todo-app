package com.igorkucher.toDoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OperationResponse {
    public OperationResponse(String valueOf) {
        this.status = !valueOf.isEmpty();
    }

    private boolean status;
}
