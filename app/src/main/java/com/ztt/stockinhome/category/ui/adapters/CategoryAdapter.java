package com.ztt.stockinhome.category.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.category.ui.OnCategoryListener;
import com.ztt.stockinhome.commons.ui.adapters.SelectedAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vtcmer on 04/09/2016.
 */
public class CategoryAdapter extends SelectedAdapter<CategoryAdapter.ViewHolderCategory, Category> {

    /**
     * Listado de categorias
     */
    private List<Category> categories;
    private OnCategoryListener onCategoryListener;

    private Animation flipGrowAnimation;

    public CategoryAdapter(List<Category> categories, OnCategoryListener onCategoryListener) {
        this.categories = categories;
        this.onCategoryListener = onCategoryListener;
    }

    /**
     * Seteo de listado de categorías
     * @param categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    /**
     * Añade una categoría
     * @param category
     */
    public void addCategory(final Category category) {
        this.categories.add(category);
        notifyDataSetChanged();
    }

    /**
     * Añade las categorías a una posición
     * @param position
     * @param category
     */
    public void addCategory(final int position, final Category category) {
        if (position > categories.size()){
            categories.add(category);
        } else {
            this.categories.add(position, category);
        }
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }


    /**
     * Actualización de un categoría
     * @param category
     */
    public void updateCategory(final Category category) {
        int position = this.categories.indexOf(category);
        this.categories.set(position, category);
        this.notifyItemChanged(position);
    }

    /**
     * Eliminación de una categoria
     * @param category
     */
    public void deleteCategory(final Category category){
        int position = this.categories.indexOf(category);
        this.categories.remove(position);
        this.notifyItemRemoved(position);
    }

    public void deleteCategories(final List<Category> categories){
        this.categories.removeAll(categories);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolderCategory onCreateViewHolder(ViewGroup parent, int viewType) {
        flipGrowAnimation = AnimationUtils.loadAnimation(parent.getContext(),R.anim.flip_grow);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolderCategory(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderCategory holder, int position) {

        Category category = this.categories.get(position);
        boolean isItemsSelected = isSelected(position);
        holder.categoryName.setText(category.getName());
        holder.setOnCategoryListener(position, category, this.onCategoryListener, isItemsSelected);
        holder.view.setSelected(isItemsSelected);
        if (isItemsSelected){
            //holder.btnEditCategory.setVisibility(View.VISIBLE);
            holder.btnEditCategory.startAnimation(this.flipGrowAnimation);
            holder.btnEditCategory.setImageResource(R.drawable.ic_check_circle_black_24dp);

        } else {
            holder.btnEditCategory.startAnimation(this.flipGrowAnimation);
            //holder.btnEditCategory.setVisibility(View.GONE);
            holder.btnEditCategory.setImageResource(R.drawable.ic_edit_black_24dp);
        }


    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    @Override
    public Category getItem(int position) {
        return this.getItem(position);
    }

    static class ViewHolderCategory extends RecyclerView.ViewHolder {

        @Bind(R.id.categoryName)
        TextView categoryName;
        @Bind(R.id.btnEditCategory)
        ImageView btnEditCategory;

        @Bind(R.id.containerCategoyItem)
        RelativeLayout containerCategoyItem;
        private View view;



        public ViewHolderCategory(View itemView) {


            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);


        }

        public void setOnCategoryListener(final int position, final Category category, final OnCategoryListener onCategoryListener, final boolean isItemsSelected) {

            /*
            btnEditCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isItemsSelected) {
                        onCategoryListener.onEditCategory(category);
                    }
                }
            });
            */

            containerCategoyItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isItemsSelected) {
                        onCategoryListener.onEditCategory(category);
                    }
                }
            });

            containerCategoyItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onCategoryListener.onSelectItem(position, category);
                    return true;
                }
            });


        }
    }
}
