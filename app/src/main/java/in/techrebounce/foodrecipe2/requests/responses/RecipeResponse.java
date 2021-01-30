package in.techrebounce.foodrecipe2.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import in.techrebounce.foodrecipe2.models.Recipe;

public class RecipeResponse {

    @SerializedName("recipe")
    @Expose()
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}';
    }
}
