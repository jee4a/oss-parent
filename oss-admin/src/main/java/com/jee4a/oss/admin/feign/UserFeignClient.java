package com.jee4a.oss.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth-service", url = "127.0.0.1:8083")
public interface  UserFeignClient {

}
