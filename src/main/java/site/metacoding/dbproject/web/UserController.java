package site.metacoding.dbproject.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.user.User;
import site.metacoding.dbproject.domain.user.UserRepository;

@RequiredArgsConstructor // final 로 돼있는 모든 생성자를 만들어줌
@Controller
public class UserController {

    // 컴포지션(의존성 연결)
    private UserRepository userRepository;
    private HttpSession session;

    // pirvate HttpservletRequest request 전역변수로 사용하면 응답이 안됐는데 요청이 덮어질 우려가 있다!!
    // DI받는 코드!!
    public UserController(UserRepository userRepository, HttpSession session) {
        this.userRepository = userRepository;
        this.session = session;
    }

    // 회원가입 페이지 (정적) - 로그인X
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // username=ssar&password=1234&email=ssar@nate.com (x-www-form)
    // 회원가입 - 로그인X
    @PostMapping("/join")
    // insert
    public String join(User user) {
        System.out.println("user:" + user);
        User userEntity = userRepository.save(user);
        System.out.println("userEntity:" + userEntity);
        // redirect : 매핑장소
        return "redirect:/loginForm"; // 로그인페이지 이동해주는 컨트롤러 메서드를 재활용
    }

    // 로그인 페이지 (정적) - 로그인X
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // SELECT * FROM user WHERE username=? AND password=?
    // 원래 SELECT 는 무조건 get요청
    // 그런데 로그인만 예외 (POST)
    // 이유 : 주소에 패스워드를 남길 수 없으니까!!
    // 로그인 - - 로그인X
    @PostMapping("/login")
    public String login(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(); // 세션영역에 접근할 수 있게 해줌. 동적인
        // 세션(sessionId:85)

        User userEntity = userRepository.mLogin(user.getUsername(), user.getPassword());

        if (userEntity == null) {
            System.out.println("아이디 혹은 패스워드가 틀렸습니다");
        } else {
            System.out.println("로그인 되었습니다.");
            session.setAttribute("prinicipal", userEntity);
        } // key 값은 prinicipal 인증 주체

        // 1.DB연결해서 username, password 있는지 확인
        // 2. 있으면 session 영역에 인증됨 이라고 메세지 하나 넣어 두자.
        return "redirect:/"; // PostController 만들고 수정하자.
    }

    // 유저상세 페이지 (동적) - 로그인O
    @GetMapping("/user/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        User principal = (User) session.getAttribute("principal");

        // 1. 인증 체크
        if (principal == null) {
            return "error/page1";
        }

        // 2. 권한체크
        if (principal.getId() != id) {
            return "error/page1";
        }

        // 3. 핵심로직
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            model.addAttribute("user", userEntity);
            return "user/detail";
        } else {
            return "error/page1";
        }
    }
    // DB에 로그 남기기(로그인 한 아이디)

    // http://localhost:8080/user/1
    // 유저수정 페이지 (동적) - 로그인O
    @GetMapping("/user/{id}/updateForm")
    public String updateForm() {

        return "user/updateForm";
    }

    // 유저수정 - 로그인O
    @PutMapping("/user/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:/user/" + id;
    }

    // 로그아웃 - 로그인O
    @GetMapping("/logout")
    public String logout() {
        return "메인페이지를 돌려주면 됨"; // PostController 만들고 수정하자.
    }
}