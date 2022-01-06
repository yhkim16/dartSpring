package com.yhkim.hello.dto;
import lombok.*;

import javax.persistence.*;



@Builder
@Data
@Entity
public class Company {
    @Id
    @Column(name ="corp_code")
    private int corp_code;
    @Column(name ="json", columnDefinition = "json")
    private String json;

    public Company(int corp_code, String json) {
        this.corp_code = corp_code;
        this.json = json;
    }

    public Company(String corp_code, String json) {
        this.corp_code = Integer.parseInt(corp_code);
        this.json = json;
    }

    public Company() {
    }
}
