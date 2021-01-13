package com.example.pizzarecipe;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<PizzaRecipe> pizzaRecipes;

    public RecipeAdapter(List<PizzaRecipe> pizzaRecipes) {
        this.pizzaRecipes = pizzaRecipes;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView ingredients;
        TextView description;


        public RecipeViewHolder(@NonNull View itemView) {// передаем ссылки на виджеты
            super(itemView);

            this.image = itemView.findViewById(R.id.image);
            this.name = itemView.findViewById(R.id.name);
            this.ingredients = itemView.findViewById(R.id.ingredients);
            this.description = itemView.findViewById(R.id.description);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {// создаем вьюшку и создаем ViewHolder
        View view = View.inflate(parent.getContext(), R.layout.recycler_view_item, parent);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {// заполняем ViewHolder данными из List<PizzaRecipe> в int position
        PizzaRecipe pizzaRecipe = this.pizzaRecipes.get(position);

        holder.image.setImageResource(pizzaRecipe.getImage());
        holder.name.setText(pizzaRecipe.getName());
        holder.ingredients.setText(pizzaRecipe.getIngredients());
        holder.description.setText(pizzaRecipe.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.pizzaRecipes.size();
    }
}
