package com.careerfit.global.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PagedResponse<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
) {

    /**
     * Page<Entity> 객체와 DTO 변환 함수를 받아 PagedResponse<DTO>를 생성하는 정적 팩토리 메서드
     *
     * @param page         Entity를 담고 있는 Page 객체
     * @param dtoConverter Entity를 DTO로 변환하는 Function
     * @param <E>          Entity 타입
     * @param <D>          DTO 타입
     * @return PagedResponse<DTO>
     */
    public static <E, D> PagedResponse<D> of(Page<E> page, Function<E, D> dtoConverter) {
        List<D> content = page.getContent().stream()
            .map(dtoConverter)
            .toList();

        return new PagedResponse<>(
            content,
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast()
        );
    }
}
