package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


// 예외를 페이지경로와 짝지어주는 역할을 하는 클래스
// @Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        // ErrorPage - 오류 종류와 그 오류에 할당하는 오류페이지의 주소(파라미터)를 넣는다,
        // 짝을 지어주는 역할은 컨트롤러에서 담당한다.
        // 오류가 발생하면 컨트롤러에서 주소를 찾고, 그 주소에 맞는 메서드가 호출된다.

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        // RuntimeException 클래스의 자식 클래스의 오류까지 잡는다.
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}