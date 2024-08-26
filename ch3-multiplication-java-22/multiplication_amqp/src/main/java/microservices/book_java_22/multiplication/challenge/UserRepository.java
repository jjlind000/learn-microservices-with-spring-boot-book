package microservices.book_java_22.multiplication.challenge;

import microservices.book_java_22.multiplication.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Note:
 * You don’t need to implement these interfaces.
 * You don’t even need to add the Spring’s @Repository annotation. Spring, using the Data module,
 * will find interfaces extending the base ones and will inject beans that implement the desired behavior.
 * That also involves processing the method names and creating the corresponding JPA queries.
 */
public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByAlias(final String alias);
    List<User> findAllByIdIn(final List<Long> ids);
}