package com.cjm721.overloaded.util;

import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ScrollEvent extends Event {

  public final double dx;
  public final double dy;

  public ScrollEvent(double dx, double dy) {
    this.dx = dx;
    this.dy = dy;
  }
}
