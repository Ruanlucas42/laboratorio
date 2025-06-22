package com.ruan.laboratorio.entity.users;

import org.springframework.beans.BeanUtils;

public class UserDTO {
    private Integer id;
    private  String login;

    public UserDTO(User user){
        BeanUtils.copyProperties(user, this);
    }

    public UserDTO(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
