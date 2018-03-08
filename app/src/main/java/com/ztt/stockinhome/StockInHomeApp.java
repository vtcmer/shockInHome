package com.ztt.stockinhome;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;

import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.SugarRecord;
import com.orm.util.SugarConfig;
import com.ztt.stockinhome.category.di.CategoryComponent;
import com.ztt.stockinhome.category.di.CategoryModule;
import com.ztt.stockinhome.category.di.DaggerCategoryComponent;
import com.ztt.stockinhome.category.ui.CategoryView;
import com.ztt.stockinhome.category.ui.OnCategoryListener;
import com.ztt.stockinhome.commons.ui.adapters.SelectedAdapter;
import com.ztt.stockinhome.libs.di.LibsModule;
import com.ztt.stockinhome.main.di.DaggerMainMenuComponent;
import com.ztt.stockinhome.main.di.MainMenuComponent;
import com.ztt.stockinhome.main.di.MainMenuModule;
import com.ztt.stockinhome.main.ui.adapters.OnItemClickListenerMainMenu;
import com.ztt.stockinhome.products.di.ProductsModule;
import com.ztt.stockinhome.scan.di.DaggerScanComponent;
import com.ztt.stockinhome.scan.di.ScanComponent;
import com.ztt.stockinhome.scan.di.ScanModule;
import com.ztt.stockinhome.scan.ui.ScanActivity;
import com.ztt.stockinhome.shop.di.DaggerShoppingListComponent;
import com.ztt.stockinhome.shop.di.DaggerShoppingListDetailComponent;
import com.ztt.stockinhome.shop.di.ShopModule;
import com.ztt.stockinhome.shop.di.ShoppingListComponent;
import com.ztt.stockinhome.shop.di.ShoppingListDetailComponent;
import com.ztt.stockinhome.shop.di.ShoppingListDetailModule;
import com.ztt.stockinhome.shop.di.ShoppingListModule;
import com.ztt.stockinhome.shop.ui.OnShoppingListDetailListener;
import com.ztt.stockinhome.shop.ui.OnShoppingListListener;
import com.ztt.stockinhome.shop.ui.ShoppingListDetailView;
import com.ztt.stockinhome.shop.ui.ShoppingListView;
import com.ztt.stockinhome.stock.di.DaggerEditProductComponent;
import com.ztt.stockinhome.stock.di.DaggerStockComponent;
import com.ztt.stockinhome.stock.di.EditProductComponent;
import com.ztt.stockinhome.stock.di.EditProductModule;
import com.ztt.stockinhome.stock.di.StockComponent;
import com.ztt.stockinhome.stock.di.StockModule;
import com.ztt.stockinhome.stock.ui.EditProductView;
import com.ztt.stockinhome.stock.ui.OnStockListener;
import com.ztt.stockinhome.stock.ui.StockActivity;
import com.ztt.stockinhome.stock.ui.StockView;

/**
 * Created by vtcmer on 01/08/2016.
 */
public class StockInHomeApp extends Application {

    /**Módulo para la application*/
    private StockInHomeModule stockInHomeModule;


    @Override
    public void onCreate() {
        super.onCreate();
        this.initModules();
        SugarContext.init(getApplicationContext());
        //this.prepopulataTables();
    }

    private void prepopulataTables() {


        /*
        SugarRecord.executeQuery("DELETE FROM CATEGORY;");
        SugarRecord.executeQuery("insert into CATEGORY (NAME) values('Default');");
        SugarRecord.executeQuery("insert into CATEGORY (NAME) values('Congelado');");
        SugarRecord.executeQuery("insert into CATEGORY (NAME) values('Desayuno');");
        */
        SugarRecord.executeQuery("DELETE FROM SHOP;");
        SugarRecord.executeQuery("insert into SHOP (NAME,IMG_THUMB) values('Defecto',2130903041);");
        SugarRecord.executeQuery("insert into SHOP (NAME,IMG_THUMB) values('Mercadona',2130903046);");
        SugarRecord.executeQuery("insert into SHOP (NAME,IMG_THUMB) values('Gadis',2130903043);");
        SugarRecord.executeQuery("insert into SHOP (NAME,IMG_THUMB) values('Gadis',2130903043);");
        SugarRecord.executeQuery("insert into SHOP (NAME,IMG_THUMB) values('AlCampo',2130903040);");
        SugarRecord.executeQuery("insert into SHOP (NAME,IMG_THUMB) values('Lidl',2130903045);");


    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
        //this.DBTearDown();
    }

    /**
     * Inicialización de los módulo genereales de la aplicación
     */
    private void initModules() {
        this.stockInHomeModule = new StockInHomeModule(this);
    }

    /**
     * Inicialización de DBFlow
     */
    private void initDBFlow() {
       // FlowManager.init(this);
    }

    private void DBTearDown() {
        //FlowManager.destroy();
    }

    /**
     * Recuperación de componente de la pantalla principal
     * @param onItemClickListenerMainMenu
     * @return
     */
    public MainMenuComponent getMainMenuComponent(final OnItemClickListenerMainMenu onItemClickListenerMainMenu){
        return DaggerMainMenuComponent
                .builder()
                .mainMenuModule(new MainMenuModule(onItemClickListenerMainMenu))
                .build();
    }

    /**
     * Recuperación de componente de stock
     * @return
     */
    public StockComponent getStockComponent(final StockActivity context, final StockView view, final OnStockListener onStockListener){
        return DaggerStockComponent
                .builder()
                .productsModule(new ProductsModule())
                .libsModule(new LibsModule(context))
                .shoppingListModule( new ShoppingListModule())
                .stockModule(new StockModule(view, onStockListener))
                .shopModule(new ShopModule())
                .categoryModule(new CategoryModule())
                .build();
    }

    public ScanComponent getScanComponent(ScanActivity activity){
        return DaggerScanComponent
                .builder()
                .scanModule(new ScanModule(activity))
                .build();
    }


    public CategoryComponent getCategoryComponent(CategoryView view, OnCategoryListener onCategoryListener){
        return DaggerCategoryComponent
                .builder()
                .libsModule(new LibsModule())
                .categoryModule(new CategoryModule(view, onCategoryListener))
                .build();
    }

    public ShoppingListComponent getShoppingListComponent(ShoppingListView view, OnShoppingListListener listener){
        return DaggerShoppingListComponent
                .builder()
                .libsModule(new LibsModule())
                .shoppingListModule(new ShoppingListModule(view,listener))
                .build();
    }


    public ShoppingListDetailComponent getShoppingListDetailComponent(Activity context, ShoppingListDetailView view, OnShoppingListDetailListener listener){
        return DaggerShoppingListDetailComponent
                .builder()
                .libsModule(new LibsModule(context))
                .productsModule(new ProductsModule())
                .shoppingListDetailModule(new ShoppingListDetailModule(view,listener))
                .build();
    }


    public EditProductComponent getEditProductComponent(EditProductView view){
        return DaggerEditProductComponent
                .builder()
                .editProductModule(new EditProductModule(view))
                .libsModule(new LibsModule())
                .shopModule(new ShopModule())
                .categoryModule(new CategoryModule())
                .build();
    }



}
