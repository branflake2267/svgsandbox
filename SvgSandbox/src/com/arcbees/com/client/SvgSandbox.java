package com.arcbees.com.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SvgSandbox implements EntryPoint {

  @Override
  public void onModuleLoad() {
    TestSvg test = new TestSvg();
    RootPanel.get().add(test);
  }

}
