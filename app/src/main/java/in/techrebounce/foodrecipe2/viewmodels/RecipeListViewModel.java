package in.techrebounce.foodrecipe2.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.techrebounce.foodrecipe2.models.Recipe;
import in.techrebounce.foodrecipe2.repositories.RecipeRepository;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;

    public RecipeListViewModel() {
        mRecipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }


}
