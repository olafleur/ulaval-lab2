package com.olivierlafleur.lab2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ProjectEntryPoint implements EntryPoint {
  
  @Override
  public void onModuleLoad() {
    HelloWorldConstants constants = GWT.create(HelloWorldConstants.class);
    
    RootPanel.get().add(new HTML(constants.Hello()));
  }

}
