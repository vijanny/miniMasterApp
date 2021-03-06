package com.masterdroup.minimasterapp.module.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.masterdroup.minimasterapp.BasePresenter;
import com.masterdroup.minimasterapp.BaseView;
import com.masterdroup.minimasterapp.model.Base;
import com.masterdroup.minimasterapp.model.Menu;
import com.masterdroup.minimasterapp.model.Recipes;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by 11473 on 2016/12/21.
 */

public class Contract {


    interface Presenter extends BasePresenter {

        void gettingData(String menuId);

        void getRecipesListData();

        void initRV(PullLoadMoreRecyclerView rv);

        void initMenuViewRV(RecyclerView food_rv, RecyclerView step_rv, RecyclerView cooking_step_rv, RecyclerView like_rv);

        void like();//点赞

        boolean isLike();//是否like

        boolean isOwner();// 是否菜谱创建者
    }

    interface MenuCreatePresenter extends BasePresenter {

        //提交新创建的菜谱
        void submitNewMenu();


        void initStepRecyclerView(RecyclerView rv, RecyclerView rv_cooking, RecyclerView rv_food);


        //增加一个步骤
        void addStep();


        //增加一个烹饪步骤
        void addCookingStep();

        //增加一列食材
        void addFood();

        void setStepPicture(String url, int requestCode);

        void initDescribeStep();//
    }

    interface MenuAloneView extends BaseView<Presenter> {
        void settingData(Recipes.RecipesBean b);

        Context getContext();

        void onIsOwner(boolean o);

        void onIsLike(boolean o);
    }


    interface MenuListView extends BaseView<Presenter> {
        Context getContext();


    }

    interface MenuCreateView extends BaseView<MenuCreatePresenter> {


        Context getContext();

        String getMenuCoverLocalUrl();

        void setMenuCoverLocalUrl(String url);

        String getMenuCoverServerUrl();

        void setMenuCoverServerUrl(String url);

        String getMenuName();

        String getMenuDescribe();

    }


}
