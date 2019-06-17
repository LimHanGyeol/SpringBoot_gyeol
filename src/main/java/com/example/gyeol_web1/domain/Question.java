package com.example.gyeol_web1.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String title;

    @JsonProperty
    private Integer countOfAnswer = 0;

    /*
     기본 String 형으로 글쓰기 같은 DB의 타입을 설정해주면 , 255자로 설정이 된다.
     Lob 로 하면 입력할 수 있는 글자의 수가 많아진다.
    */
    @Lob
    @JsonProperty
    private String contents;

    @OneToMany(mappedBy = "question")   //answer 의 ManyToOne 변수명과 같게 한다.
    @OrderBy("id DESC")
    private List<Answer> answers;


    public Question() {
    }

    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer() {
        this.countOfAnswer -= 1;
    }


}
