package in.techrebounce.foodrecipe2.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Recipe implements Parcelable {

    private String title;
    private String recipe_id;
    private String publisher;
    private String[] ingredients;
    private String image_url;
    private float social_rank;

    public Recipe(String title, String recipe_id, String publisher, String[] ingredients, String image_url, float social_rank) {
        this.title = title;
        this.recipe_id = recipe_id;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.image_url = image_url;
        this.social_rank = social_rank;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        recipe_id = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArray();
        image_url = in.readString();
        social_rank = in.readFloat();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", recipe_id='" + recipe_id + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", image_url='" + image_url + '\'' +
                ", social_rank=" + social_rank +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(recipe_id);
        dest.writeString(publisher);
        dest.writeStringArray(ingredients);
        dest.writeString(image_url);
        dest.writeFloat(social_rank);
    }
}

