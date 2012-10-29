package com.arcbees.com.client.svgdrag;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.allen_sauer.gwt.dnd.client.util.DragClientBundle;
import com.google.gwt.user.client.ui.Widget;

public class SvgAbstractDropController implements DropController {
  // CHECKSTYLE_JAVADOC_OFF

  /**
   * The drop target.
   */
  private Widget dropTarget;

  public SvgAbstractDropController(Widget dropTarget) {
    this.dropTarget = dropTarget;
    dropTarget.addStyleName(DragClientBundle.INSTANCE.css().dropTarget());
  }

  @Override
  public Widget getDropTarget() {
    return dropTarget;
  }

  @Override
  public void onDrop(DragContext context) {
  }

  @Override
  public void onEnter(DragContext context) {
    dropTarget.addStyleName(DragClientBundle.INSTANCE.css().dropTargetEngage());
  }

  @Override
  public void onLeave(DragContext context) {
    dropTarget.removeStyleName(DragClientBundle.INSTANCE.css().dropTargetEngage());
  }

  @Override
  public void onMove(DragContext context) {
  }

  @SuppressWarnings("unused")
  @Override
  public void onPreviewDrop(DragContext context) throws VetoDragException {
  }
}