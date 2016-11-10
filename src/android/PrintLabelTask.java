package schonmann.rl4e;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import honeywell.connection.ConnectionBase;
import honeywell.connection.Connection_Bluetooth;

public class PrintLabelTask extends AsyncTask<JSONObject, Void, Void>{

	private String TAG = "PrintLabelTask";

	private CallbackContext callback;

	public PrintLabelTask(Context context, CallbackContext callback) throws JSONException {
		this.callback = callback;
	}

	@Override
	protected Void doInBackground(JSONObject... params){
		try {
			doPrint(params[0].getString("mac"), params[0].getString("data").getBytes());
		}catch(JSONException jse){
			callback.error(400);
		}catch(Exception e){
			callback.error(500);
		}

		return null;
	}

	private void doPrint(String mac, byte[] data) throws Exception{
		
		ConnectionBase conn = Connection_Bluetooth.createClient(mac,false);

		Log.d(TAG, "Estabilishing connection...");

		if(!conn.getIsOpen()){
			if(!conn.open()){
				callback.error(404);
				return;
			}
		}

		Log.d(TAG, "Connection OK. Sending data...");

		int bytesWritten = 0;
		int bytesToWrite = 1024;
		int totalBytes = data.length;
		int remainingBytes = totalBytes;

		while(bytesWritten < totalBytes){
			if(remainingBytes < bytesToWrite)
				bytesToWrite = remainingBytes;
			conn.write(data, bytesWritten, bytesToWrite);
			bytesWritten += bytesToWrite;
			remainingBytes = remainingBytes - bytesToWrite;
			Thread.sleep(100);
		}

		conn.close();

		Log.d(TAG, "Print success! :)");

		callback.success(200);
	}
}
