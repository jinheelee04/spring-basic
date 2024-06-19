package hello.core.spring.discount;

import hello.core.spring.annotation.MainDiscountPolicy;
import hello.core.spring.member.Grade;
import hello.core.spring.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy")
//@Primary // Primary 어노테이션을 가진 클래스가 우선권을 가진다.
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price*discountPercent/100;
        }else{
            return 0;
        }

    }
}
