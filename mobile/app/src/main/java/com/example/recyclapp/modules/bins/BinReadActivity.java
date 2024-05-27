package com.example.recyclapp.modules.bins;

import static androidx.appcompat.app.AlertDialog.Builder;
import static androidx.appcompat.app.AlertDialog.OnClickListener;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityBinBinding;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.modules.bins.model.Bin;
import com.example.recyclapp.modules.bins.model.Color;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.modules.menus.Home;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinReadActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private List<Bin> bins;
    private List<Marker> markers = new ArrayList<>();
    private ActivityBinBinding binding;
    private GoogleMap map;
    private final String ROJO = "ROJO";
    private final String VERDE = "VERDE";
    private final String AZUL = "AZUL";
    private final String GRIS = "GRIS";
    private final String NEGRO = "NEGRO";
    //todo: cambiar iconos
    private Drawable D_ROJO;
    private Drawable D_VERDE;
    private Drawable D_AZUL;
    private Drawable D_GRIS;
    private Drawable D_NEGRO;
    private String crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mapView = binding.mapView;

        D_ROJO = ContextCompat.getDrawable(this, R.mipmap.pin_red);
        D_VERDE = ContextCompat.getDrawable(this, R.mipmap.pin_green);
        D_AZUL = ContextCompat.getDrawable(this, R.mipmap.pin_blue);
        D_GRIS = ContextCompat.getDrawable(this, R.mipmap.pin_gray);
        D_NEGRO = ContextCompat.getDrawable(this, R.mipmap.pin_black);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            crud = extras.getString("case");
        }
        binding.splash.setVisibility(View.VISIBLE);
        binding.mapView.setVisibility(View.GONE);
        binding.llData.setVisibility(View.GONE);
        binding.title.setVisibility(View.GONE);
        binding.ivBack.setVisibility(View.GONE);
        onClick();
        loadBins(savedInstanceState);
    }

    public void loadBins(Bundle savedInstanceState) {
        if (InitView.isOffline) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(BinReadActivity.this);
            return;
        }
        try {
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            Call<List<Bin>> answerCall = service.getAllBins();
            answerCall.enqueue(new Callback<List<Bin>>() {
                @Override
                public void onResponse(Call<List<Bin>> call, Response<List<Bin>> response) {
                    if (response.isSuccessful()) {
                        List<Bin> bins = response.body();
                        BinReadActivity.this.bins = bins;
                        mapView.onCreate(savedInstanceState);
                        mapView.getMapAsync(BinReadActivity.this);
                    } else {
                        Toast.makeText(BinReadActivity.this, "Algo salió mal!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<List<Bin>> call, Throwable t) {
                    Toast.makeText(BinReadActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        } catch (Throwable e) {
            Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    public void onClick() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.todos.setOnClickListener(v -> {
            deleteMarkers();
            loadAllMarkers();
        });
        binding.plastico.setOnClickListener(v -> {
            deleteMarkers();
            loadMarkers(AZUL);
        });
        binding.papelCarton.setOnClickListener(v -> {
            deleteMarkers();
            loadMarkers(GRIS);
        });
        binding.ordinario.setOnClickListener(v -> {
            deleteMarkers();
            loadMarkers(VERDE);
        });
        binding.residuos.setOnClickListener(v -> {
            deleteMarkers();
            loadMarkers(ROJO);
        });
        binding.otros.setOnClickListener(v -> {
            deleteMarkers();
            loadMarkers(NEGRO);
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.IntentWFinish(BinReadActivity.this, Home.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Obtén una instancia de GoogleMap y configura la cámara
        map = googleMap;
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomGesturesEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.setOnMarkerClickListener(this);
        updateMap();
        binding.splash.setVisibility(View.GONE);
        binding.mapView.setVisibility(View.VISIBLE);
        binding.llData.setVisibility(View.VISIBLE);
        binding.title.setVisibility(View.VISIBLE);
        binding.ivBack.setVisibility(View.VISIBLE);
        loadAllMarkers();
    }
    Thread myLocation = null;

    public void updateMap() {
        try {
            myLocation = new Thread(() -> {
                final Marker[] oldMarker = {null};
                final boolean[] isFirstTime = {true};
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        runOnUiThread(() -> {
                            //Obtener ubicacion actual
                            String[] location = GPS.getInstance().getLocalization(BinReadActivity.this).split("\\|");
                            LatLng latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));

                            if (oldMarker[0] != null) {
                                markers.remove(oldMarker[0]);
                            }
                            oldMarker[0] = map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_ROJO))));
                            markers.add(oldMarker[0]);
                            if (isFirstTime[0]) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                                isFirstTime[0] = false;
                            }
                            Toast.makeText(BinReadActivity.this, "Pava", Toast.LENGTH_SHORT).show();
                        });
                        Thread.sleep(3000);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            });
            myLocation.start();
        } catch (Throwable e) {
            e.printStackTrace();

        }
    }

    public void loadAllMarkers() {
        if (bins != null) {
            for (Bin bin : bins) {
                LatLng latLng = new LatLng(Double.parseDouble(String.valueOf(bin.getLatitude())), Double.parseDouble(String.valueOf(bin.getLongitude())));
                BitmapDescriptor bitmap;
                switch (bin.getColor().getName()) {
                    case ROJO:
                        bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_ROJO));
                        break;
                    case VERDE:
                        bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_VERDE));
                        break;
                    case AZUL:
                        bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_AZUL));
                        break;
                    case GRIS:
                        bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_GRIS));
                        break;
                    default:
                        bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_NEGRO));
                }
                Marker marker = map.addMarker(new MarkerOptions().position(latLng).icon(bitmap));
                markers.add(marker);
            }
        }
    }

    public void loadMarkers(String color) {
        if (bins != null) {
            for (Bin bin : bins) {
                if (color.equals(bin.getColor().getName())) {
                    LatLng latLng = new LatLng(Double.parseDouble(String.valueOf(bin.getLatitude())), Double.parseDouble(String.valueOf(bin.getLongitude())));
                    BitmapDescriptor bitmap;
                    switch (color) {
                        case ROJO:
                            bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_ROJO));
                            break;
                        case VERDE:
                            bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_VERDE));
                            break;
                        case AZUL:
                            bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_AZUL));
                            break;
                        case GRIS:
                            bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_GRIS));
                            break;
                        default:
                            bitmap = BitmapDescriptorFactory.fromBitmap(drawableToBitmap(D_NEGRO));
                    }
                    Marker marker = map.addMarker(new MarkerOptions().position(latLng).icon(bitmap));
                    markers.add(marker);
                }
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            // Redimensiona el bitmap existente
            return Bitmap.createScaledBitmap(bitmap, 60, 60, true);
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, 60, 60);
        drawable.draw(canvas);
        return bitmap;
    }

    public void deleteMarkers() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mapView != null) {
                mapView.onDestroy();
            }
            if (myLocation != null) {
                myLocation.interrupt();
                myLocation.stop();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (!checkPermissionResult(grantResults)) {
                Toast.makeText(this, "Si no se aceptan los permisos no se puede acceder", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkPermissionResult(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == -1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Bin binx = null;
        if(InitView.isOffline) {
            binx = new Bin();
            Color color1 = new Color();
            color1.setId(1);
            color1.setName("ROJO");
            binx.setId(1);
            binx.setColor(color1);
            binx.setLatitude(BigDecimal.valueOf(8.236657));
            binx.setLongitude(BigDecimal.valueOf(-73.320721));
        }
        /*for (Bin bin : bins) {
            if (bin.getLatitude().equals(marker.getPosition().latitude) && bin.getLongitude().equals(marker.getPosition().longitude)) {
                binx = bin;
            }
        }*/
        if (binx != null) {
            if ("UPDATE".equals(crud)) {
                showAlertDialog(binx, true);
            } else if ("DELETE".equals(crud)) {
                showAlertDialog(binx, false);
            }
            return true;
        }
        return false;
    }

    public void showAlertDialog(Bin bin, boolean isUpdate) {
        Builder builder = new Builder(this);
        builder.setTitle("IMPORTANTE");
        builder.setMessage("Deseas " + (isUpdate ? "actualizar" : "eliminar") + " la caneca seleccionada?");
        builder.setPositiveButton("Aceptar", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(isUpdate){
                    Intent intent = new Intent(BinReadActivity.this, BinAddActivity.class);
                    intent.putExtra("case", bin.toString());
                    startActivity(intent);
                    finish();
                    return;
                }
                Toast.makeText(BinReadActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}