package site.metacoding.dbproject.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // 사실 안 붙여도됨 , 부모 클래스에 정의되어 있음.
public interface UserRepository extends JpaRepository<User, Integer> {

    // 없는 것은 직접 만들기(복잡한 것)
    @Query(value = "SELECT * FROM user WHERE username  = :username AND password =:password", nativeQuery = true)
    User mLogin(@Param("username") String username, @Param("password") String password);

    // finaAll()
    // SELECT * FROM user;

    // findById()
    // SELECT * FROM user WHERE id =?
    // primary key가 붙은 id 를 알아서 인식함

    // save()
    // INSERT INTO user(username, passowrd, email, createDate) VALUES (?,?,?,?)

    // deleteById()
    // DELETE FROM user WHERE id =?

    // update는 없다!! - 영속성 컨텍스트 공부하면 사용할 수 있음!
}
