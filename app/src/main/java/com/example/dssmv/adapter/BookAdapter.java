package com.example.dssmv.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.dssmv.R;
import com.example.dssmv.helper.Utils;
import com.example.dssmv.model.Book;
import com.example.dssmv.model.Author;
import java.util.List;
import java.util.stream.Collectors;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private OnBookClickListener onBookClickListener;


    public BookAdapter(List<Book> bookList, OnBookClickListener onBookClickListener) {
        this.bookList = bookList;
        this.onBookClickListener = onBookClickListener;
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorsTextView, isbnTextView;
        ImageView coverImageView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.book_title);
            authorsTextView = itemView.findViewById(R.id.book_authors);
            isbnTextView = itemView.findViewById(R.id.book_isbn);
            coverImageView = itemView.findViewById(R.id.book_cover);
        }


        /* public void bind(Book book, OnBookClickListener onBookClickListener) {
            titleTextView.setText(book.getTitle());
            if(book.getAuthors() != null) {
            authorsTextView.setText(book.getAuthors().toString());
            }
            isbnTextView.setText(book.getIsbn());
            itemView.setOnClickListener(v -> onBookClickListener.onBookClick(book.getIsbn()));
        } */
        public void bind(Book book, OnBookClickListener onBookClickListener) {
            if (book == null) {
                Log.e("BookAdapter", "Book is null");
                return;
            }
            titleTextView.setText(book.getTitle());
            if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
                String authors = TextUtils.join(", ", book.getAuthors().stream().map(Author::getName).collect(Collectors.toList()));
                authorsTextView.setText(authors);
            } else {
                authorsTextView.setText("Unknown Authors");
            }
            isbnTextView.setText(book.getIsbn());

            if (book.getCover() != null && book.getCover().getSmallUrl() != null) {
                String imageUrl = book.getCover().getSmallUrl();
                if (imageUrl != null && imageUrl.startsWith("/api/v1/assets/cover/")) {
                    imageUrl = imageUrl.replace("/api/v1/assets/cover/", "");
                }
                String coverUrl = Utils.getWSAddress(itemView.getContext()) + "assets/cover/" + imageUrl;
                Glide.with(itemView.getContext())
                        .load(coverUrl)
                        .placeholder(R.drawable.book_placeholder)
                        .error(R.drawable.book_error)
                        .into(coverImageView);
            } else {
                coverImageView.setImageResource(R.drawable.book_placeholder);
            }

            itemView.setOnClickListener(v -> onBookClickListener.onBookClick(book.getIsbn()));
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(bookList.get(position), onBookClickListener);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public interface OnBookClickListener {
        void onBookClick(String isbn);
    }
}
