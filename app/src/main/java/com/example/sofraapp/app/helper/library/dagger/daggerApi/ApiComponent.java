package com.example.sofraapp.app.helper.library.dagger.daggerApi;

import com.example.sofraapp.app.data.interactor.LoginInteractor;
import com.example.sofraapp.app.helper.library.dagger.scope.ForApplication;
import com.example.sofraapp.app.ui.fragment.general.restaurant.OrderFoodFragment;

import dagger.Subcomponent;
@ForApplication
@Subcomponent(modules = ApiModule.class)
public interface ApiComponent {
    void inject(LoginInteractor loginInteractor);
    void inject(OrderFoodFragment orderFoodFragment);

}
