package hello.core.java;

import hello.core.java.AppConfig;
import hello.core.java.member.Grade;
import hello.core.java.member.Member;
import hello.core.java.member.MemberService;
import hello.core.java.order.Order;
import hello.core.java.order.OrderService;

public class OrderApp {

    public static void main(String[] args){
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();
//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "ItemA", 20000);
        System.out.println("order = " + order);
        System.out.println("order.calcualtePrice = " + order.calculatePrice());
    }
}
