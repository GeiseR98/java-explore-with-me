package ru.practicum.main.compilation.servise;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;

public interface CompilationService {
    CompilationDto createCompilations(NewCompilationDto newCompilationDto);
}
