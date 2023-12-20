package hello.core.spring;

import hello.core.spring.member.Grade;
import hello.core.spring.member.Member;
import hello.core.spring.member.MemberService;
import hello.core.spring.order.Order;
import hello.core.spring.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "ItemA", 20000);
        System.out.println("order = " + order);
        System.out.println("order.calcualtePrice = " + order.calculatePrice());
    }
}
