package hello.core.spring.discount;

import hello.core.spring.member.Grade;
import hello.core.spring.member.Member;
import org.springframework.stereotype.Component;

/**
 * FixDiscountPolicy와 RateDiscountPolicy를 둘다 빈 등록하면 아래 오류가 발생한다.
 * org.springframework.beans.factory.NoUniqueBeanDefinitionException:
 * No qualifying bean of type 'hello.core.spring.discount.DiscountPolicy' available: expected single matching bean but found 2: fixDiscountPolicy,rateDiscountPolicy
 */
@Component
public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000; // 1000원 할인

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return discountFixAmount;
        }else {
            return 0;
        }
    }
}
