package com.mock.service.cache;


import com.mock.spring.Autowired;
import com.mock.spring.Component;

@Component
public class AService {

    @Autowired
    private BService bService;

    @Autowired
    private CService cService;

    public void test() {
        System.out.println(bService);
    }
}
