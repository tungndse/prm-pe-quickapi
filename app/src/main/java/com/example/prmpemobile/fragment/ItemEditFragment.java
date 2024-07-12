package com.example.prmpemobile.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prmpemobile.R;
import com.example.prmpemobile.api.ItemService;
import com.example.prmpemobile.model.Item;
import com.example.prmpemobile.model.ItemGetDto;
import com.example.prmpemobile.model.ItemPostDto;
import com.example.prmpemobile.model.ResponseObject;
import com.example.prmpemobile.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemEditFragment extends Fragment {

    private EditText editTextItemName;
    private EditText editTextItemDescription;
    private Item item;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_edit, container, false);

        // Initialize views
        editTextItemName = view.findViewById(R.id.editTextItemName);
        editTextItemDescription = view.findViewById(R.id.editTextItemDescription);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);

        // Get item from arguments
        if (getArguments() != null) {
            item = (Item) getArguments().getSerializable("item");
            if (item != null) {
                editTextItemName.setText(item.getName());
                editTextItemDescription.setText(item.getDescription());
            }
        }

        // Set up button listeners
        buttonCancel.setOnClickListener(v -> goBackToItemManager());
        buttonSubmit.setOnClickListener(v -> handleSubmit());

        return view;
    }

    private void goBackToItemManager() {
        Fragment itemManagerFragment = new ItemManagerFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, itemManagerFragment);
        fragmentTransaction.addToBackStack(null); // Optional: to allow navigation back
        fragmentTransaction.commit();
    }

    private void handleSubmit() {
        String itemName = editTextItemName.getText().toString();
        String itemDescription = editTextItemDescription.getText().toString();

        if (itemName.isEmpty() || itemDescription.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ItemPostDto itemPostDto = new ItemPostDto(itemName, itemDescription);

        ItemService itemService = RetrofitClient.getClient().create(ItemService.class);
        Call<ResponseObject<ItemGetDto>> call = itemService.updateItem(item.getId(), itemPostDto);

        call.enqueue(new Callback<ResponseObject<ItemGetDto>>() {
            @Override
            public void onResponse(Call<ResponseObject<ItemGetDto>> call, Response<ResponseObject<ItemGetDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();
                    goBackToItemManager();
                } else {
                    Toast.makeText(getContext(), "Failed to update item: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<ItemGetDto>> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
