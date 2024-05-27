package com.example.recyclapp.modules.events;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.databinding.ActivityEventBinding;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.modules.events.model.RegisterEvent;
import com.example.recyclapp.modules.main.data.User;
import com.example.recyclapp.modules.events.adapters.UserAdapter;
import com.example.recyclapp.modules.events.adapters.UserSelectedAdapter;
import com.example.recyclapp.modules.events.model.CollectionType;
import com.example.recyclapp.modules.events.model.EventType;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.modules.menus.Home;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {

    private ActivityEventBinding binding;
    private List<User> users;
    UserAdapter userAdapter;

    private List<User> usersSelected;
    UserSelectedAdapter userSelectedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUsers();
        initUserSelected();
        binding.button.setOnClickListener(v -> {
            /*RegisterEvent registerEvent = new RegisterEvent();
            registerEvent.setName(re);
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            service.registerEvent()*/
        });
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

    private void loadUsers() {
        try {
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            service.getAllUsers().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        for (User user : response.body()) {
                            if (!Utils.restore(EventActivity.this, Utils.KEY_CODE).equals(user.getCode())) {
                                users.add(user);
                            }
                        }
                        service.getAllCollectionType().enqueue(new Callback<List<CollectionType>>() {
                            @Override
                            public void onResponse(Call<List<CollectionType>> call, Response<List<CollectionType>> response) {
                                if (response.isSuccessful()) {
                                    List<String> data = new ArrayList<>();
                                    for (CollectionType collectionType : response.body()) {
                                        data.add(collectionType.getName());
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EventActivity.this, R.layout.spinner_item_layout, data);
                                    adapter.setDropDownViewResource(R.layout.spinner_item_layout);
                                    binding.spinner2.setAdapter(adapter);
                                    service.getAllEventType().enqueue(new Callback<List<EventType>>() {
                                        @Override
                                        public void onResponse(Call<List<EventType>> call, Response<List<EventType>> response) {
                                            if (response.isSuccessful()) {
                                                List<String> data = new ArrayList<>();
                                                for (EventType eventType : response.body()) {
                                                    data.add(eventType.getName());
                                                }
                                                ArrayAdapter<String> adapter = new ArrayAdapter<>(EventActivity.this, R.layout.spinner_item_layout, data);
                                                adapter.setDropDownViewResource(R.layout.spinner_item_layout);
                                                binding.spinner.setAdapter(adapter);
                                            } else {
                                                Toast.makeText(EventActivity.this, "Algo falló en el servidor evento", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<EventType>> call, Throwable t) {
                                            Toast.makeText(EventActivity.this, "Algo falló en el servidor coleccion", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                                } else {
                                    Toast.makeText(EventActivity.this, "Algo falló en el servidor coleccion", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CollectionType>> call, Throwable t) {
                                Toast.makeText(EventActivity.this, "Algo falló en el servidor usuarios", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });

                    } else {
                        Toast.makeText(EventActivity.this, "Algo falló en el servidor usuarios", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(EventActivity.this, "Algo falló en el servidor", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    private void initUserSelected() {
        usersSelected = new ArrayList<>();
        userSelectedAdapter = new UserSelectedAdapter(usersSelected, position -> {
            User user = usersSelected.get(position);
            usersSelected.remove(position);
            users.add(user);
            userAdapter.notifyDataSetChanged();
            userSelectedAdapter.notifyDataSetChanged();
        });
        binding.usersSelected.setAdapter(userSelectedAdapter);
        binding.usersSelected.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initUsers() {
        users = new ArrayList<>();
        if (InitView.isOffline) {
            User user1 = new User("hola123@gmail.com", "123456", "Luis", "1066864914");
            User user2 = new User("hola123@gmail.com", "654321", "Eduardo", "1066864914");
            User user3 = new User("hola123@gmail.com", "147258", "Ospina", "1066864914");
            User user4 = new User("hola123@gmail.com", "191999", "Pava", "1066864914");
            users.add(user1);
            users.add(user2);
            users.add(user3);
            users.add(user4);
        } else {
            loadUsers();
        }
        userAdapter = new UserAdapter(users, position -> {
            User user = users.get(position);
            users.remove(position);
            usersSelected.add(user);
            userAdapter.notifyDataSetChanged();
            userSelectedAdapter.notifyDataSetChanged();
        });
        binding.listUsers.setAdapter(userAdapter);
        binding.listUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.Intent(EventActivity.this, Home.class);
    }

}