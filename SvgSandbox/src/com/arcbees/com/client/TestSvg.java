package com.arcbees.com.client;

import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class TestSvg extends Composite {
  
  private static TestSvgUiBinder uiBinder = GWT.create(TestSvgUiBinder.class);

  interface TestSvgUiBinder extends UiBinder<Widget, TestSvg> {
  }
  
  @UiField
  SimplePanel panel;
  @UiField
  PushButton drawSvg;
  @UiField
  PushButton drawCanvas;
  @UiField
  IntegerBox times;
  @UiField
  PushButton clear;
  @UiField
  PushButton clearSvg;

  private String svg;
  
  public TestSvg() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @UiHandler("drawSvg")
  void onDrawSvgClick(ClickEvent event) {
    int times = getTimes();
    drawSvg(times);
  }
  
  @UiHandler("drawCanvas")
  void onDrawCanvasClick(ClickEvent event) {
    int times = getTimes();
    drawCanvas(times);
  }
  
  @UiHandler("clear")
  void onClearClick(ClickEvent event) {
    panel.clear();
  }
  
  @UiHandler("clearSvg")
  void onClearSvgClick(ClickEvent event) {
    clearSvg();
  }
  
  private void clearSvg() {
//    Element element = panel.getElement();
//    while (element.hasChildNodes()) { // never ends...
//      System.out.println("clearsvg element");
//      element.removeChild(element.getLastChild());
//    }
    panel.clear();
  }

  private int getTimes() {
    return times.getValue();
  }
  
  private void drawSvg(int times) {
    getFile("tiger.svg", times);
  }
  
  private void getFile(String file, final int times) {
    if (svg != null) {
      drawSvgs(times);
      return;
    }
    
    String url = "/svg/" + file;
    RequestBuilder request = new RequestBuilder(RequestBuilder.GET, url);
    request.setCallback(new RequestCallback() {
      @Override
      public void onResponseReceived(Request request, Response response) {
        TestSvg.this.svg = response.getText();
        drawSvgs(times);
      }
      
      @Override
      public void onError(Request request, Throwable exception) {
        Window.alert("error");
      }
    });
    try {
      request.send();
    } catch (RequestException e) {
      e.printStackTrace();
    }
  }

  private void drawSvgs(int times) {
    for (int i=0; i < times; i++) {
      drawSvg();
    }
  }

  private void drawSvg() {
    //panel.clear();
    clearSvg();
    
    OMSVGSVGElement svgElement = OMSVGParser.parse(svg);
    panel.add(svgElement);
    svgElement.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        Window.alert("Clicked");
      }
    });
  }
  
  private void drawCanvas(int times) {
    for (int i=0; i < times; i++) {
      drawCanvas();
    }
  }
  
  private void drawCanvas() {
    panel.clear();
    
    int width = 400;
    int height = 400;
    
    Canvas canvas = Canvas.createIfSupported();
    canvas.setWidth(width + "px");
    canvas.setHeight(height + "px");
    canvas.setCoordinateSpaceWidth(width);
    canvas.setCoordinateSpaceHeight(height);
    canvas.addMouseDownHandler(new MouseDownHandler() {
      public void onMouseDown(MouseDownEvent event) {
        Window.alert("MouseDown");
      }
    });
    panel.add(canvas);
    
    Context2d context = canvas.getContext2d();
    context.beginPath();
    context.arc(50, 50, 15, 0, Math.PI * 2, false); 
    context.stroke();
  }
  
}
