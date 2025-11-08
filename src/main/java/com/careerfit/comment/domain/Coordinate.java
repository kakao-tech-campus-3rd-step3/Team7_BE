package com.careerfit.comment.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Coordinate {

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;

}
