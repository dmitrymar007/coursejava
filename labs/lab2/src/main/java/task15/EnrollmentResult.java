package task15;

/** Ожидаемые исходы зачисления. Отказ здесь штатная ситуация, поэтому это значения, а не исключения. */
public enum EnrollmentResult {
    ENROLLED,
    COURSE_NOT_FOUND,
    COURSE_NOT_OPEN,
    ALREADY_APPLIED,
    PREREQUISITE_NOT_MET,
    NO_SEATS
}
