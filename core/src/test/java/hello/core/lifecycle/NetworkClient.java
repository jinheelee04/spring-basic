package hello.core.lifecycle;

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
 */
public class NetworkClient {

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
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
}
