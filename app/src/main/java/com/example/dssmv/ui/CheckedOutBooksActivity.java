package com.example.dssmv.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dssmv.R;
import com.example.dssmv.adapter.CheckedOutBooksAdapter;
import com.example.dssmv.model.Checkout;
import com.example.dssmv.model.Library;
import com.example.dssmv.service.RequestsService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private List<Checkout> allCheckouts = new ArrayList<>();
    private boolean isShowingLibraryList = false;

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

    // NOVO VIEWHOLDER para o layout de Card mais desenvolvido (item_library.xml)
    private static class LibraryGroupViewHolder extends RecyclerView.ViewHolder {
        TextView libraryName, libraryAddress;
        TextView openDays, openTime, closeTime;
        Button updateButton, deleteButton;

        public LibraryGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            libraryName = itemView.findViewById(R.id.libraryName);
            libraryAddress = itemView.findViewById(R.id.libraryAddress);
            openDays = itemView.findViewById(R.id.openDays);
            openTime = itemView.findViewById(R.id.openTime);
            closeTime = itemView.findViewById(R.id.closeTime);
            updateButton = itemView.findViewById(R.id.btnUpdateLibrary);
            deleteButton = itemView.findViewById(R.id.btnDeleteLibrary);
        }
    }

    private void searchCheckedOutBooks() {
        String userId = userIdInput.getText().toString().trim();

        if (userId.isEmpty()) {
            Toast.makeText(this, "Please enter a valid User ID", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading(true);

        recentSearches.add(userId);
        saveRecentSearches();
        updateRecentSearchesUI();

        RequestsService.getCheckedOutBooks(userId, this, new RequestsService.CheckedOutBooksCallback() {
            @Override
            public void onSuccess(List<Checkout> checkouts) {
                runOnUiThread(() -> {
                    showLoading(false);

                    allCheckouts.clear();
                    allCheckouts.addAll(checkouts);

                    searchSection.setVisibility(View.GONE);

                    if (allCheckouts.isEmpty()) {
                        showEmptyState(true);
                        showBooksList(false);
                    } else {
                        showEmptyState(false);
                        displayLibrariesList();
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

    private void displayLibrariesList() {
        java.util.Map<String, List<Checkout>> groupedCheckouts = allCheckouts.stream()
                .collect(Collectors.groupingBy(c -> c.getBook().getLibrary().getId()));

        List<Library> librariesList = new ArrayList<>();

        for (List<Checkout> group : groupedCheckouts.values()) {
            Library library = group.get(0).getBook().getLibrary();
            librariesList.add(library);
        }

        librariesList.sort(Comparator.comparing(Library::getName));

        androidx.recyclerview.widget.RecyclerView.Adapter<LibraryGroupViewHolder> libraryAdapter =
                new androidx.recyclerview.widget.RecyclerView.Adapter<LibraryGroupViewHolder>() {

                    @NonNull
                    @Override
                    public LibraryGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = getLayoutInflater().inflate(R.layout.item_library, parent, false);

                        view.setLayoutParams(new RecyclerView.LayoutParams(
                                RecyclerView.LayoutParams.MATCH_PARENT,
                                RecyclerView.LayoutParams.WRAP_CONTENT));

                        return new LibraryGroupViewHolder(view);
                    }

                    @Override
                    public void onBindViewHolder(@NonNull LibraryGroupViewHolder holder, int position) {
                        Library library = librariesList.get(position);
                        int count = groupedCheckouts.get(library.getId()).size();

                        holder.updateButton.setVisibility(View.GONE);
                        holder.deleteButton.setVisibility(View.GONE);

                        holder.openDays.setVisibility(View.GONE);
                        holder.openTime.setVisibility(View.GONE);
                        holder.closeTime.setVisibility(View.GONE);

                        if (holder.openTime.getParent() instanceof LinearLayout) {

                            ((View) holder.openTime.getParent()).setVisibility(View.GONE);
                        }

                        holder.libraryName.setText(library.getName());

                        holder.libraryAddress.setText(count + " checked out books.");
                        holder.libraryAddress.setTypeface(null, android.graphics.Typeface.BOLD);
                        holder.libraryAddress.setTextColor(getResources().getColor(R.color.green_main));

                        holder.itemView.setOnClickListener(v -> {
                            showBooksForLibrary(library.getId(), library.getName());
                        });
                    }

                    @Override
                    public int getItemCount() {
                        return librariesList.size();
                    }
                };

        showBooksList(true);
        booksRecyclerView.setAdapter(libraryAdapter);
        isShowingLibraryList = true;
    }

    private void showBooksForLibrary(String libraryId, String libraryName) {
        List<Checkout> filteredCheckouts = allCheckouts.stream()
                .filter(checkout -> checkout.getBook().getLibrary().getId().equals(libraryId))
                .collect(Collectors.toList());

        checkedOutBooks.clear();
        checkedOutBooks.addAll(filteredCheckouts);

        booksRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        isShowingLibraryList = false;
        Toast.makeText(this, "Showing books from: " + libraryName, Toast.LENGTH_SHORT).show();
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

        String formattedLibraryId = libraryId.replace("-", "");

        RequestsService.checkInBook(formattedLibraryId, isbn, userId, this, new RequestsService.CheckInCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(CheckedOutBooksActivity.this,
                            "Book checked in successfully", Toast.LENGTH_SHORT).show();
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
        if (!isShowingLibraryList && booksRecyclerView.getVisibility() == View.VISIBLE) {

            displayLibrariesList();
        } else if (isShowingLibraryList && booksRecyclerView.getVisibility() == View.VISIBLE) {

            searchSection.setVisibility(View.VISIBLE);
            booksRecyclerView.setAdapter(null);
            booksRecyclerView.setVisibility(View.GONE);
            emptyState.setVisibility(View.GONE);
            userIdInput.setText("");
            isShowingLibraryList = false;
        } else {

            super.onBackPressed();
        }
    }
}
