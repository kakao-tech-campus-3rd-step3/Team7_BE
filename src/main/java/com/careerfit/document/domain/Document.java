package com.careerfit.document.domain;

import com.careerfit.application.domain.Application;
import com.careerfit.global.entity.CreatedDateBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "document")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "document_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class Document extends CreatedDateBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Formula("document_type")
    private DocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    public void setApplication(Application application) {
        this.application = application;
    }

}
