package com.example.dssmv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dssmv.R;
import com.example.dssmv.dtos.LibraryDto;
import com.example.dssmv.model.Library;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private List<Library> libraries;
    private LibraryClickListener libraryClickListener;
    /*private OnDeleteClickListener onDeleteClickListener;


    public interface OnDeleteClickListener {
        void onDeleteClick(Library library);
    }*/

    public LibraryAdapter(List<Library> libraries, LibraryClickListener libraryClickListener) {
        this.libraries = libraries;
        this.libraryClickListener = libraryClickListener;

    }
    public interface LibraryClickListener {
        void onUpdateClick(String libraryId);
        void onDeleteClick(String libraryId);
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library, parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Library library = libraries.get(position);
        holder.bind(library);

        holder.libraryName.setText(library.getName());
        holder.libraryAddress.setText(library.getAddress());


        holder.updateButton.setOnClickListener(v -> {
            if (libraryClickListener != null) {
                libraryClickListener.onUpdateClick(library.getId());
            }
        });


        holder.deleteButton.setOnClickListener(v -> {
            if (libraryClickListener != null) {
                libraryClickListener.onDeleteClick(library.getId());
            }
        });

       /* holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(library);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    public static class LibraryViewHolder extends RecyclerView.ViewHolder {
        private TextView libraryName, libraryAddress, openDays, openTime, closeTime;
        //private Button deleteButton;
        Button updateButton, deleteButton;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            libraryName = itemView.findViewById(R.id.libraryName);
            libraryAddress = itemView.findViewById(R.id.libraryAddress);
            openDays = itemView.findViewById(R.id.openDays);
            openTime = itemView.findViewById(R.id.openTime);
            closeTime = itemView.findViewById(R.id.closeTime);
            //deleteButton = itemView.findViewById(R.id.buttonDeleteLibrary);
            updateButton = itemView.findViewById(R.id.btnUpdateLibrary);
            deleteButton = itemView.findViewById(R.id.btnDeleteLibrary);
        }

        public void bind(Library library) {
            libraryName.setText(library.getName());
            libraryAddress.setText(library.getAddress());
            openDays.setText(library.getOpenDays());
            openTime.setText(library.getOpenTime());
            closeTime.setText(library.getCloseTime());
        }
    }
}
