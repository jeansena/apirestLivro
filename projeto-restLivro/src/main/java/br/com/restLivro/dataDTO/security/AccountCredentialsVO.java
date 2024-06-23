package br.com.restLivro.dataDTO.security;

import java.util.Objects;

// AccountCredentialsVO -- Credenciais da conta VO ou DTOs
public class AccountCredentialsVO {

    private String username;
    private String password;

    //construtor vacio
    public AccountCredentialsVO() {
    }
    //construtor com argumento
    public AccountCredentialsVO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentialsVO that = (AccountCredentialsVO) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
