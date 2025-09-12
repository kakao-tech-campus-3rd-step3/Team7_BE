package com.careerfit.comment.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Coordinate {

    private int startX;
    private int startY;
    private int endX;
    private int endY;

}
