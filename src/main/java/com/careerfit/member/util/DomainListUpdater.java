package com.careerfit.member.util;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainListUpdater {

    public static <T> void updateList(
            List<T> existingList,
            List<T> newList,
            Function<T, String> keyExtractor,
            Consumer<T> addFunction) {

        Set<String> existingKeys = existingList.stream()
                .map(keyExtractor)
                .collect(Collectors.toSet());
        Set<String> incomingKeys = newList.stream()
                .map(keyExtractor)
                .collect(Collectors.toSet());

        existingList.removeIf(item -> !incomingKeys.contains(keyExtractor.apply(item)));

        newList.stream()
                .filter(item -> !existingKeys.contains(keyExtractor.apply(item)))
                .forEach(addFunction);
    }
}

