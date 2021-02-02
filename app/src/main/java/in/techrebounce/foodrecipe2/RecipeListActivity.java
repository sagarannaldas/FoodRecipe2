package in.techrebounce.foodrecipe2;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import in.techrebounce.foodrecipe2.adapters.OnRecipeListener;
import in.techrebounce.foodrecipe2.adapters.RecipeRecyclerAdapter;
import in.techrebounce.foodrecipe2.models.Recipe;
import in.techrebounce.foodrecipe2.util.Testing;
import in.techrebounce.foodrecipe2.util.VerticalSpacingItemDecorator;
import in.techrebounce.foodrecipe2.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mRecipeRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = findViewById(R.id.recipe_list);

        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();

        if(!mRecipeListViewModel.isViewingRecipes()){
            displaySearchCategories();
        }

    }

    private void subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null) {
                    Testing.printRecipes(recipes, TAG);
                    mRecipeRecyclerAdapter.setRecipes(recipes);
                }
            }
        });
    }

    private void initRecyclerView() {
        mRecipeRecyclerAdapter = new RecipeRecyclerAdapter(this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(30);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mRecyclerView.setAdapter(mRecipeRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mRecipeRecyclerAdapter.displayLoading();
                mRecipeListViewModel.searchRecipesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {
        Log.d(TAG, "onRecipeClick: clicked. " + position);
    }

    @Override
    public void onCategoryClick(String category) {
        mRecipeRecyclerAdapter.displayLoading();
        mRecipeListViewModel.searchRecipesApi(category, 1);
    }

    private void displaySearchCategories(){
        Log.d(TAG, "displaySearchCategories: called.");
        mRecipeListViewModel.setIsViewingRecipes(false);
        mRecipeRecyclerAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
/*    private void testRetrofitRequest() {
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeSearchResponse> responseCall = recipeApi.searchRecipe(
                Constant.API_KEY,
                "chicken breast",
                "1"
        );

        responseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                Log.d(TAG, "onResponse: server response " + response.toString());
                if(response.code() == 200) {
                    Log.d(TAG, "onResponse: server response " + response.body().toString());
                    List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
                    
                    for(Recipe recipe: recipes) {
                        Log.d(TAG, "onResponse: " + recipe.getTitle());
                    }
                } else {
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

            }
        });

    }*/

   /* private void testRetrofitRequest1() {
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeResponse> responseCall = recipeApi.getRecipe(
                Constant.API_KEY,
                "41470"
        );

        responseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.d(TAG, "onResponse: server response " + response.toString());
                if(response.code() == 200) {
                    Log.d(TAG, "onResponse: server response " + response.body().toString());
                    Recipe recipe = response.body().getRecipe();
                    Log.d(TAG, "onResponse: RETRIEVED RECIPE "+ recipe.toString());
                } else {
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

            }
        });

    }*/
