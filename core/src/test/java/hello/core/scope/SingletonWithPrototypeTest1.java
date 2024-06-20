package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 정리
 * 매번 사용할 때 마다 의존관계 주입이 완료된 새로운 객체가 필요하면 사용하면 된다.
 * 그러나 실무에서 싱글톤 빈으로 대부분의 문제를 해결 할 수 있다.
 */
public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    /**
     * Provider ( JSR330 자바 표준)
     * provider의 get()을 호출하면 내부에서 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다.(DL)
     *
     * 특징
     * - get()메서드 하나로 기능이 매우 단순하다.
     * - 별도의 라이브러리가 필요하다.
     * - 자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용할 수 있다.
     */
    @Scope("singleton")
    static class ClientBean{
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    /**
     * DL 의존관계 조회(탐색)
     * ObjectProvider, ObjectFactory
     * - 지정한 빈을 컨테이너에서 대신 찾아준다. (DL)
     * prototypeBeanProvider.getObject() 를 통해서 항상 새로운 프로토타입 빈이 생성된다.
     *
     * 특징
     * - ObjectFactory: 기능이 단순, 별도의 라이브러리 필요 없음, 스프링에 의존
     * - ObjectProvider: ObjectFactory 상속, 옵션, 스트림 처리 등 편의 기능이 많고, 별도의 라이브러리 필요 없음, 스프링에 의존
     */
    @Scope("singleton")
    static class ClientBean3{
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    /**
     * 스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다.
     * 싱글톤 빈은 생성 시점에만 의존관계 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만, 싱글톤 빈과 함께 유지된다.
     * 따라서 프로토타입 빈도 한 번만 생성된다.
     */
    @Scope("singleton")
    static class ClientBean2{
        private final PrototypeBean prototypeBean; // 생성시점에 주입

        @Autowired
        public ClientBean2(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic(){
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
