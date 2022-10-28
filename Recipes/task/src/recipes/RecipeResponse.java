package recipes;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Data
public class RecipeResponse {
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private Instant date;
    @NotBlank
    private String description;
    @NotNull
    @Size(min = 1)
    private List<String> ingredients;
    @NotNull
    @Size(min = 1)
    private List<String> directions;
}