package com.example.sofraapp.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.sofraapp.R;
import com.example.sofraapp.app.data.model.general.restaurantitems.Data2RestaurantItems;
import com.example.sofraapp.app.data.model.general.restaurants.Data2Restaurants;
import com.example.sofraapp.app.data.room.RoomDao;
import com.example.sofraapp.app.data.room.RoomManger;
import com.example.sofraapp.app.helper.DrawerLocker;
import com.example.sofraapp.app.helper.HelperMethod;
import com.example.sofraapp.app.helper.Model;
import com.example.sofraapp.app.helper.RememberMy;
import com.example.sofraapp.app.ui.fragment.client.order.CartOrdersFragment;
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
import com.example.sofraapp.app.ui.fragment.restaurant.restaurantCycle.RegisterAsRestaurantFragmentOne;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.example.sofraapp.app.helper.HelperMethod.GET_MODEL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {
    DrawerLayout drawer;
    public static Toolbar toolbar;
    RememberMy logout;
    private Boolean exitApp = false;
    ActionBarDrawerToggle toggle;
    Model model;
    private Fragment fragmentCurrent;
    private Data2Restaurants data2Restaurants;
    private List<Data2RestaurantItems> foodItems = new ArrayList<>();

    private RoomDao roomDao;
    private RoomManger roomManger;
    private OrderFoodFragment orderFoodFragment = new OrderFoodFragment();
    private OrdersAsRestaurantFragment ordersAsRestaurantFragment = new OrdersAsRestaurantFragment();
    private ProductMyFragment productMyFragment = new ProductMyFragment();
    private MyOrderAsUserFragment myOrderAsUSerFragment = new MyOrderAsUserFragment();
    private LoginFragment loginFragment = new LoginFragment();
    private ListNotificationFragment listNotificationFragment = new ListNotificationFragment();
    private MyOffersFragment myOffersFragment = new MyOffersFragment();
    private TaxsFragment taxsFragment = new TaxsFragment();
    private AboutAppFragment aboutAppFragment = new AboutAppFragment();
    private ContactUsFragment contactUsFragment = new ContactUsFragment();
    private OffersFragment offersFragment = new OffersFragment();
    private EditProfileUserFragment editProfileUserFragment = new EditProfileUserFragment();
    private RegisterAsRestaurantFragmentOne editProfileRestuarantFragment = new RegisterAsRestaurantFragmentOne();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        logout = new RememberMy(this);
        model = getIntent().getParcelableExtra(GET_MODEL);
        setSupportActionBar(toolbar);
        roomManger = RoomManger.getInstance(this);
        roomDao = roomManger.roomDao();

        //for redraw MenuItem again
        invalidateOptionsMenu();

        if (logout.getSaveState() == 1) {
            fragmentCurrent = orderFoodFragment;
            HelperMethod.replece(orderFoodFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.home));
        } else if (logout.getSaveState() == 2) {
            if (logout.getAPIKey() != null){
                fragmentCurrent = orderFoodFragment;
                HelperMethod.replece(orderFoodFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.home));

                // fragmentCurrent = productMyFragment;
             //   HelperMethod.replece(productMyFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.home));
            }else {
                HelperMethod.startActivity(getApplicationContext(),LoginActivity.class);
                   }
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
        ImageView IM_Image_User = (ImageView)headerView.findViewById(R.id.IM_Image_User);

        if (logout.getNameUser() != null) {
            textView_show_name.setText(logout.getNameUser());
            Glide.with(getApplicationContext())
                    .load(logout.getProfilePath()).placeholder(R.drawable.no_image)
                    .into(IM_Image_User);
        } else {
            textView_show_name.setText("");
            Glide.with(getApplicationContext())
                    .load(R.drawable.no_image)
                    .into(IM_Image_User);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logout.getNameUser() != null ) {
                    if (logout.getSaveState() == 1) {
                        fragmentCurrent = editProfileUserFragment;
                        HelperMethod.replece(editProfileUserFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.edit));
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        fragmentCurrent = editProfileRestuarantFragment;
                        HelperMethod.replece(editProfileRestuarantFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.edit));
                        drawer.closeDrawer(GravityCompat.START);
                    }
                } else {
                    HelperMethod.startActivity(getApplicationContext(),LoginActivity.class);
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
        }else if (!fragmentCurrent.equals(orderFoodFragment)) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            HelperMethod.startActivity(getApplicationContext(), MainActivity.class);
        }else if (fragments == 1) {
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (logout.getSaveState() == 2){
            menu.findItem(R.id.action_card).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_card) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {

                @Override
                public void run() {
                    CartOrdersFragment cartOrdersFragment = new CartOrdersFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("card", new Gson().toJson(roomDao.getAllItem()));
                    bundle.putString("dev", new Gson().toJson(data2Restaurants));
                    cartOrdersFragment.setArguments(bundle);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HelperMethod.replece(cartOrdersFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.cart));

                        }
                    });

                }
            });
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
                fragmentCurrent = orderFoodFragment;
                HelperMethod.replece(orderFoodFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.home));
                break;
            case R.id.my_orders:
                if (logout.getSaveState() == 1) {
                    fragmentCurrent = myOrderAsUSerFragment;
                    HelperMethod.replece(myOrderAsUSerFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.my_orders));
                } else {
                    fragmentCurrent = productMyFragment;
                    HelperMethod.replece(productMyFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.product_my));
                }
                break;
            case R.id.alarms:
                if (logout.getSaveState() == 1) {
                    fragmentCurrent = listNotificationFragment;
                    HelperMethod.replece(listNotificationFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.notification));
                } else {
                    fragmentCurrent = ordersAsRestaurantFragment;
                    HelperMethod.replece(ordersAsRestaurantFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.order_previe));
                }
                break;
            case R.id.new_offers:
                if (logout.getSaveState() == 1) {
                    fragmentCurrent = offersFragment;
                    HelperMethod.replece(offersFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.new_offers));
                } else {
                    fragmentCurrent = myOffersFragment;
                    HelperMethod.replece(myOffersFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.my_offer));

                }
                break;
            case R.id.tax:
                fragmentCurrent = taxsFragment;
                HelperMethod.replece(taxsFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.tax));

                break;
            case R.id.about_app:
                fragmentCurrent = aboutAppFragment;
                HelperMethod.replece(aboutAppFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.about_app));
                break;
            case R.id.tearms:
                break;
            case R.id.share_app:
                break;
            case R.id.connect_us:
                fragmentCurrent = contactUsFragment;
                HelperMethod.replece(contactUsFragment, getSupportFragmentManager(), R.id.Cycle_Home_contener, toolbar, getString(R.string.connect_us));
                break;
            default:
                // R.id.log_out
                if (logout.getAPIKey() != null) {
                    String token = FirebaseInstanceId.getInstance().getToken();
                    if (logout.getSaveState() == 1) {
                        HelperMethod.getRemoveToken(this, token, "android", logout.getAPIKey(),
                                1);
                    } else {
                        HelperMethod.getRemoveToken(this, token, "",logout.getAPIKey(),
                                 2);
                    }

                    logout.removeDateUser(this);
                    HelperMethod.startActivity(MainActivity.this, SplashActivity.class);
                } else {
                    HelperMethod.startActivity(getApplicationContext(),LoginActivity.class);

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
