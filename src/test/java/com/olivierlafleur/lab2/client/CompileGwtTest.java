package com.olivierlafleur.lab2.client;

import com.google.gwt.junit.client.GWTTestCase;

public class CompileGwtTest extends GWTTestCase {
  
  @Override
  public String getModuleName() {
    return "com.olivierlafleur.lab2.Project";
  }

  public void testSandbox() {
    assertTrue(true);
  }
  
}
