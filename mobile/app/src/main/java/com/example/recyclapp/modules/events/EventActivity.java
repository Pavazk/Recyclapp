package com.example.recyclapp.modules.events;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recyclapp.R;
import com.example.recyclapp.common.Utils;
import com.example.recyclapp.common.interfaces.APIService;
import com.example.recyclapp.databinding.ActivityEventBinding;
import com.example.recyclapp.modules.events.adapters.UserAdapter;
import com.example.recyclapp.modules.events.adapters.UserSelectedAdapter;
import com.example.recyclapp.modules.events.model.CollectionType;
import com.example.recyclapp.modules.events.model.Event;
import com.example.recyclapp.modules.events.model.EventType;
import com.example.recyclapp.modules.events.model.RegisterEvent;
import com.example.recyclapp.modules.init.InitView;
import com.example.recyclapp.modules.main.data.User;
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
            RegisterEvent registerEvent = new RegisterEvent();
            registerEvent.setName(binding.etTitulo.getText().toString());
            registerEvent.setDescription(binding.etDescripcion.getText().toString());
            registerEvent.setCode_owner(Utils.restore(this, Utils.KEY_CODE));
            registerEvent.setEventType(listType.get(binding.spinner.getSelectedItemPosition()));
            registerEvent.setCollectionType(listCollection.get(binding.spinner2.getSelectedItemPosition()));
            registerEvent.setParticipants(usersSelected);
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey("case")) {
                service.updateEvent(registerEvent, extras.getInt("case")).enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EventActivity.this, "Evento actualizado", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(EventActivity.this, "Algo falló", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        Toast.makeText(EventActivity.this, "Algo falló", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
                return;
            }
            service.registerEvent(registerEvent).enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EventActivity.this, "Evento registrado", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(EventActivity.this, "Algo falló", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    Toast.makeText(EventActivity.this, "Algo falló", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        });
        binding.ivBack.setOnClickListener(v -> onBackPressed());
    }

    List<CollectionType> listCollection;
    List<EventType> listType;

    private void loadUsers() {
        try {
            listCollection = new ArrayList<>();
            listType = new ArrayList<>();
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
                                    listCollection = response.body();
                                    List<String> data = new ArrayList<>();
                                    for (CollectionType collectionType : listCollection) {
                                        data.add(collectionType.getName());
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EventActivity.this, R.layout.spinner_item_layout, data);
                                    adapter.setDropDownViewResource(R.layout.spinner_item_layout);
                                    binding.spinner2.setAdapter(adapter);
                                    service.getAllEventType().enqueue(new Callback<List<EventType>>() {
                                        @Override
                                        public void onResponse(Call<List<EventType>> call, Response<List<EventType>> response) {
                                            if (response.isSuccessful()) {
                                                listType = response.body();
                                                List<String> data = new ArrayList<>();
                                                for (EventType eventType : listType) {
                                                    data.add(eventType.getName());
                                                }
                                                ArrayAdapter<String> adapter = new ArrayAdapter<>(EventActivity.this, R.layout.spinner_item_layout, data);
                                                adapter.setDropDownViewResource(R.layout.spinner_item_layout);
                                                binding.spinner.setAdapter(adapter);

                                                userAdapter = new UserAdapter(users, position -> {
                                                    System.out.println("CLICK USER ADAPTER");
                                                    User user = users.get(position);
                                                    users.remove(position);
                                                    usersSelected.add(user);
                                                    userAdapter.updateData(users);
                                                    userSelectedAdapter.updateData(usersSelected);
                                                });
                                                binding.listUsers.setAdapter(userAdapter);
                                                binding.listUsers.setLayoutManager(new LinearLayoutManager(EventActivity.this, LinearLayoutManager.VERTICAL, false));
                                                validateUpdate();
                                            } else {
                                                Toast.makeText(EventActivity.this, "Algo falló en el servidor evento", Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<EventType>> call, Throwable t) {
                                            Toast.makeText(EventActivity.this, "Algo falló en el servidor coleccion", Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }
                                    });
                                } else {
                                    Toast.makeText(EventActivity.this, "Algo falló en el servidor coleccion", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CollectionType>> call, Throwable t) {
                                Toast.makeText(EventActivity.this, "Algo falló en el servidor usuarios", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        });

                    } else {
                        Toast.makeText(EventActivity.this, "Algo falló en el servidor usuarios", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(EventActivity.this, "Algo falló en el servidor", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    public void validateUpdate() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("case")) {
            APIService service = Utils.getRetrofit(this).create(APIService.class);
            service.getRegisterEvent(extras.getInt("case")).enqueue(new Callback<RegisterEvent>() {
                @Override
                public void onResponse(Call<RegisterEvent> call, Response<RegisterEvent> response) {
                    if (response.isSuccessful()) {
                        RegisterEvent registerEvent = response.body();
                        binding.title.setText("Actualizar evento");
                        binding.button.setText("Actualizar");
                        binding.etTitulo.setText(registerEvent.getName());
                        binding.etDescripcion.setText(registerEvent.getDescription());
                        for (int i = 0; i < listType.size(); i++) {
                            if (listType.get(i).getName().equals(registerEvent.getEventType().getName())) {
                                binding.spinner.setSelection(i);
                                break;
                            }
                        }
                        for (int i = 0; i < listCollection.size(); i++) {
                            if (listCollection.get(i).getName().equals(registerEvent.getCollectionType().getName())) {
                                binding.spinner2.setSelection(i);
                                break;
                            }
                        }
                        List<User> temp = new ArrayList<>();
                        for (User user : usersSelected) {
                            if(!users.contains(user)){
                                temp.add(user);
                            }
                        }

                        userSelectedAdapter.updateData(registerEvent.getParticipants());
                        usersSelected = registerEvent.getParticipants();

                        userAdapter.updateData(temp);
                        users = temp;
                        return;
                    }
                    Toast.makeText(EventActivity.this, "Algo falló", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

                @Override
                public void onFailure(Call<RegisterEvent> call, Throwable t) {
                    Toast.makeText(EventActivity.this, "Algo falló", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
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
    }

    private void initUserSelected() {
        usersSelected = new ArrayList<>();
        userSelectedAdapter = new UserSelectedAdapter(usersSelected, position -> {
            System.out.println("CLICK USER SELECTED ADAPTER");
            User user = usersSelected.get(position);
            usersSelected.remove(position);
            users.add(user);
            userAdapter.updateData(users);
            userSelectedAdapter.updateData(usersSelected);
        });
        binding.usersSelected.setAdapter(userSelectedAdapter);
        binding.usersSelected.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Utils.Intent(EventActivity.this, Home.class);
    }

}