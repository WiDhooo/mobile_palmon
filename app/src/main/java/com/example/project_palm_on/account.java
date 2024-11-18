package com.example.project_palm_on;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class account extends Fragment {

    private Button keluar, simpan;
    private EditText namaField, noHpField;
    private TextView tanggalLahir, emailField;
    private String userId;
    private ImageButton btn_tgl_lahir;
    private MaterialAutoCompleteTextView jenisKelaminAutoCompleteTextView;

    public account() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Inisialisasi komponen UI
        namaField = view.findViewById(R.id.isi_nama_account);
        noHpField = view.findViewById(R.id.isi_no_hp_account);
        jenisKelaminAutoCompleteTextView = view.findViewById(R.id.spinner_jenis_kelamin);
        tanggalLahir = view.findViewById(R.id.isi_tanggal_lahir_account);
        emailField = view.findViewById(R.id.isi_email_account);
        btn_tgl_lahir = view.findViewById(R.id.button_tanggal_lahir);
        simpan = view.findViewById(R.id.button_simpan_account);
        keluar = view.findViewById(R.id.button_keluar_account);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        userId = sharedPreferences.getString("user_id", null);

        if (userId != null) {
            fetchUserData();
        }

        setupGenderAutoCompleteTextView();
        setupDatePicker();

        simpan.setOnClickListener(v -> {
            if (userId != null) {
                updateUserData();
            }
        });

        keluar.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("user_id");
            editor.apply();
            Toast.makeText(getActivity(), "Anda telah logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), login_user.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void setupGenderAutoCompleteTextView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.gender_array,
                android.R.layout.simple_dropdown_item_1line
        );
        jenisKelaminAutoCompleteTextView.setAdapter(adapter);

        jenisKelaminAutoCompleteTextView.setOnClickListener(v -> jenisKelaminAutoCompleteTextView.showDropDown());
        jenisKelaminAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedGender = parent.getItemAtPosition(position).toString();
            Toast.makeText(getActivity(), "Terpilih: " + selectedGender, Toast.LENGTH_SHORT).show();
        });
    }

    private void setupDatePicker() {
        btn_tgl_lahir.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                tanggalLahir.setText(date);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }

    private void fetchUserData() {
        String url = Constants.URL_USER + "/" + userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        namaField.setText(jsonObject.optString("username"));
                        noHpField.setText(jsonObject.optString("no_telp"));
                        emailField.setText(jsonObject.optString("email"));
                        tanggalLahir.setText(jsonObject.optString("tanggal_lahir"));
                        String gender = jsonObject.optString("jenis_kelamin");
                        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) jenisKelaminAutoCompleteTextView.getAdapter();
                        int position = adapter.getPosition(gender);
                        if (position >= 0) {
                            jenisKelaminAutoCompleteTextView.setText(adapter.getItem(position), false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Gagal mengambil data pengguna", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getActivity(), "Gagal mengambil data pengguna", Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(requireContext()).add(stringRequest);
    }

    private void updateUserData() {
        String url = Constants.URL_USER + "/" + userId;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", namaField.getText().toString());
            jsonBody.put("no_telp", noHpField.getText().toString());
            jsonBody.put("email", emailField.getText().toString());
            jsonBody.put("tanggal_lahir", tanggalLahir.getText().toString());
            jsonBody.put("jenis_kelamin", jenisKelaminAutoCompleteTextView.getText().toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                    response -> Toast.makeText(getActivity(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show(),
                    error -> {
                        String errorMessage = "Gagal mengupdate data";
                        if (error.networkResponse != null) {
                            errorMessage += ": " + error.networkResponse.statusCode;
                        }
                        Log.e("API_UPDATE_ERROR", errorMessage, error);
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
            );
            Volley.newRequestQueue(requireContext()).add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Gagal membuat JSON", Toast.LENGTH_SHORT).show();
        }
    }

}
