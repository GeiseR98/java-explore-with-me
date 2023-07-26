package ru.practicum.main.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.location.dto.LocationDto;
import ru.practicum.main.location.mapper.LocationMapper;
import ru.practicum.main.location.repository.LocationRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;
    private final LocationMapper mapper;

    @Override
    @Transactional
    public LocationDto save(LocationDto location) {
        return mapper.toDto(repository.save(mapper.toEntity(location)));
    }
}
