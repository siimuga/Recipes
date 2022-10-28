package recipes;


import org.mapstruct.*;

import java.time.Instant;
import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RecipeMapper {

    RecipeResponse recipeToRecipeResponse(Recipe recipe);
    List<RecipeResponse> recipesToRecipeResponses(List<Recipe> recipes);
}
