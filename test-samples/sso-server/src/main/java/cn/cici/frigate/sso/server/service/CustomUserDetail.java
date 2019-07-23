package cn.cici.frigate.sso.server.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 18:18
 * @author: Heyfan Xie
 */
@Data
public class CustomUserDetail implements UserDetails {

    private long id;

    private String username;

    @JsonIgnore
    private String password;


    private List<GrantedAuthority> roleList;

    public CustomUserDetail(long id, String username, String password, List<GrantedAuthority> roleList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleList = roleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
