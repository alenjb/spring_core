package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.defaultanswers.ReturnsSmartNulls;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadsA: A사용자 10000원 주문
        int price1 =statefulService1.order("userA", 10000);
        //ThreadsA: B사용자 20000원 주문
        int price2 =statefulService2.order("userB", 20000);

        //ThreadsA: A사용자 주문 금액 조회
//        int price1 = statefulService1.getPrice();
        assertThat(price1).isEqualTo(10000);
        //ThreadsA: B사용자 주문 금액 조회
//        int price2 = statefulService2.getPrice();
        assertThat(price2).isEqualTo(20000);

    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}