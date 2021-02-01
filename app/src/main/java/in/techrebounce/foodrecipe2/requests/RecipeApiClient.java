package in.techrebounce.foodrecipe2.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import in.techrebounce.foodrecipe2.AppExecutors;
import in.techrebounce.foodrecipe2.models.Recipe;
import in.techrebounce.foodrecipe2.requests.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.Response;

import static in.techrebounce.foodrecipe2.util.Constant.NETWORK_TIME_OUT;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;

    public static RecipeApiClient getInstance() {
        if(instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    public RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipes;
    }

    public void searchRecipesApi(String query, int pageNumber) {
        if(mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable = null;
        }
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query,pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveRecipesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIME_OUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query,pageNumber).execute();
                if(cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<Recipe> recipes = new ArrayList<>(((RecipeSearchResponse)response.body()).getRecipes());
                    if(pageNumber == 1){
                        mRecipes.postValue(recipes);
                    } else {
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(recipes);
                        mRecipes.postValue(currentRecipes);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: Error" + error );
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
            return  ServiceGenerator.getRecipeApi().searchRecipe(
                    "",
                    query,
                    String.valueOf(pageNumber)
            );
        }
        
        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling request");
            cancelRequest = true;
        }
    }
}
