package com.example.recyclapp.modules.bins;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.databinding.ActivityBinAddBinding;
import com.example.recyclapp.modules.bins.model.Bin;
import com.example.recyclapp.modules.bins.model.Color;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.modules.menus.Home;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinAddActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityBinAddBinding binding;
    private ArrayAdapter<String> adapter;
    private List<Color> listColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBinAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.splash.setVisibility(View.VISIBLE);
        binding.ivBack.setVisibility(View.GONE);
        binding.title.setVisibility(View.GONE);
        binding.spinnerContainer.setVisibility(View.GONE);
        binding.titleLatitud.setVisibility(View.GONE);
        binding.titleLongitud.setVisibility(View.GONE);
        binding.tvLatitud.setVisibility(View.GONE);
        binding.tvLongitud.setVisibility(View.GONE);
        binding.ivImage.setVisibility(View.GONE);
        binding.groupButton.setVisibility(View.GONE);
        binding.groupSearchLocation.setVisibility(View.GONE);
        binding.groupThisLocation.setVisibility(View.GONE);
        binding.mapView.setVisibility(View.GONE);
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(this);
        listColor = new ArrayList<>();
        if (!InitView.isOffline) {
            loadColors();
            return;
        }
        List<Color> list = new ArrayList<>();
        Color color1 = new Color();
        color1.setName("ROJO");
        Color color2 = new Color();
        color2.setName("AZUL");
        Color color3 = new Color();
        color3.setName("VERDE");
        Color color4 = new Color();
        color4.setName("GRIS");
        Color color5 = new Color();
        color5.setName("NEGRO");
        list.add(color1);
        list.add(color2);
        list.add(color3);
        list.add(color4);
        list.add(color5);
        loadView();
    }

    public void loadColors() {
        try {
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            Call<List<Color>> answerCall = service.getAllColors();
            answerCall.enqueue(new Callback<List<Color>>() {
                @Override
                public void onResponse(Call<List<Color>> call, Response<List<Color>> response) {
                    if (response.isSuccessful()) {
                        listColor = response.body();
                        loadView();
                    } else {
                        Toast.makeText(BinAddActivity.this, "Algo salió mal!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<List<Color>> call, Throwable t) {
                    Toast.makeText(BinAddActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private void loadView() {
        try {
            List<String> data = new ArrayList<>();
            for (Color color : listColor) {
                data.add(color.getName().toUpperCase());
            }
            adapter = new ArrayAdapter<>(BinAddActivity.this, R.layout.spinner_item_layout, data);
            adapter.setDropDownViewResource(R.layout.spinner_item_layout);
            binding.spinner.setAdapter(adapter);
            Bundle bundle = getIntent().getExtras();
            if (bundle != null && bundle.containsKey("case")) {
                Bin bin = Bin.fromString(bundle.getString("case"));
                binding.tvLongitud.setText(String.valueOf(bin.getLongitude()));
                binding.tvLatitud.setText(String.valueOf(bin.getLatitude()));
                for (int i = 0; i < listColor.size(); i++) {
                    if (listColor.get(i).getName().equals(bin.getColor())) {
                        binding.spinner.setSelection(i);
                        break;
                    }
                }
                binding.button.setText("Actualizar caneca");
            } else {
                String[] a = GPS.getInstance().getLocalization(BinAddActivity.this).split("\\|");
                binding.tvLatitud.setText(a[0]);
                binding.tvLongitud.setText(a[1]);
                binding.spinner.setSelection(0);
            }
            binding.splash.setVisibility(View.GONE);
            binding.ivBack.setVisibility(View.VISIBLE);
            binding.groupSearchLocation.setVisibility(View.VISIBLE);
            binding.searchLocation.setOnClickListener(v -> {
                binding.mapView.setVisibility(View.VISIBLE);
                binding.ivImage.setVisibility(View.GONE);
                binding.groupThisLocation.setVisibility(View.GONE);
                binding.groupSearchLocation.setVisibility(View.GONE);
            });
            binding.groupThisLocation.setVisibility(View.VISIBLE);
            binding.thisLocation.setOnClickListener(v -> {
                String[] a = GPS.getInstance().getLocalization(BinAddActivity.this).split("\\|");
                binding.tvLatitud.setText(a[0]);
                binding.tvLongitud.setText(a[1]);
            });
            binding.ivBack.setOnClickListener(v -> onBackPressed());
            binding.title.setVisibility(View.VISIBLE);
            binding.spinnerContainer.setVisibility(View.VISIBLE);
            binding.titleLatitud.setVisibility(View.VISIBLE);
            binding.titleLongitud.setVisibility(View.VISIBLE);
            binding.tvLatitud.setVisibility(View.VISIBLE);
            binding.tvLongitud.setVisibility(View.VISIBLE);
            binding.ivImage.setVisibility(View.VISIBLE);
            binding.groupButton.setVisibility(View.VISIBLE);
            binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (listColor.get(position).getName().toUpperCase()) {
                        case "ROJO":
                            binding.ivImage.setImageResource(R.mipmap.bin_red);
                            break;
                        case "AZUL":
                            binding.ivImage.setImageResource(R.mipmap.bin_blue);
                            break;
                        case "VERDE":
                            binding.ivImage.setImageResource(R.mipmap.bin_green);
                            break;
                        case "GRIS":
                            binding.ivImage.setImageResource(R.mipmap.bin_gray);
                            break;
                        case "NEGRO":
                            binding.ivImage.setImageResource(R.mipmap.bin_black);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.button.setOnClickListener(v -> {
                try {
                    Bin bin = new Bin();
                    bin.setColor(listColor.get(binding.spinner.getSelectedItemPosition()));
                    bin.setLatitude(Double.parseDouble(binding.tvLatitud.getText().toString()));
                    bin.setLongitude(Double.parseDouble(binding.tvLongitud.getText().toString()));
                    Log.e("PAVA", binding.tvLatitud.getText().toString() + " -> " + bin.getLatitude());
                    Log.e("PAVA", binding.tvLongitud.getText().toString() + " -> " + bin.getLongitude());
                    APIService service = Utils.getRetrofit(BinAddActivity.this).create(APIService.class);
                    Bundle bundle1 = getIntent().getExtras();
                    if (bundle1 != null) {
                        service.updateBin(bin, Bin.fromString(bundle.getString("case")).getId()).enqueue(new Callback<Bin>() {
                            @Override
                            public void onResponse(Call<Bin> call, Response<Bin> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(BinAddActivity.this, "Caneca actualizada con éxito", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                                Toast.makeText(BinAddActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Bin> call, Throwable t) {
                                Toast.makeText(BinAddActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        });
                        return;
                    }
                    service.registerBin(bin).enqueue(new Callback<Bin>() {
                        @Override
                        public void onResponse(Call<Bin> call, Response<Bin> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(BinAddActivity.this, "Caneca registrada con éxito", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }

                        @Override
                        public void onFailure(Call<Bin> call, Throwable t) {
                            Toast.makeText(BinAddActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                } catch (Throwable e) {
                    e.printStackTrace();
                    Toast.makeText(BinAddActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BinAddActivity.this, "Algo salió mal!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.IntentWFinish(BinAddActivity.this, Home.class);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnMapClickListener(latLng -> {
            binding.tvLongitud.setText(String.valueOf(latLng.longitude));
            binding.tvLatitud.setText(String.valueOf(latLng.latitude));
            binding.mapView.setVisibility(View.GONE);
            binding.ivImage.setVisibility(View.VISIBLE);
            binding.groupThisLocation.setVisibility(View.VISIBLE);
            binding.groupSearchLocation.setVisibility(View.VISIBLE);
        });
        String[] location = GPS.getInstance().getLocalization(BinAddActivity.this).split("\\|");
        LatLng latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
    }
}