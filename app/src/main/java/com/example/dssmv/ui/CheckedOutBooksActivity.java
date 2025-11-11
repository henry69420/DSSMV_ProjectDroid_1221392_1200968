package com.example.dssmv.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dssmv.R;
import com.example.dssmv.adapter.CheckedOutBooksAdapter;
import com.example.dssmv.model.Checkout;
import com.example.dssmv.service.RequestsService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckedOutBooksActivity extends AppCompatActivity implements CheckedOutBooksAdapter.OnBookActionListener {
    
    private EditText userIdInput;
    private Button searchButton;
    private RecyclerView booksRecyclerView;
    private RecyclerView recentSearchesRecyclerView;
    private LinearLayout searchSection;
    private LinearLayout emptyState;
    private ProgressBar loadingIndicator;
    private LinearLayout recentSearchesContainer;
    
    private CheckedOutBooksAdapter adapter;
    private List<Checkout> checkedOutBooks = new ArrayList<>();
    private Set<String> recentSearches = new HashSet<>();
    private SharedPreferences sharedPreferences;
    
    private static final String PREFS_NAME = "CheckedOutBooksPrefs";
    private static final String RECENT_SEARCHES_KEY = "recent_searches";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_out_books);
        
        initializeViews();
        setupRecyclerViews();
        loadRecentSearches();
        setupClickListeners();
    }
    
    private void initializeViews() {
        userIdInput = findViewById(R.id.userIdInput);
        searchButton = findViewById(R.id.searchButton);
        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        recentSearchesRecyclerView = findViewById(R.id.recentSearchesRecyclerView);
        searchSection = findViewById(R.id.searchSection);
        emptyState = findViewById(R.id.emptyState);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        recentSearchesContainer = findViewById(R.id.recentSearchesContainer);
        
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    private void setupRecyclerViews() {

        adapter = new CheckedOutBooksAdapter(checkedOutBooks, this, this);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerView.setAdapter(adapter);
        

        recentSearchesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
    
    private void setupClickListeners() {
        searchButton.setOnClickListener(v -> searchCheckedOutBooks());
    }
    
    private void loadRecentSearches() {
        Set<String> savedSearches = sharedPreferences.getStringSet(RECENT_SEARCHES_KEY, new HashSet<>());
        recentSearches.addAll(savedSearches);
        updateRecentSearchesUI();
    }
    
    private void saveRecentSearches() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(RECENT_SEARCHES_KEY, recentSearches);
        editor.apply();
    }
    
    private void updateRecentSearchesUI() {
        if (recentSearches.isEmpty()) {
            recentSearchesContainer.setVisibility(View.GONE);
        } else {
            recentSearchesContainer.setVisibility(View.VISIBLE);
            setupRecentSearchesRecyclerView();
        }
    }
    
    private void setupRecentSearchesRecyclerView() {
        List<String> searchesList = new ArrayList<>(recentSearches);

        if (searchesList.size() > 5) {
            searchesList = searchesList.subList(0, 5);
        }
        

        final List<String> finalSearchesList = searchesList;
        

        androidx.recyclerview.widget.RecyclerView.Adapter<RecentSearchViewHolder> recentAdapter = 
            new androidx.recyclerview.widget.RecyclerView.Adapter<RecentSearchViewHolder>() {
                @Override
                public RecentSearchViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
                    View view = getLayoutInflater().inflate(R.layout.item_recent_search, parent, false);
                    return new RecentSearchViewHolder(view);
                }
                
                @Override
                public void onBindViewHolder(RecentSearchViewHolder holder, int position) {
                    String search = finalSearchesList.get(position);
                    holder.textView.setText(search);
                    holder.textView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                    holder.textView.setOnClickListener(v -> {
                        userIdInput.setText(search);
                        searchCheckedOutBooks();
                    });
                }
                
                @Override
                public int getItemCount() {
                    return finalSearchesList.size();
                }
            };
        
        recentSearchesRecyclerView.setAdapter(recentAdapter);
    }
    
    private static class RecentSearchViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        
        public RecentSearchViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recent_search_text);
        }
    }
    
    private void searchCheckedOutBooks() {
        String userId = userIdInput.getText().toString().trim();
        
        if (userId.isEmpty()) {
            Toast.makeText(this, "Please enter a valid User ID", Toast.LENGTH_SHORT).show();
            return;
        }
        
        showLoading(true);
        
        // Add to recent searches
        recentSearches.add(userId);
        saveRecentSearches();
        updateRecentSearchesUI();
        
        RequestsService.getCheckedOutBooks(userId, this, new RequestsService.CheckedOutBooksCallback() {
            @Override
            public void onSuccess(List<Checkout> checkouts) {
                runOnUiThread(() -> {
                    showLoading(false);
                    checkedOutBooks.clear();
                    checkedOutBooks.addAll(checkouts);
                    adapter.notifyDataSetChanged();

                    searchSection.setVisibility(View.GONE);
                    
                    if (checkouts.isEmpty()) {
                        showEmptyState(true);
                        showBooksList(false);
                    } else {
                        showEmptyState(false);
                        showBooksList(true);
                    }
                });
            }
            
            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    showLoading(false);
                    Toast.makeText(CheckedOutBooksActivity.this, 
                        "Failed to load checked out books: " + e.getMessage(), 
                        Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
    
    private void showLoading(boolean show) {
        loadingIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        searchButton.setEnabled(!show);
    }
    
    private void showBooksList(boolean show) {
        booksRecyclerView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    
    private void showEmptyState(boolean show) {
        emptyState.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public void onCheckIn(Checkout checkout) {
        String libraryId = checkout.getBook().getLibrary().getId();
        String isbn = checkout.getBook().getBook().getIsbn();
        String userId = checkout.getUserId();

        String formattedLibraryId = libraryId;

        if (libraryId != null && libraryId.length() == 32 && !libraryId.contains("-")) {
            formattedLibraryId = libraryId.substring(0, 8) + "-" +
                    libraryId.substring(8, 12) + "-" +
                    libraryId.substring(12, 16) + "-" +
                    libraryId.substring(16, 20) + "-" +
                    libraryId.substring(20, 32);
        }
        
        RequestsService.checkInBook(formattedLibraryId, isbn, userId, this, new RequestsService.CheckInCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(CheckedOutBooksActivity.this, 
                        "Book checked in successfully", Toast.LENGTH_SHORT).show();
                    // Refresh the list
                    searchCheckedOutBooks();
                });
            }
            
            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(CheckedOutBooksActivity.this, 
                        "Failed to check in the book: " + e.getMessage(), 
                        Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
    
    @Override
    public void onExtendCheckout(Checkout checkout) {
        RequestsService.extendCheckout(checkout.getId(), this, new RequestsService.ExtendCheckoutCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(CheckedOutBooksActivity.this, 
                        "Checkout extended successfully", Toast.LENGTH_SHORT).show();
                    // Refresh the list
                    searchCheckedOutBooks();
                });
            }
            
            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(CheckedOutBooksActivity.this, 
                        "Failed to extend the checkout: " + e.getMessage(), 
                        Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (booksRecyclerView.getVisibility() == View.VISIBLE || emptyState.getVisibility() == View.VISIBLE) {

            searchSection.setVisibility(View.VISIBLE);


            booksRecyclerView.setVisibility(View.GONE);
            emptyState.setVisibility(View.GONE);


            userIdInput.setText("");


        } else {

            super.onBackPressed();
        }
    }
}
