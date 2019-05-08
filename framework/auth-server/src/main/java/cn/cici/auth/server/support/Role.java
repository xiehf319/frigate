package cn.cici.auth.server.support;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @description:
 * @createDate:2019/5/6$10:55$
 * @author: Heyfan Xie
 */
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
