package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@RestController
public class ApiExceptionController {


    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {


        // 매핑주소에서 id 부분에 ex 로 온다면 RuntimeException 발생시키고 메시지로 "잘못된 사용자" 를 띄운다.
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        // 매핑주소에서 id 부분에 bad 로 온다면 IllegalArgumentException 발생시키고 메시지로 "잘못된 입력값" 를 띄운다.
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력값");
        }

        // 매핑주소에서 id 부분에 user-ex 로 온다면 UserException 발생시키고 메시지로 "사용자 오류" 를 띄운다.
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }


    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }


}
