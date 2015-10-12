package com.xint78.qianyou;
/*
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
*/

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager_main;
    private RadioGroup radioGroup_main;
    private List<RadioButton> list;
    private List<Fragment> listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initTab();
        initData();
        initListener();
    }

    private void initView() {
        // TODO Auto-generated method stub
        viewPager_main = (ViewPager) findViewById(R.id.viewPager_main);
        radioGroup_main = (RadioGroup) findViewById(R.id.radioGroup_main);
    }

    private void initTab() {
        // TODO Auto-generated method stub
        list = new ArrayList<RadioButton>();
        String[] tabArr = getResources().getStringArray(R.array.tabName);
        int len = tabArr.length;
        for (int i = 0; i < len; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(tabArr[i]);
            radioButton.setTextSize(18);
            radioButton.setButtonDrawable(null);
            radioButton.setPadding(20, 30, 20, 30);
            radioButton.setTextColor(Color.rgb(255, 255, 255));
            radioButton.setGravity(Gravity.CENTER);
            //radioButton.setButtonDrawable(R.drawable.radiobutton_selector);
            //radioButton.setBackgroundResource(R.drawable.partition);
            //radioButton.setBackground(R.drawable.radiobutton_selector);
            radioButton.setBackgroundResource(R.drawable.radiobutton_selector);
            //radioButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            int length = getResources().getDisplayMetrics().widthPixels;
            int eachLength = length / len;
            radioButton.setWidth(eachLength);
            radioGroup_main.addView(radioButton);
            list.add(radioButton);
        }
    }

    private void initData() {
        // TODO Auto-generated method stub
        listFragment = new ArrayList<Fragment>();;
        int length = list.size();
        for (int i = 0; i < length; i++) {
            GameListFragment frag = GameListFragment.newInstance(i);
            listFragment.add(frag);
        }
        FragmentManager man = getSupportFragmentManager();
        viewPager_main.setAdapter(new MyViewPager(man, listFragment));
    }

    private void initListener() {
        // TODO Auto-generated method stub
        radioGroup_main
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // TODO Auto-generated method stub
                        for (int i = 0; i < radioGroup_main.getChildCount(); i++) {
                            if (list.get(i).isChecked()) {
                                viewPager_main.setCurrentItem(i);
                            }
                        }
                    }
                });
        viewPager_main.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                list.get(arg0).setChecked(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        list.get(0).setChecked(true);
    }

    class MyViewPager extends FragmentPagerAdapter {
        private List<Fragment> list;

        public MyViewPager(FragmentManager fm, List<Fragment> list) {
            super(fm);
            // TODO Auto-generated constructor stub
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

    }
}
