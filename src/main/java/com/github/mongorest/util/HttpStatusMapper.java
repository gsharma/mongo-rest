package com.github.mongorest.util;

public final class HttpStatusMapper {
    public static enum Successful implements BaseStatus {
        SERVICE_ALIVE("Service is alive and well", 200), OK("Service successfully completed the operation.", 200);

        private final String message;
        private final int httpStatusCode;

        private Successful(final String message, final int httpStatusCode) {
            this.message = message;
            this.httpStatusCode = httpStatusCode;
        }

        public String message() {
            return message;
        }

        public int code() {
            return httpStatusCode;
        }
    }

    public static enum ClientError implements BaseStatus {
        BAD_REQUEST("Service was unhappy with the data in the request.", 400), UNAUTHORIZED(
                "Service could not authenticate caller with the given authorization credentials.", 401), NOT_FOUND(
                "Service could not lookup entity with the provided id.", 404), ALREADY_EXISTS(
                "You already submitted the same entity.", 409);

        private final String message;
        private final int httpStatusCode;

        private ClientError(final String message, final int httpStatusCode) {
            this.message = message;
            this.httpStatusCode = httpStatusCode;
        }

        public String message() {
            return message;
        }

        public int code() {
            return httpStatusCode;
        }
    }

    public static enum ServerError implements BaseStatus {
        RUNTIME_ERROR("Operation unexpectedly failed due to an internal service problem.", 500), RATE_LIMIT_CLIENT(
                "You have made too many requests to the service and have presently been rate limited.", 503), SERVICE_UNAVAILABLE(
                "Service is currently unable to service requests.", 503), ;

        private final String message;
        private final int httpStatusCode;

        private ServerError(final String message, final int httpStatusCode) {
            this.message = message;
            this.httpStatusCode = httpStatusCode;
        }

        public String message() {
            return message;
        }

        public int code() {
            return httpStatusCode;
        }
    }

    public interface BaseStatus {
        public String message();

        public int code();
    }
}