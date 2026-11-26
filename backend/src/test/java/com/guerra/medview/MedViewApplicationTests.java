package com.guerra.medview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:medview-context;DB_CLOSE_DELAY=-1")
class MedViewApplicationTests {

    @Test
    void contextLoads() {
    }
}
