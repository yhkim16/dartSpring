package com.yhkim.hello.dto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name ="user_name")
    private String userName;
    @Column(name ="user_pwd")
    private String userPwd;
    @Column
    private LocalDate created_date;

    @PrePersist
    public void createdAt() {
        this.created_date = LocalDate.now();
    }

}
