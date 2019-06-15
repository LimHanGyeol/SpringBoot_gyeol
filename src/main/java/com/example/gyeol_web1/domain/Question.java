package com.example.gyeol_web1.domain;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question")   //answer 의 ManyToOne 변수명과 같게 한다.
    @OrderBy("id ASC")
    private List<Answer> answers;

    /*
     기본 String 형으로 글쓰기 같은 DB의 타입을 설정해주면 , 255자로 설정이 된다.
     Lob 로 하면 입력할 수 있는 글자의 수가 많아진다.
     */
    @Lob
    private String contents;

    public Question() {}

    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate() {
        if (createDate == null) {
            return "";
        }
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isSameWriter(User loginUser) {
        return this.writer.equals(loginUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
