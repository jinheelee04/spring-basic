package hello.core.java;

import hello.core.java.discount.DiscountPolicy;
import hello.core.java.discount.RateDiscountPolicy;
import hello.core.java.member.MemberRepository;
import hello.core.java.member.MemberService;
import hello.core.java.member.MemberServiceImpl;
import hello.core.java.member.MemoryMemberRepository;
import hello.core.java.order.OrderService;
import hello.core.java.order.OrderServiceImpl;

public class AppConfig {

    // DI(Dependency Injection) 의존관계 주입 또는 의존성 주입
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository()); // 생성자 주입
    }

    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}