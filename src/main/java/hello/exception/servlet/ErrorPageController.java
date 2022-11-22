package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {

    // 오류 정보를 상수로 지정
    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri"; // 클라이언트 요청 URI
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type"; // 예외 타입
    public static final String ERROR_MESSAGE = "javax.servlet.error.message"; // 오류 메세지
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception"; // 예외
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name"; // 오류가 발생한 서블릿 이름
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code"; // HTTP 상태 코드




    // 이름 - errorPage404
    // 매개변수 - request, response
    // 동작 - 로그정보와 printErrorInfo 메서드를 호출한다.
    // 반환값 - 주소값 반환
    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {

        log.info("errorPage 404");
        printErrorInfo(request);

        return "error-page/404";
    }

    // 이름 - errorPage500
    // 매개변수 -  request, response
    // 동작 - 로그정보와 printErrorInfo 메서드를 호출한다.
    // 반환값 - 주소값 반환
    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {

        log.info("errorPage 500");
        printErrorInfo(request);

        return "error-page/500";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(HttpServletRequest request, HttpServletResponse response) {

        log.info("API errorPage 500");

        Map<String, Object> result = new HashMap<>();

        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);

        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        return new ResponseEntity(result, HttpStatus.valueOf(statusCode));
    }


    private void printErrorInfo(HttpServletRequest request) {


        // 위에서 상수로 정리한 오류 정보들을 찍음

        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));

        // 클라이언트로 부터 발생한 정상 요청인지, 아니면 오류 페이지를 출력하기 위한 내부 요청인지 구분하기 위해서 사용
        // 클라이언트가 처음 요청하면 dispatcherType=REQUEST 이다.
        log.info("dispatchType={}", request.getDispatcherType());

    }



}