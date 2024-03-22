package com.example.data.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "Health-Check",
        description = "endpoints for health check API"
)
public class SuccessController {
    @GetMapping("/success")
    @Operation(
            summary = "health check",
            description = "health check endpoint,"
                    + " must return \"success\" plain text",
            tags = {"Health-Check"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE
                            )
                    }
            )
    })
    public String healthCheck() {
        return "success";
    }
}
