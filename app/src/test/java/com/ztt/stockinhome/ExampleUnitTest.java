package com.ztt.stockinhome;

import com.orm.SugarApp;
import com.orm.SugarRecord;
import com.ztt.stockinhome.category.model.Category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
//@RunWith(RobolectricGradleTestRunner.class)
//@Config(constants = BuildConfig.class, sdk = 21,manifest="./src/main/AndroidManifest.xml")
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = StockInHomeApp.class, packageName = "com.orm.model", manifest = Config.NONE)
public class ExampleUnitTest extends SugarApp {

/*
    @Before
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());

    }

    @After
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
    */

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void saveCategory(){
        try {

            Category c1 = SugarRecord.findById(Category.class,1L);

            Category category = new Category();
            category.setName("Congelado3");
            Long id = SugarRecord.save(category);
            assertNotNull(id);
        }catch(Throwable thw){
            thw.printStackTrace();
        }
    }


}