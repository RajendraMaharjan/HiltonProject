package com.hilton.core.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Response;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResponse {
    private String message;
    private Response.Status status;
}
