package br.com.restLivro.repository;

import br.com.restLivro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //metodo de condiçao que e usado no userService
    //findByUsername -- pode ser outro nome
    @Query("SELECT u FROM User u WHERE u.userName =:userName")
    User findByUsername (@Param("userName") String userName);
}
