package org.example.restaurant_system.repository;

import org.example.restaurant_system.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DishRepository extends JpaRepository <Dish , Long> {

    // create method to retrieve the most expensive dish
    //@Query("SELECT d FROM Dish d ORDER BY d.price DESC LIMIT 1")
    @Query(value = "SELECT * FROM dishes ORDER BY price DESC LIMIT 1", nativeQuery = true)
    Dish findTopByOrderByPriceDesc();

    //create method to retrieve a dish with the minimum calories
    @Query(value = "SELECT * FROM dishes ORDER BY calories ASC LIMIT 1", nativeQuery = true)
    Dish findTopByOrderByCaloriesAsc();

    //develop a method to list dishes based on a specific ingredient
    @Query(value = "SELECT d FROM Dish d WHERE d.ingredients LIKE %:ingredients%")
    List<Dish> findByIngredientsContaining(@Param("ingredients") String ingredient); //A string contains a string

    //A method to show all dishes feature
    /*@Query(value = "SELECT * FROM dishes" , nativeQuery = true)
    Dish findAllDishes();*/

    @Modifying
    @Query("UPDATE Dish d SET d.name =:name , d.price =:price , d.calories =:calories, d.ingredients =:ingredients WHERE d.id =:id ")
    int updateDish(@Param("id") Long id,
                   @Param("name") String name,
                   @Param("price") Double price,
                   @Param("calories") Integer calories,
                   @Param("ingredients") String ingredients);

}
