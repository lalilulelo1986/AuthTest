package kz.lalafa.jpademo.logging.service

import org.junit.Test

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED
import org.apache.catalina.manager.StatusTransformer.setContentType
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.lang.Thread.sleep
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@RunWith(SpringRunner::class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    lateinit var authService: AuthService

    @Test
    fun loginByPhone() {
        authService.requestLoggingByPhone("4234234")
    }
}

class Sms{
    @Test
    fun sms() {
        val rt = RestTemplate()

        //while (true) {
        //for (J in 1..1){
            val newFixedThreadPool = Executors.newFixedThreadPool(10)
            newFixedThreadPool.submit {
                val headers = HttpHeaders()
                headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
                val map = LinkedMultiValueMap<String, String>()
                map.add("st.r.phone", "+77071113146")
                val req = HttpEntity<MultiValueMap<String, String>>(map, headers)
                rt.postForEntity("https://ok.ru/dk?cmd=AnonymRecoveryStartPhoneLink&st.cmd=anonymRecoveryStartPhoneLink", req, String::class.java)
                //println(J)
            }
            //if (J % 100 == 0)
                newFixedThreadPool.awaitTermination(1, TimeUnit.SECONDS)
        sleep(5L)
            newFixedThreadPool.shutdown()
        //}
        //}
    }
}