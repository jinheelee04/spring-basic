package hello.core.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @ComponentScan 
 * @Component 어노테이션이 붙은 클래스를 찾아서 자동으로 스프링 빈 등록을 해준다.
 * 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
 *  빈 이름 기본 전략: MemberServiceImpl 클래스 -> memberServiceImpl
 *  빈 이름 직접 지정: @Component('memberService)
 *
 * 참고: @Configuration도 자동으로 스프링 빈에 등록된다.
 *
 * @Autowired 를 사용하여 의존관계를 자동 주입해준다.
 */
@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Configuration.class})  // 스캔 대상 제외
)
public class AutoAppConfig {

}
