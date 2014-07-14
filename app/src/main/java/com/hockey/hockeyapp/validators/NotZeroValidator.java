package com.hockey.hockeyapp.validators;

/**
 * Created by Owner on 7/10/2014.
 */

import android.widget.EditText;
import com.andreabaccega.formedittextvalidator.Validator;


public class NotZeroValidator extends Validator {

    public NotZeroValidator(String _customErrorMessage) {
        super(_customErrorMessage);
    }

    @Override
    public boolean isValid(EditText editText) {
        String text = editText.getText().toString();

        if (text.equals("."))
            text = "0.0";

        double input = Double.parseDouble(text);
        return !(input == 0.0);
    }
}
