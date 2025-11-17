package com.dummy.omni_biz.authentication.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    @Schema(description = "Username", example = "sampleUser")
    private String username;

    @Schema(description = "Password", example = "pass@1234")
    private String password;
}
