package com.careerfit.comment.domain;

import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.document.domain.Document;
import com.careerfit.global.entity.TimeBaseEntity;
import com.careerfit.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private Integer page;

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
            .page(request.page())
            .member(member)
            .document(document)
            .build();
    }

    public void updateContent(String content, Integer page) {
        if (content != null) {
            this.content = content;
        }
        if (page != null) {
            this.page = page;
        }
    }
}
