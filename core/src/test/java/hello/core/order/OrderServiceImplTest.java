package hello.core.order;

import hello.core.spring.discount.FixDiscountPolicy;
import hello.core.spring.member.Grade;
import hello.core.spring.member.Member;
import hello.core.spring.member.MemoryMemberRepository;
import hello.core.spring.order.Order;
import hello.core.spring.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {
    /**
     * 수정자 주입일 경우 에러
     *  Cannot invoke "hello.core.spring.member.MemberRepository.findById(java.lang.Long)" because "this.memberRepository" is null
     *
     */
    @Test
    void createOrder(){
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L,"name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
