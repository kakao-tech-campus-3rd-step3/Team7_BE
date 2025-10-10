package com.careerfit.comment.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;

}
