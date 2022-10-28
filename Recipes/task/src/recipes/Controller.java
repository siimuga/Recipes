package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Resource
    RecipeService recipeService;


    @Resource
    UserService userService;


    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody UserRegisterInfo request) {
        userService.registerUser(request);
    }

    @GetMapping("/recipe/{id}")
    public RecipeResponse findRecipe(@PathVariable Integer id) {
        return recipeService.findRecipe(id);
    }

    @GetMapping("/recipe/search")
    public List<RecipeResponse> findRecipe(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        if (name == null && category != null)
            return recipeService.findRecipesByCategory(category);
        else if (category == null && name != null)
            return recipeService.findRecipesByName(name);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/recipe/new")
    public Counter addRecipe(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody RecipeInfo request) {
        return recipeService.addRecipe(details, request);
    }

    @PutMapping("/recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody RecipeInfo request, @PathVariable Integer id) {
        recipeService.updateRecipe(details, request, id);
    }

    @DeleteMapping("/recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable Integer id) {
        recipeService.deleteRecipe(details, id);
    }
}
