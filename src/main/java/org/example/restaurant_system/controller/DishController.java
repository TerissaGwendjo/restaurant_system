package org.example.restaurant_system.controller;

import org.example.restaurant_system.model.Dish;
import org.example.restaurant_system.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DishController {
    @Autowired
    private DishService service;

    @GetMapping ("/")
    public String showHomePage (Model model){
        model.addAttribute("dishes" , service.getAllDishes());
        return "home";
    }

    @GetMapping ("dishes/add")
    public String showAddDishForm (Model model) {
        //an empty data that will be filled with data afterwards
        //Dish dish = new Dish();
        model.addAttribute("dish" , new Dish());
        return "add-dish";
    }

    //Now we need a postMapping for after we add, and we click submit but same url
    @PostMapping ("/dishes/add")
    public String addDish(
            @ModelAttribute Dish dish) {
        service.saveDish(dish);
        return "redirect:/";
    }

    @GetMapping("/dishes/search")
    public String showSearchForm() {
        return "search-dish";
    }

    @GetMapping("/dishes/by-ingredient")
    public String showDishesByIngredient(@RequestParam ("ingredient") String ingredient, Model model) {
        List<Dish> dishes = service.getDishesByIngredient(ingredient);
        //System.out.println(dishes);
        model.addAttribute("dishes" , dishes);

        return "search-dish";

    }

    @GetMapping("/dishes/most-expensive")
    public String showMostExpensiveDish(Model model){

        model.addAttribute("dishes", service.getMostExpensiveDish());
        return "search-dish";

    }

    @GetMapping("/dishes/lowest-calories")
    public String showLowestCalories(Model model){

        model.addAttribute("dishes", service.getLowestCaloriesDish());
        return "search-dish";

    }

 /*   @GetMapping("/dishes/show")
    public String showAllDishes(Model model) {
        model.addAttribute("dishes" ,service.getAllDishes());
        return "show-dish";
    }*/

    //Adding edit features
    @GetMapping("/dishes/edit/{id}")
    public String showEditDishForm(@PathVariable Long id, Model model) {
    Dish dish = service.getDishById(id);
    if (dish == null) throw new RuntimeException("Dish not found!!");
    model.addAttribute("dish", dish);
    return "edit-dish";
    }

    @PostMapping ("/dishes/edit/{id}")
    public String updateDish(
            @PathVariable Long id,
            @RequestParam ("name") String name,
            @RequestParam ("price") Double price,
            @RequestParam ("calories") Integer calories,
            @RequestParam ("ingredients") String ingredients
    ){
        service.updateDish(id, name, price, calories, ingredients);
        return "redirect:/";
    }



}
