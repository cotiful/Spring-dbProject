package site.metacoding.dbproject.domain.post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.metacoding.dbproject.domain.user.User;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // primary key

    @Column(length = 300, nullable = false)
    private String title;

    @Lob // CLOB 4GB 문자 타입
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;
}
