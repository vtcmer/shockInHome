package com.ztt.stockinhome.main.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ztt.stockinhome.R;
import com.ztt.stockinhome.main.entities.MainMenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vtcmer on 01/08/2016.
 */
public class MainMenuListAdapter extends RecyclerView.Adapter<MainMenuListAdapter.ViewHolderMainMenuList> {


    private List<MainMenuItem> mainMenuItems;
    private OnItemClickListenerMainMenu onItemClickListenerMainMenu;

    public MainMenuListAdapter(List<MainMenuItem> mainMenuItems, OnItemClickListenerMainMenu onItemClickListenerMainMenu) {
        this.mainMenuItems = mainMenuItems;
        this.onItemClickListenerMainMenu = onItemClickListenerMainMenu;
    }

    @Override
    public ViewHolderMainMenuList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_menu_item, parent, false);
        return new ViewHolderMainMenuList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderMainMenuList holder, int position) {

        MainMenuItem item = this.mainMenuItems.get(position);

        holder.mainMenuItemText.setText(item.getTitle());
        holder.mainMenuItemImg.setImageResource(item.getIcon());

        holder.setOnItemClickListener(item, this.onItemClickListenerMainMenu);


    }

    @Override
    public int getItemCount() {
        return this.mainMenuItems.size();
    }

    /**
     * Carga los valores en la lista
     * @param mainMenuItems
     */
    public void setMainMenuItems(List<MainMenuItem> mainMenuItems) {
        this.mainMenuItems.clear();
        this.mainMenuItems.addAll(mainMenuItems);
        this.notifyDataSetChanged();
    }


    static class ViewHolderMainMenuList extends RecyclerView.ViewHolder {

        private View view;

        @Bind(R.id.mainMenuItemImg)
        ImageView mainMenuItemImg;
        @Bind(R.id.mainMenuItemText)
        TextView mainMenuItemText;

        public ViewHolderMainMenuList(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this,this.view);
        }

        public void setOnItemClickListener(final MainMenuItem mainMenuItem, final OnItemClickListenerMainMenu onItemClickListenerMainMenu) {
            mainMenuItemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListenerMainMenu.onItemClick(mainMenuItem);
                }
            });
        }
    }
}
