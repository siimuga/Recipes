package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecipeService {

    @Resource
    private RecipeRepository recipeRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private RecipeMapper recipeMapper;


    public RecipeResponse findRecipe(Integer id) {
            Optional<Recipe> byId = recipeRepository.findById(id);
            if (byId.isPresent()) {
                RecipeResponse recipeResponse = new RecipeResponse();
                recipeResponse.setName(byId.get().getName());
                recipeResponse.setDescription(byId.get().getDescription());
                recipeResponse.setIngredients(byId.get().getIngredients());
                recipeResponse.setDirections(byId.get().getDirections());
                recipeResponse.setCategory(byId.get().getCategory());
                recipeResponse.setDate(byId.get().getDate());
                return recipeResponse;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
    }

    public Counter addRecipe(UserDetails details, RecipeInfo request) {
        User user = userRepository.findByEmail(details.getUsername());
        Recipe recipe = new Recipe();
        recipe.setName(request.getName());
        recipe.setCategory(request.getCategory());
        recipe.setDescription(request.getDescription());
        recipe.setIngredients(request.getIngredients());
        recipe.setDirections(request.getDirections());
        recipe.setDate(Instant.now());
        recipe.setUser(user);
        Recipe savedRecipe = recipeRepository.save(recipe);
        Counter counter = new Counter();
        counter.setId(savedRecipe.getId());
        return counter;
    }

    public void deleteRecipe(UserDetails details, Integer id) {
        Optional<Recipe> byId = recipeRepository.findById(id);
        if (byId.isPresent()) {
            if (Objects.equals(byId.get().getUser().getEmail(), details.getUsername())) {
                recipeRepository.delete(byId.get());
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void updateRecipe(UserDetails details, RecipeInfo request, Integer id) {
        Optional<Recipe> byId = recipeRepository.findById(id);
        if (byId.isPresent()) {
            if (Objects.equals(byId.get().getUser().getEmail(), details.getUsername())) {
                byId.get().setName(request.getName());
                byId.get().setCategory(request.getCategory());
                byId.get().setDescription(request.getDescription());
                byId.get().setIngredients(request.getIngredients());
                byId.get().setDirections(request.getDirections());
                byId.get().setDate(Instant.now());
                recipeRepository.save(byId.get());
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<RecipeResponse> findRecipesByCategory(String category) {
        List<Recipe> entities = recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
        return recipeMapper.recipesToRecipeResponses(entities);
    }

    public List<RecipeResponse> findRecipesByName(String name) {
        List<Recipe> entities = recipeRepository.findAllByNameContainsIgnoreCaseOrderByDateDesc(name);
        return recipeMapper.recipesToRecipeResponses(entities);
    }
}
