package br.com.restLivro.Service;

import br.com.restLivro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices  implements UserDetailsService {
    //informação de log
    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired
    UserRepository repository;

    //construtor da classe
    public UserServices (UserRepository repository){
        this.repository= repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //info de log
        logger.info("Encontrar um usuário pelo nome" + username );

        var user = repository.findByUsername(username); //buscar o nome

        //verificar se o nome e nullo
        //se for nullo ou diferente vai rola uma exeção
        if (user !=null){
            return user;
        }
        else {
            throw new UsernameNotFoundException("Username " + username + "não encontrado");
        }

    }
}
