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
 *
 * 권장방법
 * 패키지 위치를 지정하지 않고, 
 * 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것
 *
 * 스프링부트는  @SpringBootApplication에 @ComponentScan을 포함하고 있어서 별도로 설정할 필요가 없다.
 *
 * 컴포넌트 스캔 기본 대상
 * @Component:
 * @Controller: 스프링 MVC컨트롤러로 인식
 * @Service: 특별한 처리 하지 않는다. 대신 개발자들이 핵심 비즈니스 계층을 인식하는데 도움을 준다.
 * @Repository: 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 반환해준다.
 * @Configuration: 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리 한다.
 */
@Configuration
@ComponentScan(
        basePackages = {"hello.core.spring.member"}, // 컴포넌트 대상 패키지, 기본 설정: @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작위치(ex: hello.core.spring)
//        basePackageClasses = AutoAppConfig.class, // 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Configuration.class})  // 스캔 대상 제외
)
public class AutoAppConfig {

}
