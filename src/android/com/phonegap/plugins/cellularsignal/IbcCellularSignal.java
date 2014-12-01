package com.phonegap.plugins.cellularsignal;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import org.json.JSONArray;
import org.json.JSONException;

public class IbcCellularSignal extends CordovaPlugin {
    
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	
        if (action.equals("enable")) {
        	startListen();
        } 
        if(action.equals("disable")) {
           stopListen();
		   this.webView.sendJavascript("oApp.sms.updateCellSignalLevel('')");  
        }
		if (action.equals("getstrength")) {
        	callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, getStrength()));
            return true;
        }
		if (action.equals("checksmssupport")) {
			Activity ctx = this.cordova.getActivity();
            if(ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)){
                this.webView.sendJavascript("oApp.sms.updateSupportsSMS(true)");
            } else {
                this.webView.sendJavascript("oApp.sms.updateSupportsSMS(false)");
            }
        }
		if (action.equals("checkrfcamerasupport")) {
			Activity ctx = this.cordova.getActivity();
            if(ctx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                this.webView.sendJavascript("oApp.barcodeScanner.updateSupportsCamera(true)");
            } else {
                this.webView.sendJavascript("oApp.barcodeScanner.updateSupportsCamera(false)");
            }
        }

        return true;
    } 

	String callback;
	
    
      PhoneStateListener phoneStateListener = new PhoneStateListener() {
                    @Override
                    public void onSignalStrengthsChanged(SignalStrength signalStrength)
                    {
                            super.onSignalStrengthsChanged(signalStrength);
                           
                            int strengthDbm = 0;
                            if (signalStrength.isGsm()){
                            	
                                    //strengthDbm = -140 + 2 * signalStrength.getGsmSignalStrength();
									strengthDbm = signalStrength.getGsmSignalStrength();
                            } else {
                                    //strengthDbm = signalStrength.getCdmaDbm();
                                    //if (strengthDbm == -1){
                                            //strengthDbm = signalStrength.getEvdoDbm();
                            }
                            updateSignalStrength(strengthDbm);
                    }
            };

		PhoneStateListener phoneStateListener2 = new PhoneStateListener() {
					@Override
                    public void onServiceStateChanged (ServiceState serviceState)
                    {
                            super.onServiceStateChanged(serviceState);
                           
                            updateServiceState(serviceState.getState());
                    }
            };
    
   
    private void updateSignalStrength(int strengthDbm) {
        this.webView.sendJavascript("oApp.sms.updateCellSignalLevel('" + strengthDbm + "')");  
    }

	private void updateServiceState(int serviceState) {
        this.webView.sendJavascript("oApp.sms.updateCellServiceState('" + serviceState + "')");  
    }
   
    @Override
    public void onPause(boolean multitasking)
    {
        stopListen();
    }
   
    @Override
    public void onResume(boolean multitasking)
    {
        startListen();
    }
   
    @Override
    public void onDestroy()
    {
            stopListen();
    }
    
    private void startListen()
    { TelephonyManager telephonyManager = (TelephonyManager) this.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
			telephonyManager.listen(phoneStateListener2, PhoneStateListener.LISTEN_SERVICE_STATE);
    }
    private void stopListen()
    { TelephonyManager telephonyManager = (TelephonyManager) this.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
			telephonyManager.listen(phoneStateListener2, PhoneStateListener.LISTEN_NONE);
    }
	private int getStrength(){
		SignalStrength signalStrength = (SignalStrength) this.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		int iStrength = 0;
		if (signalStrength.isGsm()){
			iStrength=signalStrength.getGsmSignalStrength();
		}
		return iStrength;
	}
   
   
}

