package base;

import lombok.NonNull;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A repository for storing your top-level class's instances.
 *
 * @param <T> the type of your top-level class
 */
public abstract class Repository<T> {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final List<T> entities;

    /**
     * A constructor for initializing the repository.
     *
     * @param clazz the type of your top-level class
     * @throws IOException if an I/O error happens
     */
    protected Repository(
            @NonNull final Class<T> clazz) throws IOException {

        this.entities = MAPPER.readValue(
                Repository.class.getResourceAsStream("/data.json"),
                MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz)
        );
    }

    /**
     * {@return the list of your top-level class's instances}
     */
    public final List<T> getAll() {
        return Collections.unmodifiableList(this.entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return entities.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}