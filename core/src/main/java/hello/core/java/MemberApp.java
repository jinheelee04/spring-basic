package hello.core.java;

import hello.core.java.member.Grade;
import hello.core.java.member.Member;
import hello.core.java.member.MemberService;

public class MemberApp {

    public static void main(String[] args){
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
//        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = "+member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
