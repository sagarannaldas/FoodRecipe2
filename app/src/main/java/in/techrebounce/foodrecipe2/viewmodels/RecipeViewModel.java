package in.techrebounce.foodrecipe2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import in.techrebounce.foodrecipe2.models.Recipe;
import in.techrebounce.foodrecipe2.repositories.RecipeRepository;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;

    public RecipeViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe() {
        return mRecipeRepository.getRecipe();
    }

    public void searchRecipeById(String recipeId){
            mRecipeRepository.searchRecipeById(recipeId);
    }
}
