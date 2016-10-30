package org.hmhb.exception;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

/**
 * DTO to hold exception information to send back as JSON in our REST calls.
 */
@Immutable
public class JsonErrorResponse {

    private final int statusCode;
    private final String type;
    private final String message;

    /**
     * Constructs a {@link JsonErrorResponse}.
     *
     * @param statusCode the http status code
     * @param type the exception type (fully qualified name)
     * @param message the exception message
     */
    public JsonErrorResponse(
            int statusCode,
            @Nonnull String type,
            @Nullable String message
    ) {
        this.statusCode = statusCode;
        this.type = requireNonNull(type, "type cannot be null");
        this.message = message; /* Exceptions don't always have a message. */
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
