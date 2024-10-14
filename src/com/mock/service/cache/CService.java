package com.mock.service.cache;

import com.mock.spring.Autowired;
import com.mock.spring.Component;

@Component
public class CService {
    @Autowired
    private AService aService;
}
