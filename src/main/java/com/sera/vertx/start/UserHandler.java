package com.sera.vertx.start;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public interface UserHandler extends Handler<RoutingContext> {
  static UserHandler create(Vertx vertx, Router router) {
    return new UserHandlerImpl(vertx, router);
  }
}
