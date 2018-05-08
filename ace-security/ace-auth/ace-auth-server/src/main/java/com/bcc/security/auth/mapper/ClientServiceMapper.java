package com.bcc.security.auth.mapper;

import com.bcc.security.auth.entity.ClientService;

import tk.mybatis.mapper.common.Mapper;

public interface ClientServiceMapper extends Mapper<ClientService> {
    void deleteByServiceId(int id);
}