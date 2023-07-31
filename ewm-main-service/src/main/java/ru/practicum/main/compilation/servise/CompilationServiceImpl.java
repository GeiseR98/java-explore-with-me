package ru.practicum.main.compilation.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.repository.CompilationRepository;
import ru.practicum.main.utility.Utility;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper mapper;
    private final Utility utility;


    @Override
    @Transactional
    public CompilationDto createCompilations(NewCompilationDto newCompilationDto) {
        return mapper.toDto(compilationRepository.save(mapper.toEntity(newCompilationDto)));
    }


}
