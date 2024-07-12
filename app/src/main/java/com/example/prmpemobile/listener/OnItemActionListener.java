package com.example.prmpemobile.listener;

import com.example.prmpemobile.model.Item;

public interface OnItemActionListener {
    void onItemEdit(Item item);
    void onItemDelete(Item item);
}