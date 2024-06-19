package hello.core.java.order;

import hello.core.java.AppConfig;
import hello.core.java.member.Grade;
import hello.core.java.member.Member;
import hello.core.java.member.MemberService;
import hello.core.spring.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;
//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }
    @Test
    void CreateOrder(){
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    /**
     * Cannot invoke "hello.core.spring.member.MemberRepository.findById(java.lang.Long)" because "this.memberRepository" is null
     */
//    @Test
//    void fieldInjectionTest(){
//        OrderServiceImpl orderService = new OrderServiceImpl(); // autowired 적용 안됨
//        orderService.createOrder(1L, "itemA", 10000);
//    }
}
