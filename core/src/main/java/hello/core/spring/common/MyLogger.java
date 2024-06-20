package hello.core.spring.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 웹 스코프 @Scope(value = "request")
 * - HTTP 요청 당 하나씩 생성되고, HTTP요청이 끝나는 시점에 소멸된다.
 *
 * 프록시 방식(proxyMode = ScopedProxyMode.TARGET_CLASS)
 * - 적용 대상이 클래스이면 TARGET_CLASS를 선택
 * - 적용 대상이 인터페이스면 INTERFACES 선택
 *
 * CGLIB라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 * - 스프링 컨테이너는 CGLIB라는 바이트 코드를 조작하는 라이브러리를 사용해서 MyLogger를 상속받는 가짜 프록시 객체를 생성한다.
 * - 우리가 등록한 순수한 MyLogger 클래스가 아니라 MyLogger$$SpringCGLIB 이라는 클래스로 만들어진 객체가 대신 등록된 것을 확인 할 수 있다.
 * - 그래서 의존 관계 주입도 이 가짜 프록시 객체가 주입된다.
 *
 * 가짜 프록시 객체는 요청이 오면 그때 내부에서 진짜 빈을 요청하는 위임 로직이 들어있다.
 * - 이 가짜 프록시 객체는 내부에 진짜 myLogger를 찾는 방법을 알고 있다.
 * - 클라이언트가 myLogger.logic() 을 호출하면 사실은 가짜 프록시 객체의 메서드를 호출한 것이다.
 * - 가짜 프록시 객체는 request스코프의 진짜 myLogger.logic()를 호출한다.
 * - 가짜 프록시 객체는 원본 클래스를 상속 받아서 만들어졌기 때문에 이 객체를 사용하는 클라이언트 입장에서는
 *   사실 원본인지 아닌지도 모르게, 동일하게 사용할 수 있다.(다형성)
 *
 *   특징
 *   - 프록시 객체 덕분에 클라이언트는 마치 싱글톤 빈을 사용하듯이 편리하게 request scope를 사용할 수 있다.
 *   - 사실 Provider를 사용하든, 프록시를 사용하든 핵심 아이디어는 진짜 객체 조회를 꼭 필요한 시점까지 지연처리 한다는 점이다.
 *   - 단지 애노테이션 설정 변경만으로 원본 객체를 프록시 객체로 대체할 수 있다. 이것이 바로 다형성과 DI컨테이너가 가진 큰 강점이다.
 *   - 웹 스코프가 아니어도 프록시는 사용할 수 있다.
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "["+ requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:"+ this);

    }

    @PreDestroy
    public void close(){
        System.out.println("[" + uuid + "] request scope bean close:"+ this);
    }
}
