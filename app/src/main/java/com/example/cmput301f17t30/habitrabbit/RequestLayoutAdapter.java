/*
 *     <HabitRabbit- A habit tracking app.>
 *     Copyright (C) <2017>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.example.cmput301f17t30.habitrabbit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.cmput301f17t30.habitrabbit.MainActivity.userController;

/**
 * Adapter for displaying incoming friend requests
 * @see com.example.cmput301f17t30.habitrabbit.AddFriendDialogue
 */

public class RequestLayoutAdapter extends RecyclerView.Adapter<RequestLayoutAdapter.ViewHolder> {

    private ArrayList<FriendRequest> requests;

    public RequestLayoutAdapter(ArrayList<FriendRequest> requests, Context context) {
        this.requests = requests;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView friendName;
        private ImageView friendProfile;
        private Button acceptButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            friendName = itemView.findViewById(R.id.requester_name);
            friendProfile = itemView.findViewById(R.id.requester_pic);
        }

        @Override
        public void onClick(View view) {
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .request_row_layout, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final RequestLayoutAdapter.ViewHolder holder, final int position) {
        final FriendRequest request = requests.get(position);

        String friendName = request.getSender();

        holder.friendName.setText(friendName);
        //holder.friendProfile.setImageBitmap(friendProfilePic);

        Button acceptButton = holder.itemView.findViewById(R.id.accept_request);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add friend to your friends list
                if (userController.getFriends().contains(request.getSender()) || request.getSender().equals(userController.getUsername())){

                } else {
                    userController.addFriend(request.getSender());
                    userController.saveUser();
                    ElasticSearchController.AcceptRequestTask acceptTask = new ElasticSearchController.AcceptRequestTask();
                    acceptTask.execute(request);
                }
                ElasticSearchController.DeleteRequestTask deleteRequestTask = new ElasticSearchController.DeleteRequestTask();
                deleteRequestTask.execute(request);
                requests.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });

        Button denyButton = holder.itemView.findViewById(R.id.deny_request);
        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ElasticSearchController.DeleteRequestTask deleteTask = new ElasticSearchController.DeleteRequestTask();
                deleteTask.execute(request);
                requests.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {

        return requests.size();
    }
}
