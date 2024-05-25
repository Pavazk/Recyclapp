package com.example.recyclapp.modules.bins;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityBinAddBinding;
import com.example.recyclapp.interfaces.APIService;
import com.example.recyclapp.models.ResponseFailure;
import com.example.recyclapp.models.User;
import com.example.recyclapp.modules.bins.model.Bin;
import com.example.recyclapp.modules.bins.model.Color;
import com.example.recyclapp.modules.menus.Home;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.location.FusedOrientationProviderClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinAddActivity extends AppCompatActivity {
    private ActivityBinAddBinding binding;
    ArrayAdapter<String> adapter;

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
        //loadColors();
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
        loadView(list);
    }

    public void loadColors() {
        try {
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            Call<List<Color>> answerCall = service.getAllColors();
            answerCall.enqueue(new Callback<List<Color>>() {
                @Override
                public void onResponse(Call<List<Color>> call, Response<List<Color>> response) {
                    if (response.isSuccessful()) {
                        List<Color> colors = response.body();
                        loadView(colors);
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

    private void loadView(List<Color> colors) {
        try {
            List<String> data = new ArrayList<>();
            for (Color color : colors) {
                data.add(color.getName());
            }
            adapter = new ArrayAdapter<>(BinAddActivity.this, R.layout.spinner_item_layout, data);
            adapter.setDropDownViewResource(R.layout.spinner_item_layout);
            binding.spinner.setAdapter(adapter);
            Bundle bundle = getIntent().getExtras();
            if (bundle != null && bundle.containsKey("case")) {
                Bin bin = Bin.fromString(bundle.getString("case"));
                binding.tvLongitud.setText(String.valueOf(bin.getLongitude()));
                binding.tvLatitud.setText(String.valueOf(bin.getLatitude()));
                for (int i = 0; i < colors.size(); i++) {
                    if (colors.get(i).getName().equals(bin.getColor())) {
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
            binding.groupThisLocation.setVisibility(View.VISIBLE);
            binding.groupThisLocation.setOnClickListener(v -> {
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
                    switch (position) {
                        case 0:
                            binding.ivImage.setImageResource(R.mipmap.bin_red);
                            break;
                        case 1:
                            binding.ivImage.setImageResource(R.mipmap.bin_blue);
                            break;
                        case 2:
                            binding.ivImage.setImageResource(R.mipmap.bin_green);
                            break;
                        case 3:
                            binding.ivImage.setImageResource(R.mipmap.bin_gray);
                            break;
                        case 4:
                            binding.ivImage.setImageResource(R.mipmap.bin_black);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.button.setOnClickListener(v -> Toast.makeText(BinAddActivity.this, "Se enviará la información", Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BinAddActivity.this, "Algo salió mal!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.Intent(BinAddActivity.this, Home.class);
    }
}