package com.mojtaba.jobboard.exception;

/**
 * This exception is used for business-layer authorization failures.
 *
 * <p>Example: user tries to update someone else's job
 * <pre>{@code
 * if (!job.getOwnerId().equals(currentUserId)) {
 *     throw new UnauthorizedException("You cannot modify this job");
 * }
 * }</pre>
 */
public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
