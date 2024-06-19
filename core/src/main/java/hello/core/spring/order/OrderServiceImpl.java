package hello.core.spring.order;

//import hello.core.java.discount.FixDiscountPolicy;
//import hello.core.java.discount.RateDiscountPolicy;

import hello.core.spring.discount.DiscountPolicy;
import hello.core.spring.member.Member;
import hello.core.spring.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 정리
 * 최근에는 생성자 1개를 두고, @Autowired 생략
 * Lombok 라이브러리의 @RequiredArgsConstructor 함께 사용
 */
@Component
@RequiredArgsConstructor // final이 붙은 필드를 파라미터로 받는 생성자를 자동으로 만들어준다. ( ctrl + f12 생성자 확인 할 수 있다.)
public class OrderServiceImpl implements OrderService {

    /**
     * 생성자 주입
     * 특징
     * 1. 생성자 호출시점에 딱 1번만 호출되는 것이 보장된다.
     * 2. "불변, 필수" 의존 관계에 사용
     *
     * 생성자가 1개가 있으면 자동으로 주입이 되기 때문에 @Autowired를 생략 가능하다.
     * 빈 등록 시, 생성자 주입도 같이 된다.
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
//    @Autowired  //생략 가능
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy){
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    /**
     * 필드 주입
     * 이름 그대로 필드에 바로 주입하는 방법
     * 특징
     * 1. 코드가 간결하지만 외부에서 변경이 불가능해서 테스트하기 힘들다
     * 2. DI 프레임워크가 없으면 아무것도 할 수 없다.
     * 사용 안하는 것이 좋음
     * - 애플리 케이션의 실제 코드와 관계없는 테스트 코드 사용가능
     * - 스프링 설정을 목적으로하는 @Configuration 같은 곳에서만 특별한 용도로 사용
     *
     */
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;

    /**
     * 수정자 주입(setter 주입)
     * 특징
     * 1. "선택,변경" 가능성이 있는 의존관계에 사용
     * 2. 자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법이다.
     */
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }
    /**
     * 일반 메서드 주입
     * 일반 메서드를 통해서 주입
     *  특징
     *  - 한번에 여러 필드를 주입 받을 수 있다.
     *  - 일반적으로 잘 사용 안함
     */
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//     @Autowired
//     public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
//         this.memberRepository = memberRepository;
//         this.discountPolicy = discountPolicy;
//     }


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
