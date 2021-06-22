package com.rsdt.strukton;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;

import com.github.clans.fab.FloatingActionButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends AppCompatActivity {

    public static final String FRAGMENT_PREFERENCE = "FRAGMENT_PREFERENCE";

    private PreferenceFragment pFragment;

    private MaterialDialog iconDialog;

    private MaterialSimpleListAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        pFragment = (PreferenceFragment) getFragmentManager().findFragmentByTag(FRAGMENT_PREFERENCE);
        if(pFragment == null)
        {
            pFragment = new PreferenceFragment();
            transaction.add(R.id.container, pFragment, FRAGMENT_PREFERENCE);
        }
        transaction.show(pFragment);
        transaction.commit();

        FloatingActionButton button = (FloatingActionButton)findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconAdapter = new MaterialSimpleListAdapter(MainActivity.this);
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("1")
                        .icon(R.drawable.one)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("2")
                        .icon(R.drawable.two)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("3")
                        .icon(R.drawable.three)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("4")
                        .icon(R.drawable.four)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("5")
                        .icon(R.drawable.five)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("6")
                        .icon(R.drawable.six)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("7")
                        .icon(R.drawable.seven)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("8")
                        .icon(R.drawable.eight)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("9")
                        .icon(R.drawable.nine)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("10")
                        .icon(R.drawable.ten)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("11")
                        .icon(R.drawable.eleven)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("12")
                        .icon(R.drawable.twelve)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("13")
                        .icon(R.drawable.thirtheen)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("14")
                        .icon(R.drawable.fourtheen)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("15")
                        .icon(R.drawable.fiveteen)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("16")
                        .icon(R.drawable.sixteen)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("17")
                        .icon(R.drawable.seventeen)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("18")
                        .icon(R.drawable.eightteen)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("19")
                        .icon(R.drawable.nineteen)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("20")
                        .icon(R.drawable.twenty)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("21")
                        .icon(R.drawable.twentyone)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("22")
                        .icon(R.drawable.twentytwo)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("23")
                        .icon(R.drawable.twentythree)
                        .backgroundColor(Color.WHITE)
                        .build());
                iconAdapter.add(new MaterialSimpleListItem.Builder(MainActivity.this)
                        .content("24")
                        .icon(R.drawable.twentyfour)
                        .backgroundColor(Color.WHITE)
                        .build());

                iconDialog = new MaterialDialog.Builder(MainActivity.this)
                        .title("Choose a icon")
                        .adapter(iconAdapter, new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                MaterialSimpleListItem item = iconAdapter.getItem(which);
                                SharedPreferences.Editor editor = Strukton.getPrefences().edit();
                                String icon = item.getContent().toString();
                                editor.putString(TrainLocationService.PREFERENCE_ICON, icon);
                                editor.apply();

                                pFragment.setIcon(icon);

                                dialog.dismiss();
                            }
                        })
                        .autoDismiss(true)
                        .show();
            }
        });


        if(!TrainLocationServicePermissionHelper.isStarted)
        {
            if(TrainLocationServicePermissionHelper.canStart(this))
            {
                int code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
                if(code == ConnectionResult.SUCCESS) {
                    Intent intent = new Intent(this, TrainLocationService.class);
                    startService(intent);
                    TrainLocationServicePermissionHelper.isStarted = true;
                }
                else
                {
                    GooglePlayServicesUtil.getErrorDialog(code, this, 1).show();
                }
            }
        }
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {

        /**
         * Check if we can start the LocationService now.
         * */
        if(TrainLocationServicePermissionHelper.hasPermissionOfPermissionRequestResult(requestCode, permissions, grantResults))
        {
            int code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
            if(code == ConnectionResult.SUCCESS) {
                Intent intent = new Intent(this, TrainLocationService.class);
                startService(intent);
                TrainLocationServicePermissionHelper.isStarted = true;
            }
            else
            {
                GooglePlayServicesUtil.getErrorDialog(code, this, 1).show();
            }
        }
        else
        {
            /**
             * We still can't start it, because the user doesn't give the permission.
             * Show a status notification where is made clear that the user needs to give this permission.
             * */
            Snackbar.make(findViewById(R.id.container), "Locatie diensten zijn geweigerd", Snackbar.LENGTH_INDEFINITE).setAction("Oplossen", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TrainLocationServicePermissionHelper.canStart(MainActivity.this);
                }
            }).show();
        }
    }

}
