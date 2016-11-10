package schonmann.rl4e;

import android.content.Context;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Antonio Schönmann on 29/06/2016.
 */

public class LabelPrinterPlugin extends CordovaPlugin{
    public static final String TAG = "LabelPrinterPlugin";

    private static final String PRINT = "print";

    public LabelPrinterPlugin() {
	}

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

	    final JSONObject options = args.getJSONObject(0);
        final Context context = this.cordova.getActivity().getApplicationContext();
        
        if(PRINT.equals(action)){
			PrintLabelTask plTask = new PrintLabelTask(context, callbackContext);
			plTask.execute(options);
        }

        return true;
    }
}