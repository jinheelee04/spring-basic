package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifecycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig{
        /**
         * 설정 정보 사용 특징
         * - 매서드 이름을 자유롭게 줄 수 있다.
         * - 스프링 빈이 스프링 코드에 의존하지 않는다.
         * - 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는
         *   외부라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
         *
         *  종료 메서드 추론
         *  - @Bean의 destroyMethod는 기본값이 "(inferred)"로 등록되어 있다.
         *  - 이 추론 기능은 "close", "shutdown"라는 이름의 메서드를 자동으로 호출해준다.
         *  - 따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작한다.
         *  - 추론 기능을 사용하지 않을 경우 destroyMethod = "" 을 사용하면 된다.
         * @return
         */
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
