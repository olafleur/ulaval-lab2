package com.olivierlafleur.lab2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ProjectEntryPoint implements EntryPoint {
  private TabPanel tabPanel;
  private HTMLPanel autreRetour;
	
  @Override
  public void onModuleLoad() {
    HelloWorldConstants constants = GWT.create(HelloWorldConstants.class);
    
    RootPanel.get().add(new HTML(constants.Hello()));
  
    SimplePanel retour = requeteServeur();
    autreRetour = new HTMLPanel("");
    requeteServeur2();
    
    tabPanel = new TabPanel();
    
    tabPanel.add(autreRetour, "Page 0");
    tabPanel.add(retour, "Page 1");
    tabPanel.add(new HTML("<h1>Page 2</h1>"), "Page 2");
    
    tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
    	public void onSelection(SelectionEvent<Integer> event) {
    		History.newItem("page" + event.getSelectedItem());
    	}
    });
    
    History.addValueChangeHandler(new ValueChangeHandler<String>() {
    	public void onValueChange(ValueChangeEvent<String> event) {
    		String historyToken = event.getValue();
    		
    		try {
    			if(historyToken.substring(0, 4).equals("page")) {
    				String tabIndexToken = historyToken.substring(4,5);
    				int tabIndex = Integer.parseInt(tabIndexToken);
    				tabPanel.selectTab(tabIndex);
    			} else {
    				tabPanel.selectTab(0);
    			}
    		} catch (IndexOutOfBoundsException e) {
    			tabPanel.selectTab(0);
    		}
    	}
    });
    
    tabPanel.selectTab(0);
    
    RootPanel.get().add(tabPanel);
  }
  
  private void requeteServeur2() {
	String url = "https://api.github.com/users/olafleur";
	
	JsonpRequestBuilder rb = new JsonpRequestBuilder();
	
	rb.requestObject(url, new AsyncCallback<JavaScriptObject>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("ERREUR");
		}

		@Override
		public void onSuccess(JavaScriptObject result) {
			JSONObject objResult = new JSONObject(result);
			GWT.log(objResult.toString());
			String image = objResult.get("data").isObject().get("avatar_url").isString().stringValue();
			
			autreRetour.add(new HTML("<img src="+image+">"));
		}});

}

private SimplePanel requeteServeur() {
	  RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "http://127.0.0.1:8888");
	  final Label label = new Label();
	  SimplePanel simplePanel = new SimplePanel();
	  
	  rb.setCallback(new RequestCallback() {
		  public void onResponseReceived (Request req, Response res) {
			  if(res.getStatusCode() == 200) {
				  label.setText(res.getText());
			  } else {
				  label.setText("Erreur");
			  }
		  }
		  
		  public void onError(Request req, Throwable e) {
			  label.setText("ERREUR SERVEUR");
		  }
	  });
	  
	  try {
		rb.send();
	} catch (RequestException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  
	  simplePanel.add(label);
	  
	  return simplePanel;
  }

}
