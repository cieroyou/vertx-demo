package com.sera.vertx.start;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);

        router.get("/abcd")
                .respond(
                        ctx -> Future.succeededFuture(new JsonObject().put("hello", "world")));

        router.get("/person")
                .respond(
                        ctx -> Future.succeededFuture(new ResponseDto("sera", 33)));


        router.route("/users/*").handler(UserHandler.create(vertx, router));
//    router.route().handler(context -> {
//      String address = context.request().connection().remoteAddress().toString();
//      // Get the query parameter "name"
//      MultiMap queryParams = context.queryParams();
//      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
//      // Write a json response
//      context.json(new JsonObject().put("name", name).put("address", address).put("message", "Hello " + name + " connected from " + address));
//    });
        Route route = router.route();

        route.handler(ctx -> {

            HttpServerResponse response = ctx.response();
            // enable chunked responses because we will be adding data as
            // we execute over other handlers. This is only required once and
            // only if several handlers do output.
            response.setChunked(true);

            response.write("route1\n");

            // Call the next matching route after a 5 second delay
            ctx.vertx().setTimer(5000, tid -> ctx.next());
        });

        route.handler(ctx -> {

            HttpServerResponse response = ctx.response();
            response.write("route2\n");

            // Call the next matching route after a 5 second delay
            ctx.vertx().setTimer(5000, tid -> ctx.next());
        });

        route.handler(ctx -> {

            HttpServerResponse response = ctx.response();
            response.write("route3");

            // Now end the response
            ctx.response().end();
        });

        vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }
}
