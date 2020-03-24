package com.fahmisbas.covid19indonesia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private TextView tvJumlahKasus, tvSembuh, tvPerawatan, tvMeninggal;
    private ArrayList<ProvinsiModel> provinsiDataList = new ArrayList<>();
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvJumlahKasus = view.findViewById(R.id.tv_jumlahkasus);
        tvSembuh = view.findViewById(R.id.tv_sembuh);
        tvPerawatan = view.findViewById(R.id.tv_perawatan);
        tvMeninggal = view.findViewById(R.id.tv_meninggal);

        JSONIndoData jsonIndo = new JSONIndoData("https://indonesia-covid-19.mathdro.id/api");
        jsonIndo.start();

        return view;
    }

    public class JSONIndoData extends Thread {  //ga make asyntask waowkaok

        String result = "";
        String address;

        JSONIndoData(String address) {
            this.address = address;
        }

        @Override
        public void run() {
            super.run();
            getJson();
        }

        private void getJson() {
            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int reading = reader.read();

                while (reading != -1) {
                    char current = (char) reading;
                    result += current;
                    reading = reader.read();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processingIndonesiaJSON(result);
                        getProvinsiJSON(result);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processingIndonesiaJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String meninggal = jsonObject.getString("meninggal");
            tvMeninggal.setText(meninggal);
            String perawatan = jsonObject.getString("perawatan");
            tvPerawatan.setText(perawatan);
            String sembuh = jsonObject.getString("sembuh");
            tvSembuh.setText(sembuh);
            String jumlahkasus = jsonObject.getString("jumlahKasus");
            tvJumlahKasus.setText(jumlahkasus);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getProvinsiJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String perProvinsi = jsonObject.getString("perProvinsi");
            JSONObject objectProvinsi = new JSONObject(perProvinsi);
            String jsonAddress = objectProvinsi.getString("json");


            JSONProvinsiData provinsiData = new JSONProvinsiData(jsonAddress);
            provinsiData.start();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class JSONProvinsiData extends Thread {

        String address;
        String result = "";

        JSONProvinsiData(String address) {
            this.address = address;
        }

        @Override
        public void run() {
            super.run();
            getJson();
        }

        private void getJson() {
            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int reading = reader.read();

                while (reading != -1) {
                    char current = (char) reading;
                    result += current;
                    reading = reader.read();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processingProvinsiJSON(result);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processingProvinsiJSON(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String data = jsonObject.getString("data");

            JSONArray array = new JSONArray(data);

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonPart = array.getJSONObject(i);
                ProvinsiModel provinsiModel = new ProvinsiModel();

                String provinsi = jsonPart.getString("provinsi");
                String kasusPositif = jsonPart.getString("kasusPosi");
                String kasusSembuh = jsonPart.getString("kasusSemb");
                String kasusMeninggal = jsonPart.getString("kasusMeni");
                int kodeProvinsi = jsonPart.getInt("kodeProvi");

                provinsiModel.setKodeProvinsi(kodeProvinsi);
                provinsiModel.setKasusPositif(kasusPositif);
                provinsiModel.setKasusSembuh(kasusSembuh);
                provinsiModel.setKasusMeninggal(kasusMeninggal);
                provinsiModel.setNamaProvinsi(provinsi);

                provinsiDataList.add(provinsiModel);
            }

            initRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        RecyclerView rvProvinsi = view.findViewById(R.id.rv_provinsiData);
        ProvinsiDataAdapter adapter = new ProvinsiDataAdapter(provinsiDataList );
        rvProvinsi.setAdapter(adapter);
    }
}
