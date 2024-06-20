package hello.core.spring.web;

import hello.core.spring.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    /**
     * Error creating bean with name 'myLogger': Scope 'request' is not active for the current thread
     * 스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만,
     * request 스코프 빈은 아직 생성되지 않는다. 이 빈은 실제 고객의 요청(HTTP request)이 와야 생성할 수 있다.
     * 따라서 LogDemoController가 생성되는 시점에 request가 없어서 발생하는 오류이다.
     */
    private final MyLogger myLogger;

    /**
     * ObjectProvider.getObject()를 LogDemoController나 LogDemoService에서 따로 호출하더라도
     * 같은 HTTP 요청이면 같은 스프링 빈이 반환된다.
     */
//    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
        System.out.println("myLogger = " + myLogger.getClass()); // myLogger = class hello.core.spring.common.MyLogger$$SpringCGLIB$$0

        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }


}
