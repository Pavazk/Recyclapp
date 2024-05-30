package com.example.recyclapp.modules.products;

import static com.example.recyclapp.modules.bins.BinReadActivity.drawableToBitmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.databinding.ActivityProductBinding;
import com.example.recyclapp.modules.bins.GPS;
import com.example.recyclapp.modules.bins.model.Bin;
import com.example.recyclapp.modules.menus.Home;
import com.example.recyclapp.modules.products.adapter.ProductAdapter;
import com.example.recyclapp.modules.products.model.Item;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements OnMapReadyCallback {

    List<Item> items;
    ProductAdapter productAdapter;
    ActivityProductBinding binding;
    List<Bin> bins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(this);
        items = new ArrayList<>();
        bins = new ArrayList<>();
        try {
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            service.getAllItems().enqueue(new Callback<List<Item>>() {
                @Override
                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                    if (response.isSuccessful()) {
                        items = response.body();
                        if (items.isEmpty()) {
                            Toast.makeText(ProductActivity.this, "No hay productos disponibles", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            return;
                        }
                        productAdapter = new ProductAdapter(items, position -> {
                            binding.mapView.setVisibility(View.VISIBLE);
                            binding.groupButton.setVisibility(View.VISIBLE);
                            binding.listProducts.setVisibility(View.GONE);
                            loadBin(items.get(position));
                            binding.button.setOnClickListener(v -> {
                                binding.mapView.setVisibility(View.GONE);
                                binding.groupButton.setVisibility(View.GONE);
                                binding.listProducts.setVisibility(View.VISIBLE);
                                binding.mapView.onCreate(savedInstanceState);
                                binding.mapView.getMapAsync(ProductActivity.this);
                            });
                        });
                        binding.listProducts.setAdapter(productAdapter);
                        binding.listProducts.setLayoutManager(new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.VERTICAL, false));
                        return;
                    }
                    Toast.makeText(ProductActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

                @Override
                public void onFailure(Call<List<Item>> call, Throwable t) {
                    Toast.makeText(ProductActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProductActivity.this, Home.class));
    }

    Marker mylocation = null;
    GoogleMap map = null;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomGesturesEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        GPS.getInstance().getLocationByGoogleService(this, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                LatLng latLng;
                try {
                    latLng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                } catch (Throwable e) {
                    e.printStackTrace();
                    Toast.makeText(ProductActivity.this, "No se pudo obtener una ubicación precisa", Toast.LENGTH_SHORT).show();
                    String[] location = GPS.getInstance().getLocalization(ProductActivity.this).split("\\|");
                    latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
                }
                mylocation = map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(ContextCompat.getDrawable(ProductActivity.this, R.mipmap.icon_my_location)))));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            }
        });
    }

    public void loadBin(Item item) {
        APIService service = Utils.getRetrofit(this).create(APIService.class);
        service.getAllBins().enqueue(new Callback<List<Bin>>() {
            @Override
            public void onResponse(Call<List<Bin>> call, Response<List<Bin>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(ProductActivity.this, "No hay canecas disponibles", Toast.LENGTH_SHORT).show();
                        binding.mapView.setVisibility(View.GONE);
                        binding.groupButton.setVisibility(View.GONE);
                        binding.listProducts.setVisibility(View.VISIBLE);
                        return;
                    }
                    for (Bin bin : response.body()) {
                        if (bin.getColor().getName().equals(item.getColor().getName())) {
                            bins.add(bin);
                        }
                    }
                    Bin bin = encontrarCanecaCercana(bins);
                    drawMarker(bin);
                    drawPolyline(mylocation.getPosition(), new LatLng(bin.getLatitude(), bin.getLongitude()));
                    return;
                }
                Toast.makeText(ProductActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<List<Bin>> call, Throwable t) {

                Toast.makeText(ProductActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    private void drawMarker(Bin bin) {
        LatLng latLng = new LatLng(Double.parseDouble(String.valueOf(bin.getLatitude())), Double.parseDouble(String.valueOf(bin.getLongitude())));
        BitmapDescriptor bitmap;
        switch (bin.getColor().getName().toUpperCase()) {
            case "ROJO":
                bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.pin_red)));
                break;
            case "VERDE":
                bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.pin_green)));
                break;
            case "AZUL":
                bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.pin_blue)));
                break;
            case "GRIS":
                bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.pin_gray)));
                break;
            default:
                bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.pin_black)));
        }
        map.addMarker(new MarkerOptions().position(latLng).icon(bitmap));
    }

    private void drawPolyline(LatLng from, LatLng to) {
        PolylineOptions options = new PolylineOptions()
                .add(from)
                .add(to)
                .width(5)
                .color(Color.RED);
        map.addPolyline(options);
    }

    public Bin encontrarCanecaCercana(List<Bin> listaUbicaciones) {
        if (mylocation == null || listaUbicaciones == null || listaUbicaciones.isEmpty()) {
            Toast.makeText(this, "Ubicaciones no validas", Toast.LENGTH_SHORT).show();
            binding.mapView.setVisibility(View.GONE);
            binding.groupButton.setVisibility(View.GONE);
            binding.listProducts.setVisibility(View.VISIBLE);
        }

        Bin canecaCercana = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Bin bin : listaUbicaciones) {
            double distancia = distanciaEuclidiana(mylocation.getPosition(), new LatLng(bin.getLatitude(), bin.getLongitude()));
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                canecaCercana = bin;
            }
        }

        return canecaCercana;
    }

    private double distanciaEuclidiana(LatLng ubicacion1, LatLng ubicacion2) {
        double dx = ubicacion1.latitude - ubicacion2.latitude;
        double dy = ubicacion1.longitude - ubicacion2.longitude;
        return Math.sqrt(dx * dx + dy * dy);
    }
}