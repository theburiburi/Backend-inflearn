package com.group.libraryapp.domain.user;


import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20, name = "name")
    private String name;
    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();
    protected User() {}; //아무것도 없는 기본 생성자가 꼭필요하다
    public User(String name, Integer age) {
        if(name.isBlank() || name == null){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)가 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void loanBook(String bookName){
        this.userLoanHistories.add(new UserLoanHistory(this,bookName));
    }

    public void returnBook(String bookName){
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName)) //stream = 함수형 프로그래밍 가능
                .findFirst().orElseThrow(IllegalArgumentException::new); //findFirst = Optional 형태
        targetHistory.DoReturn();
    }
}
