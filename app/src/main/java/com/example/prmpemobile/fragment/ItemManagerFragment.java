package com.example.prmpemobile.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prmpemobile.R;
import com.example.prmpemobile.activity.MainMenuActivity;
import com.example.prmpemobile.adapter.ItemAdapter;
import com.example.prmpemobile.api.ItemService;
import com.example.prmpemobile.listener.OnItemActionListener;
import com.example.prmpemobile.model.Item;
import com.example.prmpemobile.model.ResponseObject;
import com.example.prmpemobile.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemManagerFragment extends Fragment implements OnItemActionListener {

    private RecyclerView recyclerViewItems;
    private ItemAdapter itemAdapter;
    private List<Item> itemList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_manager, container, false);

        recyclerViewItems = view.findViewById(R.id.recyclerViewItems);

        // Set up RecyclerView with ItemAdapter
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter = new ItemAdapter(itemList, this); // Pass `this` as the listener
        recyclerViewItems.setAdapter(itemAdapter);

        // Load items from server or other sources
        loadItems();

        // Set up buttons
        view.findViewById(R.id.buttonMenu).setOnClickListener(v -> goToMainMenu());
        view.findViewById(R.id.buttonCreate).setOnClickListener(v -> goToItemCreation());

        return view;
    }

    private void loadItems() {
        ItemService itemService = RetrofitClient.getClient().create(ItemService.class);
        Call<ResponseObject<List<Item>>> call = itemService.getItems();

        call.enqueue(new Callback<ResponseObject<List<Item>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Item>>> call, Response<ResponseObject<List<Item>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> items = response.body().getData();
                    itemList.clear();
                    itemList.addAll(items);
                    itemAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load items", Toast.LENGTH_SHORT).show();
                    Log.e("ItemManagerFragment", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Item>>> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ItemManagerFragment", "Request failed", t);
            }
        });
    }

    private void goToMainMenu() {
        // Navigate back to MainMenuActivity
        Intent intent = new Intent(getActivity(), MainMenuActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    private void goToItemCreation() {
        // Navigate to ItemCreationFragment
        Fragment itemCreationFragment = new ItemCreationFragment();

        // Begin a FragmentTransaction
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with ItemCreationFragment
        fragmentTransaction.replace(R.id.fragment_container, itemCreationFragment);
        fragmentTransaction.addToBackStack(null); // Optional: to allow navigation back
        fragmentTransaction.commit();
    }

    @Override
    public void onItemEdit(Item item) {
        // Create an instance of ItemEditFragment
        ItemEditFragment itemEditFragment = new ItemEditFragment();

        // Pass the item data to the fragment
        Bundle args = new Bundle();
        args.putSerializable("item", item); // Ensure `Item` implements `Serializable`
        itemEditFragment.setArguments(args);

        // Begin a FragmentTransaction
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with ItemEditFragment
        fragmentTransaction.replace(R.id.fragment_container, itemEditFragment);
        fragmentTransaction.addToBackStack(null); // Optional: to allow navigation back
        fragmentTransaction.commit();
    }

    @Override
    public void onItemDelete(Item item) {
        // Show a confirmation dialog before deleting
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item: " + item.getName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> performDelete(item))
                .setNegativeButton("No", null)
                .show();
    }

    private void performDelete(Item item) {
        ItemService itemService = RetrofitClient.getClient().create(ItemService.class);
        Call<Void> call = itemService.deleteItem(item.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Remove item from the list and notify the adapter
                    itemList.remove(item);
                    itemAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to delete item: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
