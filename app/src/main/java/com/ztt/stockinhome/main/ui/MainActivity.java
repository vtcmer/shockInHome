package com.ztt.stockinhome.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orm.SugarRecord;
import com.ztt.stockinhome.R;
import com.ztt.stockinhome.StockInHomeApp;
import com.ztt.stockinhome.category.model.Category;
import com.ztt.stockinhome.category.ui.CategoryActivity;
import com.ztt.stockinhome.main.EnumMainMenuActions;
import com.ztt.stockinhome.main.di.MainMenuComponent;
import com.ztt.stockinhome.main.entities.MainMenuItem;
import com.ztt.stockinhome.main.ui.adapters.MainMenuListAdapter;
import com.ztt.stockinhome.main.ui.adapters.OnItemClickListenerMainMenu;
import com.ztt.stockinhome.shop.ui.ShoppingListActivity;
import com.ztt.stockinhome.stock.ui.StockActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnItemClickListenerMainMenu {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private MainMenuListAdapter adapter;

    private StockInHomeApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.app = (StockInHomeApp) getApplication();

        this.setupInjection();
        this.setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.adapter.setMainMenuItems(this.renderMenuItems());
    }

    private void setupInjection() {

        MainMenuComponent mainMenuComponent = this.app.getMainMenuComponent(this);
        this.adapter = mainMenuComponent.getAdapter();

    }

    private void setupRecyclerView() {

        this.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        this.recyclerView.setAdapter(this.adapter);

    }


    private List<MainMenuItem> renderMenuItems() {

        List<MainMenuItem> mainMenuItemList = new ArrayList<MainMenuItem>();

        MainMenuItem product = new MainMenuItem();
        product.setTitle(this.getResources().getString(R.string.product_title));
        product.setIcon(R.drawable.shopping_cart_icon);
        product.setAction(EnumMainMenuActions.SHOW_PRODUCTS);
        mainMenuItemList.add(product);



        MainMenuItem categoriesProduct = new MainMenuItem();
        categoriesProduct.setTitle(this.getResources().getString(R.string.categories));
        categoriesProduct.setIcon(R.drawable.ic_shop_category);
        categoriesProduct.setAction(EnumMainMenuActions.CATEGORIES);
        mainMenuItemList.add(categoriesProduct);

        MainMenuItem shoppingList = new MainMenuItem();
        shoppingList.setTitle(this.getResources().getString(R.string.shoppinglist));
        shoppingList.setIcon(R.mipmap.ic_shopping_list);
        shoppingList.setAction(EnumMainMenuActions.SHOPPING_LIST);
        mainMenuItemList.add(shoppingList);

        return mainMenuItemList;

    }

    @Override
    public void onItemClick(MainMenuItem mainMenuItem) {

        if (mainMenuItem.getAction().equals(EnumMainMenuActions.SHOW_PRODUCTS)){
            Intent intent = new Intent(this, StockActivity.class);
            this.startActivity(intent);
        } else if (mainMenuItem.getAction().equals(EnumMainMenuActions.CATEGORIES)){
            Intent intent = new Intent(this, CategoryActivity.class);
            this.startActivity(intent);
        } else if (mainMenuItem.getAction().equals(EnumMainMenuActions.SHOPPING_LIST)){
            Intent intent = new Intent(this, ShoppingListActivity.class);
            this.startActivity(intent);
        }

    }
}
