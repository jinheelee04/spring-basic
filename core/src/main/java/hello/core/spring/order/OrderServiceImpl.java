package hello.core.spring.order;

//import hello.core.java.discount.FixDiscountPolicy;
//import hello.core.java.discount.RateDiscountPolicy;

import hello.core.spring.discount.DiscountPolicy;
import hello.core.spring.member.Member;
import hello.core.spring.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
//    private final MemberRepository memberRepository = new MemoryMemberRepository(); // DIP 위반
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // DIP 위반(추상화 뿐만 아니라 구체화에도 의존하고 있음)
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // OCP 위반 FixDiscountPolicy -> RateDiscountPolicy로 변경하는 순간 ServiceImpl도 변경해야 함
    //  OCP 위반, 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 줌

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
