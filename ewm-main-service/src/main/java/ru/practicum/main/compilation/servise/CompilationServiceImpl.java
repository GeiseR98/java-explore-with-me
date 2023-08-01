package ru.practicum.main.compilation.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.compilation.repository.CompilationRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.utility.Utility;

import java.util.List;

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

    @Override
    @Transactional
    public void deleteCompilations(Integer compId) {
        compilationRepository.deleteById(utility.checkCompilation(compId).getId());
    }

    @Override
    @Transactional
    public CompilationDto changeCompilations(Integer compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation oldCompilations = utility.checkCompilation(compId);

        if (updateCompilationRequest.getEvents() != null && !updateCompilationRequest.getEvents().isEmpty()) {
            List<Event> eventList = utility.checkEvents(updateCompilationRequest.getEvents());
            oldCompilations.setEvents(eventList);
        }
        if (updateCompilationRequest.getPinned() != null) {
            oldCompilations.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null && !updateCompilationRequest.getTitle().isEmpty()) {
            oldCompilations.setTitle(updateCompilationRequest.getTitle());
        }
        log.debug("Подборка обновлена");
        return mapper.toDto(compilationRepository.save(oldCompilations));
    }
}
