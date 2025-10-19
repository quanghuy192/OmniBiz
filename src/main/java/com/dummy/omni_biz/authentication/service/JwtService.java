package com.dummy.omni_biz.authentication.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.dummy.omni_biz.authentication.entity.UserInfo;
import com.dummy.omni_biz.authentication.repository.UserInfoRepository;
import com.dummy.omni_biz.authentication.util.JwtUtil;
import com.dummy.omni_biz.exception.UserNotFoundException;

@Service
public class JwtService {

    private JwtUtil jwtUtil ;
    private UserInfoRepository userInfoRepository;

    public JwtService(JwtUtil jwtUtil, UserInfoRepository userInfoRepository) {
        this.jwtUtil = jwtUtil;
        this.userInfoRepository = userInfoRepository;
    }

    public String generateToken(String username) {
        final UserInfo userInfo = userInfoRepository.findByUsername(username);
        if(Objects.isNull(userInfo)) {
            throw new UserNotFoundException("User not found");
        }
        return jwtUtil.generateToken(username, List.of(userInfo.getRole()));
    }
    
}
