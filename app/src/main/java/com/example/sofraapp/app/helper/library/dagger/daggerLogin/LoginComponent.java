package com.example.sofraapp.app.helper.library.dagger.daggerLogin;
import com.example.sofraapp.app.data.model.client.cycleClient.registerclinet.Register;
import com.example.sofraapp.app.helper.library.dagger.scope.ApiScope;
import com.example.sofraapp.app.ui.fragment.client.userCycle.LoginFragment;

import dagger.Subcomponent;
@ApiScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginFragment loginFragment);
    void inject(Register register);

}
