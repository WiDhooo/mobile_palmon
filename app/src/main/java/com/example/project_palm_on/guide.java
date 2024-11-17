package com.example.project_palm_on;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class guide extends AppCompatActivity {

    ImageView ic_kembali_guide;
    Button btn_penanaman_guide, btn_perawatan_guide, btn_pemanenan_guide;
    EditText searchGuideInput;

    private GuideViewModel guideViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        // Initialize ViewModel
        guideViewModel = new ViewModelProvider(this).get(GuideViewModel.class);

        // Initialize UI elements
        btn_penanaman_guide = findViewById(R.id.button_penanaman_guide);
        btn_perawatan_guide = findViewById(R.id.button_perawatan_guide);
        btn_pemanenan_guide = findViewById(R.id.button_pemanenan_guide);
        ic_kembali_guide = findViewById(R.id.icon_kembali_guide);
//        searchGuideInput = findViewById(R.id.serch_guide);

        ic_kembali_guide.setOnClickListener(view -> finish());

        // Set default fragment and active button
        setActiveButton(btn_penanaman_guide);
        getGuideData("Penanaman");

        // Button listeners for fragment navigation
        btn_penanaman_guide.setOnClickListener(v -> {
            setActiveButton(btn_penanaman_guide);
            getGuideData("Penanaman");
        });

        btn_perawatan_guide.setOnClickListener(v -> {
            setActiveButton(btn_perawatan_guide);
            getGuideData("Perawatan");
        });

        btn_pemanenan_guide.setOnClickListener(v -> {
            setActiveButton(btn_pemanenan_guide);
            getGuideData("Pemanenan");
        });

//        // Search functionality
//        searchGuideInput.setOnEditorActionListener((v, actionId, event) -> {
//            String query = searchGuideInput.getText().toString().trim();
//            if (TextUtils.isEmpty(query)) {
//                // If no query, show category buttons and load default data
//                findViewById(R.id.kumpulan_tombol_guide).setVisibility(View.VISIBLE);
//                getGuideData("Penanaman"); // Load default category data
//            } else {
//                // If there's a query, hide category buttons and perform search
//                findViewById(R.id.kumpulan_tombol_guide).setVisibility(View.GONE);
//                searchGuides(query);
//            }
//            return true;
//        });
    }

    private void setActiveButton(Button activeButton) {
        resetButtonStyles();
        activeButton.setBackgroundColor(getResources().getColor(R.color.green_login));
        activeButton.setTextColor(getResources().getColor(R.color.white));
    }

    private void resetButtonStyles() {
        btn_penanaman_guide.setBackgroundColor(getResources().getColor(R.color.white));
        btn_penanaman_guide.setTextColor(getResources().getColor(R.color.black));

        btn_perawatan_guide.setBackgroundColor(getResources().getColor(R.color.white));
        btn_perawatan_guide.setTextColor(getResources().getColor(R.color.black));

        btn_pemanenan_guide.setBackgroundColor(getResources().getColor(R.color.white));
        btn_pemanenan_guide.setTextColor(getResources().getColor(R.color.black));
    }

    private void getGuideData(String category) {
        String url = Constants.URL_GUIDE + "/tag/" + category;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<GuideItem> guideItems = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject guideObject = response.getJSONObject(i);
                                String author = guideObject.getString("nama_pembuat");
                                String title = guideObject.getString("judul");
                                String description = guideObject.getString("isi");
                                String time = guideObject.getString("created_at");
                                String imageUrl = guideObject.getString("gambar");

                                guideItems.add(new GuideItem(author, title, description, time, imageUrl, category));
                            }

                            guideViewModel.setGuideItems(guideItems);
                            sendDataToFragment(category);
                        } catch (Exception e) {
                            Log.e("Parse Error", "Error parsing guide data", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

//    private void searchGuides(String query) {
//        String url = Constants.URL_GUIDE + "/search?query=" + query;
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        List<GuideItem> guideItems = new ArrayList<>();
//                        try {
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject guideObject = response.getJSONObject(i);
//                                String author = guideObject.getString("nama_pembuat");
//                                String title = guideObject.getString("judul");
//                                String description = guideObject.getString("isi");
//                                String time = guideObject.getString("created_at");
//                                String imageUrl = guideObject.getString("gambar");
//
//                                guideItems.add(new GuideItem(author, title, description, time, imageUrl, "Search"));
//                            }
//
//                            guideViewModel.setGuideItems(guideItems);
//                            sendDataToFragment("Search");
//                        } catch (Exception e) {
//                            Log.e("Parse Error", "Error parsing search results", e);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Volley Error", error.toString());
//                    }
//                });
//
//        Volley.newRequestQueue(this).add(jsonArrayRequest);
//    }

    private void sendDataToFragment(String category) {
        Fragment fragment;

        switch (category) {
            case "Pemanenan":
                fragment = new pemanenan_fragment_guide();
                break;
            case "Perawatan":
                fragment = new perawatan_fragment_guide();
                break;
//            case "Search":
//                // Pass the guideItems from the ViewModel to the fragment using newInstance()
//                fragment = search_results_fragment.newInstance(guideViewModel.getGuideItems().getValue());
//                break;
            default:
                fragment = new penanaman_fragment_guide();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_guide, fragment)
                .commit();
    }
}
