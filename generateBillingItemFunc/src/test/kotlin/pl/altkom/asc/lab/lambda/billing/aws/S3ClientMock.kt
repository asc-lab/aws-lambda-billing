package pl.altkom.asc.lab.lambda.billing.aws

import com.amazonaws.services.s3.event.S3EventNotification
import java.io.BufferedReader
import java.io.StringReader

class S3ClientMock : S3Client() {

    override fun getReader(record: S3EventNotification.S3EventNotificationRecord): BufferedReader {
        return BufferedReader(StringReader(
                "99050555745;Ka Aa;A\n" +
                        "29120458762;Kb Ab;A\n" +
                        "39091666028;Kc Ac;B\n" +
                        "77050929111;Kd Ad;A\n" +
                        "76091166752;Ke Ae;A\n" +
                        "97031653569;Kf Af;B\n" +
                        "35060205229;Kg Ag;A\n" +
                        "38112669875;Kh Ah;B\n" +
                        "13102408939;Kh Ah;A"
        ))
    }
}