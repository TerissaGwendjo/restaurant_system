package org.example.restaurant_system.service;

import org.example.restaurant_system.model.Dish;
import org.example.restaurant_system.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    @Autowired
    private DishRepository repository;

    //Save a new dish
    public Dish saveDish (Dish dish) {
        return repository.save(dish);
    }

    // get the most expensive dish
    public Dish getMostExpensiveDish () {
        return repository.findTopByOrderByPriceDesc();
    }

    //get the lowest calories dish
    public Dish getLowestCaloriesDish (){
        return repository.findTopByOrderByCaloriesAsc();
    }

    //get a list of dishes based on a specific ingredient
    public List<Dish>getDishesByIngredient (String ingredient) {
        return repository.findByIngredientsContaining(ingredient);
    }
    public List<Dish> getAllDishes(){
        return repository.findAll();
    }

    public Dish getDishById (Long id) {
        return repository.getById(id);
    }

    @Transactional // means every database operation has to be accurate. either it works it it will rollback.
    // If the transaction is successful, everything will be committed to the database. If an error occurs or one of them fails, then there will be a rollback
    //it ensures integrity and consistency of our database. It ensures that any opereation carried out within the service layer is successful
    //so if one parameter is wrong, everything rolls back to ensure consistency and integrity as we learnt from ACID
    //We do this in the service layer cuz this is the point where we can handle errors. So we want to delegate the
    // responsibility of handling the interaction boundaries and roll back procedures to the spring framework. Also to maintain
    //clean architecture. That is allowing each layer with it's responsibility or a responsibility.
    public boolean updateDish(Long id,
                              String name,
                              Double price,
                              Integer calories,
                              String ingredients){
        int results = repository.updateDish(id, name, price, calories, ingredients);
        return results > 0; //Yu could still use void and return nothing
    }


    // Method to edit a dish
  /*  public Dish editDish(Long id, Dish dish) {
        Optional<Dish> existingDishOptional = repository.findById(id);
        if (existingDishOptional.isPresent()) {
            Dish existingDish = existingDishOptional.get();
            existingDish.setName(dish.getName());
            existingDish.setPrice(dish.getPrice());
            existingDish.setCalories(dish.getCalories());
            existingDish.setIngredients(dish.getIngredients());
            return repository.save(existingDish);
        } else {
            throw new DishNotFoundException("Dish with ID " + id + " not found");
        }
    }*/



}
