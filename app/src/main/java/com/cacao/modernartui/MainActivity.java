package com.cacao.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends ActionBarActivity {
	
	static private final String URL = "http://www.moma.org";
	
	private View leftTopView, leftBottomView, rightTopView, rightBottomView;
	private SeekBar seekBar;
	private int ltColor, lbColor, rtColor, rbColor;
	private DialogFragment mDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        leftTopView = (View) findViewById(R.id.left_top);
        leftBottomView = (View) findViewById(R.id.left_bottom);
        rightTopView = (View) findViewById(R.id.right_top);
        rightBottomView = (View) findViewById(R.id.right_bottom);
        seekBar = (SeekBar) findViewById(R.id.seekBarBottom);
        
        ltColor = getColor(leftTopView);
        lbColor = getColor(leftBottomView);
        rtColor = getColor(rightTopView);
        rbColor = getColor(rightBottomView);
        
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }
    
    private void changeColor (View view, int factor, int color){
    	view.setBackgroundColor(color + factor*2);
    }
    private int getColor(View view){
    	return ((ColorDrawable) view.getBackground()).getColor();
    }
    
    private OnSeekBarChangeListener seekBarChangeListener= new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			changeColor(leftTopView, progress, ltColor);
			changeColor(leftBottomView, progress, lbColor);
			changeColor(rightTopView, progress, rtColor);
			changeColor(rightBottomView, progress, rbColor);
		}
	};
	
	private void visitMOMA (boolean letsVisit){
		if(letsVisit){
			Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
			startActivity(baseIntent);
		} else {
			mDialog.dismiss();
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_more) {
            mDialog = AlertDialogFragment.newInstanse();
            mDialog.show(getFragmentManager(), "Alert");
        }
        return super.onOptionsItemSelected(item);
    }
    
    public static class AlertDialogFragment extends DialogFragment {
    	public static AlertDialogFragment newInstanse(){
    		return new AlertDialogFragment();
    	}
    	
    	@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
					.setMessage("Click below to learn more!")
					.setCancelable(false)
					.setNegativeButton("Not Now",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									((MainActivity) getActivity())
										.visitMOMA(false);
								}
							})		
					.setPositiveButton("Visit MOMA",
							new DialogInterface.OnClickListener() {
								public void onClick(final DialogInterface dialog, int id) {
									((MainActivity) getActivity())
										.visitMOMA(true);
								}
							}).create();
				}
    	}
}
