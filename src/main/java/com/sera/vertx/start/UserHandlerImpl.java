package com.sera.vertx.start;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

//https://stackoverflow.com/questions/55352588/vertx-how-to-separate-routers-to-a-different-class-keeping-a-single-verticle
public class UserHandlerImpl implements UserHandler {
  private Router router;

  public UserHandlerImpl(Vertx vertx, Router router) {
    this.router = router;

    router.get("/:id").respond(ctx -> getUser());

//    router.get("/:id").handler(ctx -> {
//        HttpServerResponse response = ctx.response();
//        response.setChunked(true);
//        response.write("route3");
//        ctx.response().end();
//      }
//    );

//    router.get("/:id/").handler(this::getVehicle);
//    router.get("/:id/").handler(this::getVehicle);
//    router.post("/:id/trips/:tripId/reports").handler(this::postReports);
//    router.post(":id/journey").handler(this::postJourney);
  }

  private Future<JsonObject> getUser() {
//    return Future.succeededFuture(new JsonObject().put("hello", "sera"));
    return Future.succeededFuture(JsonObject.mapFrom(new ResponseDto("sera", 33)));

  }

  @Override

  public void handle(RoutingContext ctx) {
    router.handleContext(ctx);
  }
}
