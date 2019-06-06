package kz.lalafa.jpademo.logging.service

interface OtpInterface {

    fun requestOtp(phone: String)

    fun validateOtp(otp: String, token: String)
}