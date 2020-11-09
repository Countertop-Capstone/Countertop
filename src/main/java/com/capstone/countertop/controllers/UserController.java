package com.capstone.countertop.controllers;

import com.capstone.countertop.models.ApiRecipe;
import com.capstone.countertop.models.Favorite;
import com.capstone.countertop.models.Recipe;
import com.capstone.countertop.repositories.FavoriteRepository;
import com.capstone.countertop.repositories.RecipeRepository;
import com.capstone.countertop.repositories.UserRepository;
import com.capstone.countertop.services.Api;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.capstone.countertop.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
    private final RecipeRepository recipeDao;
    private final FavoriteRepository favoriteRepository;


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, RecipeRepository recipeDao, FavoriteRepository favoriteRepository) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.recipeDao = recipeDao;
        this.favoriteRepository = favoriteRepository;
    }


    //CHANGES

    @GetMapping("user/profile")
    public String showProfile(Model model, @PageableDefault(value=9) Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userDao.getOne(user.getId()));
        model.addAttribute("page", recipeDao.findAllByUser(user, pageable));
        return "users/newProfile";
    }




    @PostMapping("/user/update")
    public String updateUser(
            @RequestParam(name="id") long id,
            @RequestParam(name="email") String email,
            @RequestParam(name="username") String username,
            @RequestParam(name="password_old") String password_old,
            @RequestParam(name="password_new") String password_new,
            @RequestParam(name="date") String birth,
            @RequestParam(name="url") String url
    ) {
        User user = userDao.getOne(id);
        user.setUsername(username.toLowerCase());
        if (!password_old.isEmpty() && !password_new.isEmpty()) {
            if (passwordEncoder.encode(password_old).equals(user.getPassword()))
                user.setPassword(passwordEncoder.encode(password_new));
        }
        user.setEmail(email);
        user.setUrl(url);
        java.util.Date dob = java.sql.Date.valueOf(LocalDate.parse(birth));
        user.setDob(dob);

        userDao.save(user);

        return "redirect:/user/profile";
    }

    //CHANGES END

    @GetMapping("/users/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/users/favorites")
    public String showFavorites(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userDao.getOne(user.getId());

        List<Favorite> favorites = user.getUsersFavorites();
        List<Long> recipeIds = new ArrayList<>();
        List<Recipe> favoriteRecipes = new ArrayList<>();
        List<Long> apiRecipeIds = new ArrayList<>();
        List<ApiRecipe> favoriteApiRecipes = new ArrayList<>();

        for (Favorite favorite : favorites) {
            if (favorite.isApiRecipe()) {
                apiRecipeIds.add(favorite.getRecipeId());
            } else {
                recipeIds.add(favorite.getRecipeId());
            }
        }

        for(Long id : recipeIds) {
            favoriteRecipes.add(recipeDao.getOne(id));
        }

        model.addAttribute("favoriteRecipes", favoriteRecipes);
        try {
            model.addAttribute("favoriteApiRecipes", Api.getBulkRecipeInformation("https://api.spoonacular.com/recipes/informationBulk?ids=", apiRecipeIds));
        } catch(Error | InterruptedException | ParseException | IOException e) {
            e.printStackTrace();
        }

        return "/users/favorites";
    }

    @GetMapping("/about")
    public String aboutUs(){
        return "aboutUs";
    }

}
