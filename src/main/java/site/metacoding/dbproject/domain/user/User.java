package site.metacoding.dbproject.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;

//JPA 라이브러리는 JAVA(자바언어로) Persistence(DB에 영구적인 저장) API(노출되어 있는 메서드)
//1.CRUD 메서드를 기본제공
//2.자바코드로 DB를 자동생성 기능제공
//3.ORM제공!! 
@AllArgsConstructor
@NoArgsConstructor
@Data // Getter, Setter, ToSTring
@Entity // 서버 실행시 해당 클래스로 테이블을 생성해
@EntityListeners(AuditingEntityListener.class) // 시간 알아서 해줌
public class User {
    // IDENTITY 전략은 DB에게 번호증가 전략을 위임하는 것!! - 알아서 디비에 맞게 찾아줌.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // primary key

    @Column(length = 20, unique = true)
    private String username; // ssar 아이디
    @Column(length = 12, nullable = false)
    private String password;
    @Column(length = 1600000)
    private String email;

    @CreatedDate // insert
    // 회원가입한 날짜
    private LocalDateTime creatDate;
    @LastModifiedDate // insert,update
    // 업데이트된 날짜
    private LocalDateTime updateDate;
}
