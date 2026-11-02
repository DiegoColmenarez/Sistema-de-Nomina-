package infrastructure.persistence;

import application.model.EmployeeRecord;
import application.repository.EmployeeRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<UUID, EmployeeRecord> storage = new LinkedHashMap<>();

    public InMemoryEmployeeRepository(List<EmployeeRecord> employees) {
        employees.forEach(employee -> storage.put(employee.id(), employee));
    }

    @Override
    public Optional<EmployeeRecord> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<EmployeeRecord> findAll() {
        return List.copyOf(storage.values());
    }
}