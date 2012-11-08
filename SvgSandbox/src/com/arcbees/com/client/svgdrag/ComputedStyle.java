package com.arcbees.com.client.svgdrag;

import com.google.gwt.dom.client.Element;

public class ComputedStyle {

  public static native String getStyleProperty(Element el, String prop) /*-{
        var computedStyle;
        if (document.defaultView && document.defaultView.getComputedStyle) { // standard (includes ie9)
            computedStyle = document.defaultView.getComputedStyle(el, null)[prop];

        } else if (el.currentStyle) { // IE older
            computedStyle = el.currentStyle[prop];

        } else { // inline style
            computedStyle = el.style[prop];
        }
        return computedStyle;
  }-*/;

  public static native String getStyle(Element el, String prop) /*-{
        var style = el.style[prop];
        return style;
  }-*/;

}
