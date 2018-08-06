package com.example.procapp;

import java.io.IOException;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Create popup dialog for adding diary entrys
 * @author Tristan Cunningham
 *
 */
public class AddDialog extends DialogFragment {
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	LayoutInflater linf = LayoutInflater.from(getActivity());            
    	final View inflator = linf.inflate(R.layout.dialog_add, null);
    	
    	//  ==== dialog setup =====
    	
    	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity()); 

        alert.setTitle("Add entry"); 
        alert.setMessage("Dosage Ammount"); 
        alert.setView(inflator); 
        alert.setCancelable(false);	// Don't close unless by programmed
        
        //	===== Drug list setup =====
        
        RadioButton[] rb = new RadioButton[5];
    	RadioGroup rg = (RadioGroup) inflator.findViewById(R.id.radioGroup1);
    	
    	for(int i = 0; i < 5; i++){
    		rb[i] = new RadioButton(getActivity());
    		rb[i].setText(Drugs.values()[i].toString());	// For each drug in Drugs enum, add it to the list
    		rg.addView(rb[i]);
    	}
    	
    	//  ===== Buttons =====
         
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() { 
	        public void onClick(DialogInterface dialog, int whichButton) 
	        { 
	        	RadioGroup rg = (RadioGroup) inflator.findViewById(R.id.radioGroup1);
	        	EditText n = (EditText) inflator.findViewById(R.id.editText1);
	        	int i = rg.indexOfChild(inflator.findViewById(rg.getCheckedRadioButtonId())); // Get which drug list item is selected
	        	
	        	if(n.getText().toString().equals("") || n.getText() == null || i == -1){
	        		Toast.makeText(getActivity(), "No option selected or ammount set", 10).show(); // If invalid show warning, don't dismiss dialog
	        	} else {
	        		try {
						DiaryFrag.AddItem(Drugs.values()[i], Integer.parseInt(n.getText().toString())); // Add diary entry from dialog entered data
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		dialog.dismiss();	// Dismiss dialog once added
	        	}
            } 
        }); 

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
            	dialog.dismiss(); // if cancelled hide dialog
            } 
        }); 
        
        return alert.create(); // Return and show the dialog
    }
}