package jp.oumimaiko.wordmemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.List;

import jp.oumimaiko.wordmemo.databinding.ActivityMainBinding;
import jp.oumimaiko.wordmemo.databinding.RecyclerItemBinding;

public class MainActivity extends FragmentActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        ObservableList<RecyclerItemViewModel> list = new ObservableArrayList<>();
        for (int i = 0; i < 50; i++) {
            RecyclerItemViewModel viewModel = new RecyclerItemViewModel("text1 : " + i, "text2 : " + i);
            list.add(viewModel);
        }
        binding.recycler.setAdapter(new ListAdapter(this, list));

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

        public ListAdapter(@NonNull Context context, @NonNull ObservableList<RecyclerItemViewModel> list) {
            this.context = context;
            this.list = list;
        }

        private final Context context;
        private final List<RecyclerItemViewModel> list;


        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(context, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final RecyclerItemViewModel viewModel = getItem(position);
            if (viewModel.isExpanded()) {
                holder.binding.expandButton.setSelected(false);
                holder.binding.expandableLayout.expand(true);
            } else {
                holder.binding.expandButton.setSelected(false);
                holder.binding.expandableLayout.collapse(true);
            }

            viewModel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewModel.isExpanded()) {
                        holder.binding.expandButton.setSelected(false);
                        holder.binding.expandableLayout.collapse(true);
                    } else {
                        holder.binding.expandButton.setSelected(true);
                        holder.binding.expandableLayout.expand(true);
                    }
                    viewModel.setExpanded(!viewModel.isExpanded());
                }
            });

            viewModel.setExpandButtonText(position + ". Tap to expand");

            holder.binding.setViewModel(viewModel);
            holder.binding.executePendingBindings();

        }

        @Override
        public int getItemCount() { return list.size(); }

        public RecyclerItemViewModel getItem(int position) {
            return list.get(position);
        }

        @NonNull
        @Override
        public String getSectionName(int position) { return null; }

        @SuppressLint("DefaultLocale")
        @NonNull
        private String getNameForItem(int position) { return String.format("Item %d", position + 1); }

        public class ViewHolder extends RecyclerView.ViewHolder {

            RecyclerItemBinding binding;

            public ViewHolder(Context context, ViewGroup parent) {
                super(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false));
                binding = DataBindingUtil.bind(itemView);
            }
        }
    }

    public void showDialog() {
        DialogFragment newFragment = new EditDialog();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
