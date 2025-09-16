package com.careerfit.comment.domain;

import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.document.domain.Document;
import com.careerfit.global.entity.TimeBaseEntity;
import com.careerfit.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class Comment extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(nullable = false)
    @Embedded
    private Coordinate coordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    public static Comment of(Document document, Member member, CommentCreateRequest request) {
        return Comment.builder()
            .content(request.content())
            .coordinate(request.coordinate())
            .member(member)
            .document(document)
            .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
