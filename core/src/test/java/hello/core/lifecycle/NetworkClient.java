package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 스프링 빈의 이벤트 라이프사이클
 * 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
 *
 * 초기화 콜백 : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
 * 소멸전 콜백 : 빈이 소멸되기 직전에 호출
 *
 * 참고: 객체의 생성과 초기화를 분리하자
 * 생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다.
 * 초기화는 이렇게 생성된 값들을 활용해서 외부 컨넥션을 연결하는 등 무거운 동작을 수행한다.
 * 따라서 객체를 생성하는 부분과 초기화 하는 부분을 명확하게 나눈는 것이 유지보수 관점에서 좋다.
 *
 *  InitializingBean, DisposableBean 초기화, 소멸 인터페이스 단점
 *  - 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
 *  - 초기화, 소멸 메소드의 이름을 변경할 수 없다.
 *  - 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
 */
public class NetworkClient implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect: " + url);
    }

    public void call(String message){
        System.out.println("call: " + url+" message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect(){
        System.out.println("close: " + url);
    }

    /**
     * 의존관계 주입이 끝나면 호출
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    /**
     * 빈 종료시 호출
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
