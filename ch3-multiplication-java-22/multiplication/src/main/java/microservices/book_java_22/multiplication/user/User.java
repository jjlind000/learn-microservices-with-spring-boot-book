package microservices.book_java_22.multiplication.user;

import lombok.*;
import jakarta.persistence.*;

/**
 * Stores information to identify the user.
 */
@Entity
@Table(name = "USERS")
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String alias;

    public User(final String userAlias)
    {
        this(null, userAlias);
    }
}