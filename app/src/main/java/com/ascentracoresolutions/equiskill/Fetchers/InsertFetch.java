package com.ascentracoresolutions.equiskill.Fetchers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ascentracoresolutions.equiskill.Adapters.InternAdapter;
import com.ascentracoresolutions.equiskill.Adapters.SkillAdapter;
import com.ascentracoresolutions.equiskill.Adapters.StudentAdapter;
import com.ascentracoresolutions.equiskill.Admin.AdminActivity;
import com.ascentracoresolutions.equiskill.Getters.Interns;
import com.ascentracoresolutions.equiskill.Getters.Skill;
import com.ascentracoresolutions.equiskill.Getters.Student;
import com.ascentracoresolutions.equiskill.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InsertFetch {

    public final String TAG = "AllFetcher";
    public static final String USER_ID_SIGN = "user_id_sign";

    private StudentAdapter studentAdapter;
    private ArrayList<Student> list;
    private Context context;
    private Button button;
    private TextView error;
    private Class<?> activity;
    private ConstraintLayout main;

    private LinearLayout mainLayout;
    SharedPreferences sharedPreferences;
    private InternAdapter internAdapter;
    private ArrayList<Interns> intern_list;

    private String data;

    private ArrayList<String> skillList;
    private ArrayList<Skill> sk_l;
    private SkillAdapter skillAdapter;

    public InsertFetch(String data, ArrayList<String> skillList) {
        this.data = data;
        this.skillList = skillList;
    }
    public InsertFetch(StudentAdapter studentAdapter, ArrayList<Student> list) {

        this.studentAdapter = studentAdapter;
        this.list = list;
    }


    public InsertFetch(SkillAdapter skillAdapter, ArrayList<Skill> sk_l) {
        this.skillAdapter = skillAdapter;
        this.sk_l = sk_l;
    }

    public  InsertFetch(InternAdapter internAdapter, ArrayList<Interns> intern_list) {
        this.internAdapter = internAdapter;
        this.intern_list = intern_list;
    }

    // Internship upload constructor
    public InsertFetch(Context context, TextView error, Button button, LinearLayout main) {
        this.context = context;
        this.error = error;
        this.button = button;
        mainLayout = main;
    }

    public InsertFetch(String data) {
        this.data = data;
    }

    //sign in  fetcher
    public InsertFetch(Context context, TextView error, Class<?> activity, Button button, ConstraintLayout main) {
        this.context = context;
        this.error = error;
        this.activity = activity;
        this.button = button;
        this.main = main;

    }

    public void fetchData(String url_link) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            String result = fetchDataFromUrl(url_link);

            new Handler(Looper.getMainLooper()).post(() -> onDataFetched(result));
        });

        executorService.shutdown();
    }
    @NonNull
    private String fetchDataFromUrl(String url_link) {

        StringBuilder result =  new StringBuilder();

        try {
            URL url = new URL(url_link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;

                while((line = br.readLine()) != null) {
                    result.append(line);
                }
            } finally {
                httpURLConnection.disconnect();
            }
        } catch(Exception e) {

            Log.e(TAG, "error: " + e.getMessage());
        }

        return result.toString();
    }


    private void onDataFetched(String result) {

        if(result != null) {
            try {

                JSONObject jsonObject = new JSONObject(result);

                Log.e(TAG, "onDataFetched: " + jsonObject);


                if (skillAdapter != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("skills");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject skill = jsonArray.getJSONObject(i);

                        String name = skill.getString("name");
                        sk_l.add(new Skill(name, skill.getInt("level")));
                    }

                    skillAdapter.notifyDataSetChanged();

                    return;

                }


                if (internAdapter != null) {

                        JSONArray jsonArray = jsonObject.getJSONArray("internships");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject internship = jsonArray.getJSONObject(i);

                            String title = internship.getString("title");
                            String company_name = internship.getString("company_name");
                            String description = internship.getString("description");


                            intern_list.add(new Interns(title, company_name, description, internship.getString("internship_id"),  internship.getInt("score")));
                        }

                        internAdapter.notifyDataSetChanged();

                        return;


                }


                // sign in system
                if (jsonObject.has("signin") && activity != null && button != null && main != null) {
                    sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);

                    String signIn = jsonObject.getString("signin");

                    if (jsonObject.has("user_type")){

                        String user_type =  jsonObject.getString("user_type");



                        sharedPreferences.edit().putString(MainActivity.USER_TYPE, user_type).apply();

                        sharedPreferences.edit().putString(MainActivity.USER_ID, jsonObject.getString("signin")).apply();
                        if (user_type.equals("user")) {

                            context.startActivity(new Intent(context, activity));
                        } else {

                            context.startActivity(new Intent(context, AdminActivity.class));
                        }

                    }

                    if (signIn.equals("exists")) {

                        if (error != null) {
                            error.setText("Already user exists");
                            error.setVisibility(TextView.VISIBLE);
                        }
                    } else if (signIn.equals("no")) {
                        if (error != null) {
                            error.setText("No User Found");
                            error.setVisibility(TextView.VISIBLE);
                        }
                    } else {
                        if (jsonObject.has("otp")) {

                            sharedPreferences.edit().putString("otp", jsonObject.getString("otp")).apply();
                            sharedPreferences.edit().putString(USER_ID_SIGN, jsonObject.getString("signin")).apply();

                            context.startActivity(new Intent(context, activity));

                            Log.e(TAG, "executed");
                        } else {

                            context.startActivity(new Intent(context, activity));
                        }


                    }


                    button.setEnabled(true);
                    main.setAlpha(1);


                    Log.e(TAG, "onDataFetched: sign in block");
                    return;
                }
                // sign in end system

                // Internship Upload Block
                if (jsonObject.has("message") && !jsonObject.has("students") && activity == null) {

                    String message = jsonObject.getString("message");

                    if (message.equals("success")) {

                        if (error != null) {
                            error.setText("Internship Created Successfully!");
                            error.setVisibility(TextView.VISIBLE);
                        }

                    } else {

                        if (error != null) {
                            error.setText(message);
                            error.setVisibility(TextView.VISIBLE);
                        }
                    }

                    if (button != null) button.setEnabled(true);
                    if (mainLayout != null) mainLayout.setAlpha(1);

                    return;
                }




                JSONArray jsonArray = jsonObject.getJSONArray("students");

                list.clear();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject student = jsonArray.getJSONObject(i);

                    String name = student.getString("name");
                    String score = String.valueOf(student.getDouble("score"));
                    String student_id = student.getString("student_id");

                    list.add(new Student(name, score, student_id));
                }

                studentAdapter.notifyDataSetChanged();



                if (jsonObject.has("message")) {
                    if (data.equals("upload")) {
                        String message = jsonObject.getString("message");

                        Log.e(TAG, "success");

                        if (jsonObject.has("internship_id")) {

                            String internshipId = jsonObject.getString("internship_id");

                            for (String skill : skillList) {

                                String skillUrl = MainActivity.url +
                                        "company_skills/?name=" + skill +
                                        "&internship_id=" + internshipId;

                                fetchData(skillUrl);
                            }
                        }
                        return;
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, "JSON parsing error: " + e);
            }

        } else {
            Log.e(TAG, "null fetch error");
        }

    }


}
