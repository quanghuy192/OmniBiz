package com.dummy.omni_biz.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dummy.omni_biz.authentication.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    
    public UserInfo findByUsername(String username);
}
