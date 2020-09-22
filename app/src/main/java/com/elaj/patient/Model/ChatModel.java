package com.elaj.patient.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wokman on 5/7/2017.
 */

public class ChatModel {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("order_id")
    @Expose
    public int orderId;
    @SerializedName("sender_id")
    @Expose
    public int senderId;
    @SerializedName("deleted_by_sender")
    @Expose
    public int deletedBySender;
    @SerializedName("deleted_by_recipient")
    @Expose
    public int deletedByRecipient;
    @SerializedName("ordered_at")
    @Expose
    public String orderedAt;
    @SerializedName("last_message")
    @Expose
    public String lastMessage;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("unread_messages")
    @Expose
    public int unreadMessages;
    @SerializedName("friend_id")
    @Expose
    public int friendId;
    @SerializedName("friend_name")
    @Expose
    public String friendName;
    @SerializedName("friend_img")
    @Expose
    public String friendImg;

    public int getId() {

        return id;
    }

}
