package com.example.project_palm_on;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("kalkulasi/{id}")
    Call<KalkulasiResponse> getKalkulasiById(@Path("id") int id);
}
