package org.titansmora;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager mViewPager;
    public static final String url = "jdbc:mysql://166.62.27.168:3306/inhalodb?useUnicode=true&characterEncoding=utf-8";
    public static final String user= "ashen";
    public static final String pass= "ashen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accont);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        class processSignup extends AsyncTask<String,Void,String>
        {

            String status = null;
            ProgressDialog mProgress = new ProgressDialog(getActivity());
            @Override
            protected String doInBackground(String... params) {
                try {

                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url,user,pass);

                    Statement st = con.createStatement();
                    String  sql_query = "insert into Users values('"+null+"','"+params[2]+"', '"+params[1]+"', '"+params[3]+"', '"+params[4]+"','"+params[0]+"', '"+params[5]+"' )";

                    st.execute(sql_query);

                    status = "Success";


                } catch (ClassNotFoundException e) {
                    status = e.getMessage();

                } catch (SQLException e) {
                    status = e.getMessage();
                }


                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgress.setMessage("Please Wait...");
                mProgress.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mProgress.dismiss();
                try
                {

                  if (status.equals("Success"))
                    {
                        Toast.makeText(getActivity(),"Success... You can login now", Toast.LENGTH_SHORT).show();
                        mViewPager.setCurrentItem(0);
                    }
                    else
                  {
                      Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                  }
                }catch (Exception ex){}
            }
        }
        class processLogin extends AsyncTask<String, Void, String>{
            String id = null;
            String isAccountIsEmptyTold = null;
            String password = null;
            String authSuccess = null;
            ProgressDialog mProgress = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgress.setMessage("Please Wait...");
                mProgress.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    isAccountIsEmptyTold = null;
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url,user,pass);

                    Statement st = con.createStatement();
                    String  sql_query = "select id from Users where email = '"+params[0]+"'";
                    ResultSet rs = st.executeQuery(sql_query);
                    rs.next();
                    if (rs.getRow() == 0)

                    {
                        rs.close();
                        sql_query = "select id from Users where username = '"+params[0]+"'";
                        rs = st.executeQuery(sql_query);
                        rs.next();
                        if (rs.getRow() == 0)
                        {
                            isAccountIsEmptyTold = "IsAccountIsEmpty";
                        }else if (rs.getRow() > 0)
                        {
                            id = rs.getString(1);
                            isAccountIsEmptyTold = null;
                            sql_query = "select password from Users where username = '"+params[0]+"'";
                            rs = st.executeQuery(sql_query);
                            rs.next();
                            if (!params[1].equals(rs.getString(1)))
                            {
                                isAccountIsEmptyTold = "PasswordIncorrect";
                            }
                            else
                            {
                                authSuccess = "done";
                            }
                        }
                    }
                    else if (rs.getRow() > 0) {

                        id = rs.getString(1);
                        isAccountIsEmptyTold = null;
                        id = rs.getString(1);
                        isAccountIsEmptyTold = null;
                        sql_query = "select password from Users where email = '"+params[0]+"'";
                        rs = st.executeQuery(sql_query);
                        rs.next();
                        if (!params[1].equals(rs.getString(1)))
                        {
                            isAccountIsEmptyTold = "PasswordIncorrect";
                        }
                        else
                        {
                            authSuccess = "done";
                        }
                    }

                  //  sinhalaDif = rs.getString(1);

                } catch (ClassNotFoundException e) {


                } catch (SQLException e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mProgress.dismiss();
                try {
                    if (isAccountIsEmptyTold.equals("IsAccountIsEmpty")) {
                        final Dialog noAccountFoundDialog = new Dialog(getActivity());
                        noAccountFoundDialog.setContentView(R.layout.layout_no_account_found);
                        noAccountFoundDialog.show();
                        TextView btnDismissNoAccountFound = (TextView) noAccountFoundDialog.findViewById(R.id.btnDismissNoAccountFound);
                        btnDismissNoAccountFound.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                noAccountFoundDialog.dismiss();
                            }
                        });
                    }
                }catch (Exception e){}
                try {
                    if (authSuccess.equals("done"))
                    {
                        getActivity().getSharedPreferences("PREFERENCES",MODE_PRIVATE).edit().putString("uid",id).commit();
                        startActivity(new Intent(getActivity(),Splash.class));
                        getActivity().finish();
                    }
                }catch (Exception ex){}
                try
                {
                    if (isAccountIsEmptyTold.equals("PasswordIncorrect"))
                    {
                        final Dialog noAccountFoundDialog = new Dialog(getActivity());
                        noAccountFoundDialog.setContentView(R.layout.layout_no_account_found);
                        noAccountFoundDialog.show();
                        TextView btnDismissNoAccountFound = (TextView) noAccountFoundDialog.findViewById(R.id.btnDismissNoAccountFound);
                       TextView accountStatusTxtNoFound = (TextView) noAccountFoundDialog.findViewById(R.id.accountStatusTxtNoFound);
                        accountStatusTxtNoFound.setText("Password is Incorrect");
                        TextView accountStatusCommentTxtNoFound = (TextView) noAccountFoundDialog.findViewById(R.id.accountStatusCommentTxtNoFound);
                        accountStatusCommentTxtNoFound.setText("We couldn't recognize your password");
                        btnDismissNoAccountFound.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                noAccountFoundDialog.dismiss();
                            }
                        });
                    }
                }catch (Exception ex){}
            }
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
          if (getArguments().getInt(ARG_SECTION_NUMBER) == 1)
          {
              View rootView = inflater.inflate(R.layout.layout_signin,container,false);
              final EditText editEmailSignin = (EditText) rootView.findViewById(R.id.editEmailSignin);
              final EditText editPasswordSignin = (EditText) rootView.findViewById(R.id.editPasswordSignin);
              Button buttonLogin = (Button) rootView.findViewById(R.id.buttonLogin);
              buttonLogin.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      //get username & password to string
                      String username = editEmailSignin.getText().toString();
                      String password = editPasswordSignin.getText().toString();
                      //check text fields is empty or null
                      if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
                      {
                          Toast.makeText(getActivity(), "No empty fields allowed", Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          new processLogin().execute(username,password);
                      }

                  }
              });

              return rootView;
          }
          else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2)
          {
              View rootView = inflater.inflate(R.layout.layout_signup,container,false);
              final EditText editEmailSignup = (EditText) rootView.findViewById(R.id.editEmailSignup);
              final EditText editUsernameSignup = (EditText) rootView.findViewById(R.id.editUsernameSignup);
              final EditText editPasswordSignup = (EditText) rootView.findViewById(R.id.editPasswordSignup);
              final EditText editAgeSignup = (EditText) rootView.findViewById(R.id.editAgeSignup);
              final EditText editAddressSignup = (EditText) rootView.findViewById(R.id.editAddressSignup);
              final EditText editHeightSignup = (EditText)rootView.findViewById(R.id.editHeightSignup);
              Button buttonSignup = (Button) rootView.findViewById(R.id.buttonSignup);
              buttonSignup.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      //start signup
                      String email = editEmailSignup.getText().toString();
                      String password = editPasswordSignup.getText().toString();
                      String username = editUsernameSignup.getText().toString();
                      String age = editAgeSignup.getText().toString();
                      String address = editAddressSignup.getText().toString();
                      String height = editHeightSignup.getText().toString();

                      //check any text field empty
                      if  (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(age) || TextUtils.isEmpty(address) || TextUtils.isEmpty(height))
                      {
                          Toast.makeText(getActivity(), "No empt fields allowed", Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          //execute process for signup
                          new processSignup().execute(email,password,username,age,address,height);
                      }
                  }
              });

              return rootView;
          }
          return null;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SignIn";
                case 1:
                    return "SignUp";

            }
            return null;

        }
    }

}
