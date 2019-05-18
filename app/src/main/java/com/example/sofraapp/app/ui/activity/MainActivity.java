package com.example.sofraapp.app.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofraapp.R;
import com.example.sofraapp.app.helper.DrawerLocker;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.helper.SaveData;
import com.example.sofraapp.app.ui.fragment.client.order.MyOrderAsUserFragment;
import com.example.sofraapp.app.ui.fragment.client.userCycle.EditProfileUserFragment;
import com.example.sofraapp.app.ui.fragment.client.userCycle.LoginFragment;
import com.example.sofraapp.app.ui.fragment.client.userCycle.notifications.ListNotificationFragment;
import com.example.sofraapp.app.ui.fragment.general.offers.OffersFragment;
import com.example.sofraapp.app.ui.fragment.general.restaurant.OrderFoodFragment;
import com.example.sofraapp.app.ui.fragment.mainCycle.AboutAppFragment;
import com.example.sofraapp.app.ui.fragment.mainCycle.ContactUsFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.TaxsFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.foodItem.ProductMyFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.offers.MyOffersFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.orders.OrdersAsRestaurantFragment;
import com.example.sofraapp.app.ui.fragment.restaurant.restaurantCycle.EditProfileRestuarantFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import static com.example.sofraapp.app.helper.HelperMethod.GET_DATA;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {
    DrawerLayout drawer;
    public static Toolbar toolbar;
    RememberMy logout;
    private Boolean exitApp = false;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        logout = new RememberMy(this);
        setSupportActionBar(toolbar);
        if (logout.getSaveState() == 1) {
            OrderFoodFragment orderFoodFragment = new OrderFoodFragment();
            HelperMethod.replece(orderFoodFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.home));
        } else if (logout.getSaveState() == 2) {
            ProductMyFragment productMyFragment = new ProductMyFragment();
            HelperMethod.replece(productMyFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.home));
        } else {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //for perform Action in Ic_Settings
        View headerView = navigationView.getHeaderView(0);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.IM_Ic_Settings);
        TextView textView_show_name = (TextView) headerView.findViewById(R.id.TV_Navigation_Bar_Name_User);
        if (logout.getNameUser() != null) {
            textView_show_name.setText(logout.getNameUser());
        } else {
            textView_show_name.setText("");
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logout.getNameUser() != null) {
                    if (logout.getSaveState() == 1) {
                        EditProfileUserFragment editProfileUserFragment = new EditProfileUserFragment();
                        HelperMethod.replece(editProfileUserFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.edit));
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        EditProfileRestuarantFragment editProfileRestuarantFragment = new EditProfileRestuarantFragment();
                        HelperMethod.replece(editProfileRestuarantFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.edit));
                        drawer.closeDrawer(GravityCompat.START);
                    }
                } else {
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replece(loginFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.login));
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        // for change tittle in NavigationView 1- get menu from navigationView
        Menu menu = navigationView.getMenu();
        MenuItem nav_logout = menu.findItem(R.id.log_out);
        if (logout.getAPIKey() != null) {
            nav_logout.setTitle(R.string.log_out);
        } else {
            nav_logout.setTitle(R.string.login);
        }
        if (logout.getSaveState() == 1) {
            //2- find MenuItem you want to change it
            MenuItem nav_myorder = menu.findItem(R.id.my_orders);
            MenuItem nav_alarm = menu.findItem(R.id.alarms);
            MenuItem nav_offer = menu.findItem(R.id.new_offers);
            MenuItem nav_tax = menu.findItem(R.id.tax);
            //3- set new title to the MenuItem
            nav_myorder.setTitle(getString(R.string.my_orders));
            nav_alarm.setTitle(getString(R.string.alarms));
            nav_offer.setTitle(getString(R.string.new_offers));
            nav_tax.setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        setDraweEnabled(true);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragments == 1) {
            if (exitApp) {
                HelperMethod.closeApp(getApplicationContext());
                return;
            }
            exitApp = true;
            Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitApp = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.home_page:
                OrderFoodFragment orderFoodFragment = new OrderFoodFragment();
                HelperMethod.replece(orderFoodFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.home));
                break;
            case R.id.my_orders:
                if (logout.getSaveState() == 1) {
                    MyOrderAsUserFragment myOrderAsUSerFragment = new MyOrderAsUserFragment();
                    HelperMethod.replece(myOrderAsUSerFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.my_orders));
                } else {
                    ProductMyFragment productMyFragment = new ProductMyFragment();
                    HelperMethod.replece(productMyFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.product_my));
                }
                break;
            case R.id.alarms:
                if (logout.getSaveState() == 1) {
                    ListNotificationFragment listNotificationFragment = new ListNotificationFragment();
                    HelperMethod.replece(listNotificationFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.notification));
                } else {
                    OrdersAsRestaurantFragment ordersAsRestaurantFragment = new OrdersAsRestaurantFragment();
                    HelperMethod.replece(ordersAsRestaurantFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.order_previe));
                }
                break;
            case R.id.new_offers:
                if (logout.getSaveState() == 1) {
                    OffersFragment offersFragment = new OffersFragment();
                    HelperMethod.replece(offersFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.new_offers));
                } else {
                    MyOffersFragment myOffersFragment = new MyOffersFragment();
                    HelperMethod.replece(myOffersFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.my_offer));

                }
                break;
            case R.id.tax:
                TaxsFragment taxsFragment = new TaxsFragment();
                HelperMethod.replece(taxsFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.tax));

                break;
            case R.id.about_app:
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                HelperMethod.replece(aboutAppFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.about_app));
                break;
            case R.id.tearms:
                break;
            case R.id.share_app:
                break;
            case R.id.connect_us:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                HelperMethod.replece(contactUsFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.connect_us));
                break;
            default:
                // R.id.log_out
                if (logout.getAPIKey() != null) {
                    logout.removeDateUser(this);
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replece(loginFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.login));
                } else {
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replece(loginFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.login));
                    drawer.closeDrawer(GravityCompat.START);
                }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setDraweEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }
}
