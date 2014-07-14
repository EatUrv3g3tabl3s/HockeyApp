package com.hockey.hockeyapp.validators;

/**
 * Created by Owner on 7/10/2014.
 */

import android.widget.EditText;
import com.andreabaccega.formedittextvalidator.Validator;


public class NoSpaceValidator extends Validator {

    public NoSpaceValidator() {
        super("Can't have spaces");
    }

    @Override
    public boolean isValid(EditText editText) {
        return !editText.getText().toString().contains(" ");
    }
}
