package site.metacoding.dbproject.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.dbproject.domain.post.Post;
import site.metacoding.dbproject.domain.post.PostRepository;
import site.metacoding.dbproject.domain.user.User;

@RequiredArgsConstructor // final이 붙은 애들에 대한 생성자를 만들어준다.
@Controller
public class PostController {

    private final HttpSession session;
    private final PostRepository postRepository;

    // GET 글쓰기 페이지 /post/writeForm - 인증만 필요
    @GetMapping("/s/post/writeForm")
    public String writeForm() {

        if (session.getAttribute("principal") == null) {
            return "redirect:/loginForm";
        }

        return "post/writeForm";
    }

    // 메인페이지 - 인증 x
    // GET 글목록 페이지 /post/list, /
    // @GetMapping({"/", "/post/list"})
    // Get 요청만 그냥 통과시키소~ post, delete, update 말고
    @GetMapping({ "/", "/post/list" })
    public String list(@RequestParam(defaultValue = "0") Integer page, Model model) {
        // 1.postRepository의 findAll() 호출
        // 2. model에 담기
        // post 목록 desc 생성하기 findall sort 통해서 정렬해줌
        // model.addAttribute("posts",
        // postRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));

        PageRequest pq = PageRequest.of(page, 3);
        model.addAttribute("posts", postRepository.findAll(pq));
        model.addAttribute("prevPage", page - 1);
        model.addAttribute("nextPage", page + 1);

        return "post/list";
    }

    // GET 글상세보기 페이지 /post/{id} (삭제버튼 만들어 두면됨, 수정버튼 만들어 두면됨) - 인증 x
    @GetMapping("/post/{id}") // Get 요청에 /post만 제외 시키기
    public String detail(@PathVariable Integer id, Model model) {
        Optional<Post> postOp = postRepository.findById(id);

        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            model.addAttribute("post", postEntity);
            System.out.println("==============================================");
            return "post/detail";
        } else {
            return "error/page1";
        }

    }

    // GET 글수정 페이지 /post/{id}/updateForm - 인증 O
    // Get 요청이네?? 앞에 문지기한테 통과시키라고 했는데 인증이 필요하네?
    @GetMapping("/s/post/{id}/updateForm")
    public String updateForm(@PathVariable Integer id) {
        return "post/updateForm"; // ViewResolver 도움 받음.
    }

    // DELETE 글삭제 /post/{id} - 글목록으로 가기 -인증O
    @DeleteMapping("/s/post/{id}")
    public String delete(@PathVariable Integer id) {
        return "redirect:/";
    }

    // UPDATE 글수정 /post/{id} - 글상세보기 페이지가기 인증 O
    @PutMapping("/s/post/{id}")
    public String update(@PathVariable Integer id) {
        return "redirect:/post/" + id;
    }

    // POST 글쓰기 /post - 글목록으로 가기 - 인증 O
    @PostMapping("/s/post")

    // 오브젝트로 받음, 근데 user 오브젝트가 외래키로 참조되고 있기 떄문에 user 넣어줘야함
    public String write(Post post) {

        // title, content 1.null 검사, 2. 공백검사, 3.길이 검사 등등등

        // 인증체크
        if (session.getAttribute("principal") == null) {
            return "redirect:/loginForm";
        }
        User principal = (User) session.getAttribute("principal");
        post.setUser(principal);
        // INSERT INTO post(title, content, userId) values (사용자, 사용자, 세션오브젝트의 PK)

        postRepository.save(post);
        return "redirect:/";
    }
}