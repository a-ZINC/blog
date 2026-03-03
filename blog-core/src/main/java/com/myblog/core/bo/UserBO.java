package com.myblog.core.bo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBO {

    private Long id;
    private String username;
    private String email;
    private String role;
}
