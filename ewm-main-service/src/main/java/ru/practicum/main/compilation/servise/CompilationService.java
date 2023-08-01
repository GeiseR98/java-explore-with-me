package ru.practicum.main.compilation.servise;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;

public interface CompilationService {
    CompilationDto createCompilations(NewCompilationDto newCompilationDto);

    void deleteCompilations(Integer compId);

    CompilationDto changeCompilations(Integer compId, UpdateCompilationRequest updateCompilationRequest);
}
