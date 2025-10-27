package com.example.dssmv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.dssmv.R;
import com.example.dssmv.model.Checkout;
import com.example.dssmv.model.Book;
import com.example.dssmv.model.Author;
import com.example.dssmv.service.RequestsService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckedOutBooksAdapter extends RecyclerView.Adapter<CheckedOutBooksAdapter.ViewHolder> {
    private List<Checkout> checkouts;
    private Context context;
    private OnBookActionListener listener;

    public interface OnBookActionListener {
        void onCheckIn(Checkout checkout);
        void onExtendCheckout(Checkout checkout);
    }

    public CheckedOutBooksAdapter(List<Checkout> checkouts, Context context, OnBookActionListener listener) {
        this.checkouts = checkouts;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checked_out_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Checkout checkout = checkouts.get(position);
        Book book = checkout.getBook().getBook();
        
        // Set book title
        holder.titleText.setText(book.getTitle());
        
        // Set authors
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            StringBuilder authors = new StringBuilder();
            for (Author author : book.getAuthors()) {
                if (authors.length() > 0) {
                    authors.append(", ");
                }
                authors.append(author.getName());
            }
            holder.authorText.setText(authors.toString());
        } else {
            holder.authorText.setText("Unknown Author");
        }
        
        // Set library name
        holder.libraryText.setText("From: " + checkout.getBook().getLibrary().getName());
        
        // Set due date information
        LocalDateTime dueDate = checkout.getDueDate();
        if (dueDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            holder.dueDateText.setText("Due: " + dueDate.format(formatter));
            
            // Calculate days until due
            LocalDateTime now = LocalDateTime.now();
            long daysUntilDue = java.time.temporal.ChronoUnit.DAYS.between(now, dueDate);
            
            if (daysUntilDue <= 0) {
                holder.daysUntilDueText.setText("Overdue by " + Math.abs(daysUntilDue) + " days");
                holder.daysUntilDueText.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            } else {
                holder.daysUntilDueText.setText("Due in " + daysUntilDue + " days");
                holder.daysUntilDueText.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
            }
        }
        
        // Load book cover
        if (book.getCover() != null && book.getCover().getMediumUrl() != null) {
            String coverUrl = book.getCover().getMediumUrl();
            if (coverUrl.contains("/api/v1/assets/cover/")) {
                coverUrl = coverUrl.replace("/api/v1/assets/cover/", "");
            }
            
            RequestsService.getCover(coverUrl, context, new RequestsService.CoverCallback() {
                @Override
                public void onSuccess(android.graphics.Bitmap bitmap) {
                    holder.coverImage.setImageBitmap(bitmap);
                }
                
                @Override
                public void onError(Exception e) {
                    holder.coverImage.setImageResource(R.drawable.book_error);
                }
            });
        } else {
            holder.coverImage.setImageResource(R.drawable.book_placeholder);
        }
        
        // Set button click listeners
        holder.checkInButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCheckIn(checkout);
            }
        });
        
        holder.extendButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onExtendCheckout(checkout);
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkouts.size();
    }

    public void updateData(List<Checkout> newCheckouts) {
        this.checkouts = newCheckouts;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;
        TextView titleText;
        TextView authorText;
        TextView libraryText;
        TextView dueDateText;
        TextView daysUntilDueText;
        Button checkInButton;
        Button extendButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImage = itemView.findViewById(R.id.coverImage);
            titleText = itemView.findViewById(R.id.titleText);
            authorText = itemView.findViewById(R.id.authorText);
            libraryText = itemView.findViewById(R.id.libraryText);
            dueDateText = itemView.findViewById(R.id.dueDateText);
            daysUntilDueText = itemView.findViewById(R.id.daysUntilDueText);
            checkInButton = itemView.findViewById(R.id.checkInButton);
            extendButton = itemView.findViewById(R.id.extendButton);
        }
    }
}
