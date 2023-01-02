package by.godevelopment.domain.models;

import java.util.List;

public record Receipt(
    int id,
    List<String> lines
) { }
