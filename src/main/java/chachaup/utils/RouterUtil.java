package chachaup.utils;

import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class RouterUtil {
    protected static void checkSession(Request req, Response res) {
        if (req.session().attribute("user") == null) {
            res.redirect("/login");
            halt();
        }
    }
}
