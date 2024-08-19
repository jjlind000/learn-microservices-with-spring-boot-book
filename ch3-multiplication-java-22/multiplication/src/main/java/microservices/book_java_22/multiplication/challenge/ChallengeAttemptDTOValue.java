package microservices.book_java_22.multiplication.challenge;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.Value;

/**
 * Attempt coming from the user
 */
//@Value
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeAttemptDTOValue
{
    @Min(1) @Max(99) int factorA;
    @Min(1) @Max(99) int factorB;
    @NotBlank String userAlias;
    @Positive(message = "How could you possibly get a negative result here? Try again.") int guess;
}