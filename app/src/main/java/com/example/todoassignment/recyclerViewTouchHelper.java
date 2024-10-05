package com.example.todoassignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoassignment.Adapter.todoAdapter;

public class recyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback {

    private todoAdapter adapter;

    public recyclerViewTouchHelper(todoAdapter adapter) {
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.adapter=adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction==ItemTouchHelper.RIGHT){
            AlertDialog.Builder builder=new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are You Sure ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.deleteTask(position);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(position);
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else{
            adapter.editItem(position);
        }
    }
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // Check if swipe action is being performed
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Retrieve item view
            View itemView = viewHolder.itemView;

            Paint p = new Paint();

            // For swipe right (delete action)
            if (dX > 0) {
                // Set red background for swipe right
                p.setColor(Color.RED);
                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), p);

                // Set delete icon
                Drawable deleteIcon = ContextCompat.getDrawable(adapter.getContext(),R.drawable.baseline_delete_sweep_24);
                int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + iconMargin;
                int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                int iconLeft = itemView.getLeft() + iconMargin;
                int iconRight = iconLeft + deleteIcon.getIntrinsicWidth();

                // Draw delete icon
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                deleteIcon.draw(c);
            }
            // For swipe left (edit action)
            else if (dX < 0) {
                // Set primary dark color background for swipe left
                p.setColor(ContextCompat.getColor(adapter.getContext(), R.color.colorPrimaryDark));
                c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), p);

                // Set edit icon
                Drawable editIcon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.baseline_mode_edit_outline_24);
                int iconMargin = (itemView.getHeight() - editIcon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + iconMargin;
                int iconBottom = iconTop + editIcon.getIntrinsicHeight();
                int iconRight = itemView.getRight() - iconMargin;
                int iconLeft = iconRight - editIcon.getIntrinsicWidth();

                // Draw edit icon
                editIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                editIcon.draw(c);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}
