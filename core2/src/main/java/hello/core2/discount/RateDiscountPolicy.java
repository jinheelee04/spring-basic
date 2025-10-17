package hello.core2.discount;

import hello.core2.member.Grade;
import hello.core2.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{

    private int discountRate = 10;

    @Override
    public int discount(Member member, int price) {

        return member.getGrade() == Grade.VIP ? price * discountRate / 100 : 0;
    }
}
