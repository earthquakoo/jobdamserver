package com.jobdamserver.core.exception;

import lombok.Getter;

@Getter
public enum ErrorInfo {
    /**
     * Authentication
     **/
    MEMBER_ALREADY_EXIST(400, "MEMBER_ALREADY_EXIST", "Member with the given email already exists."),
    MEMBER_NOT_FOUND(400, "MEMBER_NOT_FOUND", "Member with the given email does not exist."),
    INVALID_PASSWORD(400, "INVALID_PASSWORD", "Password is invalid."),

    /**
     * Room Exceptions
     */
    DUPLICATE_ROOM(400, "DUPLICATE_ROOM", "Duplicate room name."),
    ROOM_NOT_FOUND(400, "ROOM_NOT_FOUND", "Room does not exist."),
    PARTICIPANTS_OVER_COUNT(400, "PARTICIPANTS_OVER_COUNT", "You cannot set it smaller than the number of people in the current room."),

    /**
     * JWT Exceptions
     **/
    INVALID_JWT_TOKEN(401, "INVALID_JWT_TOKEN", "JWT token is invalid."),
    EXPIRED_JWT_TOKEN(401, "EXPIRED_JWT_TOKEN", "JWT token is expired."),

    /**
     * Global Exceptions
     **/
    UNAUTHORIZED_OPERATION_EXCEPTION(403, "UNAUTHORIZED_OPERATION_EXCEPTION", "You do not have permission to perform the operation");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    ErrorInfo(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
