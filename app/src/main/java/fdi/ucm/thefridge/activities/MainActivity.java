package fdi.ucm.thefridge.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.fragments.IngredientesContentFragment;
import fdi.ucm.thefridge.fragments.OneclicContentFragment;
import fdi.ucm.thefridge.fragments.RecetasContentFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager=(ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneclicContentFragment(), "OneClic");
        adapter.addFragment(new RecetasContentFragment(), "Receta");
        adapter.addFragment(new IngredientesContentFragment(), "Nevera");
        viewPager.setAdapter(adapter);

        TabLayout tabs= (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        Toast.makeText(getBaseContext(), "Base de datos preparada", Toast.LENGTH_LONG).show();



        //DBHelper.Usuario usuario=dbHelper.get("usr2");
    }

    static class MyAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_registrar) {
            Context context = this.getApplicationContext();
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            //context.startActivity(intent);
            return true;
        }
        if (id == R.id.action_settings_login) {
            Context context = this.getApplicationContext();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
            //context.startActivity(intent);
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
