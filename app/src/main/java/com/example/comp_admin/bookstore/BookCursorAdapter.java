package com.example.comp_admin.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp_admin.bookstore.data.BookContract;
import com.example.comp_admin.bookstore.data.BookContract.BookEntry;

import static com.example.comp_admin.bookstore.R.string.editor_insert_book_failed;

/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {
    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */


    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById( R.id.name );
        TextView priceTextView = view.findViewById( R.id.price );
        final TextView quantityTextView = view.findViewById( R.id.quantity );
        Button button = view.findViewById( R.id.button_sale );
        button.setFocusable(false);

        // Find the columns of book attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex( BookEntry._ID );
        int nameColumnIndex = cursor.getColumnIndex( BookEntry.COLUMN_BOOK_NAME );
        int priceColumnIndex = cursor.getColumnIndex( BookEntry.COLUMN_BOOK_PRICE );
        final int quantityColumnIndex = cursor.getColumnIndex( BookEntry.COLUMN_BOOK_QUANTITY );

        // Read the pet attributes from the Cursor for the current book
        final String id = cursor.getString( idColumnIndex );
        String bookName = cursor.getString( nameColumnIndex );
        Double bookPrice = cursor.getDouble( priceColumnIndex );
        final Integer quantityBook = cursor.getInt( quantityColumnIndex );


        // Update the TextViews with the attributes for the current book
        nameTextView.setText( bookName );
        priceTextView.setText( String.valueOf(bookPrice) );
        quantityTextView.setText( String.valueOf( quantityBook ) );

        // Set onClickListener on the "BUY" button, will decrease quantity by 1 unless quantity
        // is at 0 where we then have a toast message pop up saying "Sold Out"
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantityBook == 0) {
                    Toast.makeText( view.getContext(), "Sold Out", Toast.LENGTH_SHORT ).show();
                } else {
                    MainActivity inventoryActivity = (MainActivity) context;
                    inventoryActivity.decreaseQuantity( Integer.valueOf( id ), Integer.valueOf( quantityBook ) );
                }
            }
        } );


    }

}

