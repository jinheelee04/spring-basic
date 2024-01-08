package hello.core.singleton;

import hello.core.spring.AppConfig;
import hello.core.spring.member.MemberRepository;
import hello.core.spring.member.MemberServiceImpl;
import hello.core.spring.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {
    /*
     * memberRepository 인스턴스는 모두 같은 인스턴스가 공유되어 사용한다.
     */
    @Test
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    /**
     * 순수한 클래스라면 다음과 같이 출력되어야 한다.
     * ->  class hello.core.spring.AppConfig
     * 클래스 명에 xxxCGLIB가 붙으면 내가 만든 클래스가 아니라 스프링이
     * CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를
     * 상속 받은 임의의 다른 클래스로 만들고 그 다른 클래스를 스프링 빈으로 등록한 것이다.
     * -> 싱글톤 보장된다.
     * 참고: "AppConfig@CGLIB는 AppConfig의 자식 타입으로 AppConfig 타입으로 조회할 수 있다.
     * 정리: @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤 보장하지 않는다.
     * memberRepository()처럼 의존관계 주입이 필요해서 메서드를 직접 호출할 때 싱글톤을 보장하지 않는다.
     * 크게 고민할 것이 없다. 스프링 설정 정보는 항상 @Configuration을 사용하자
     */
    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
    }
}
